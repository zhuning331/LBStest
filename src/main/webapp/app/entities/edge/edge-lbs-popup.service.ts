import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { EdgeLbs } from './edge-lbs.model';
import { EdgeLbsService } from './edge-lbs.service';

@Injectable()
export class EdgeLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private edgeService: EdgeLbsService

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
                this.edgeService.find(id).subscribe((edge) => {
                    edge.createTime = this.datePipe
                        .transform(edge.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    edge.updateTime = this.datePipe
                        .transform(edge.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    edge.deleteTime = this.datePipe
                        .transform(edge.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.edgeModalRef(component, edge);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.edgeModalRef(component, new EdgeLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    edgeModalRef(component: Component, edge: EdgeLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.edge = edge;
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
