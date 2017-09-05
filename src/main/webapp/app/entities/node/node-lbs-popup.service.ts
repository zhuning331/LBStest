import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { NodeLbs } from './node-lbs.model';
import { NodeLbsService } from './node-lbs.service';

@Injectable()
export class NodeLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private nodeService: NodeLbsService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.nodeService.find(id).subscribe((node) => {
                    node.createTime = this.datePipe
                        .transform(node.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    node.updateTime = this.datePipe
                        .transform(node.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    node.deleteTime = this.datePipe
                        .transform(node.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.nodeModalRef(component, node);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.nodeModalRef(component, new NodeLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    nodeModalRef(component: Component, node: NodeLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.node = node;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
