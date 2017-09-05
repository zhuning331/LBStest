import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { EdgeTypeLbs } from './edge-type-lbs.model';
import { EdgeTypeLbsService } from './edge-type-lbs.service';

@Injectable()
export class EdgeTypeLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private edgeTypeService: EdgeTypeLbsService

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
                this.edgeTypeService.find(id).subscribe((edgeType) => {
                    edgeType.createTime = this.datePipe
                        .transform(edgeType.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    edgeType.updateTime = this.datePipe
                        .transform(edgeType.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    edgeType.deleteTime = this.datePipe
                        .transform(edgeType.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.edgeTypeModalRef(component, edgeType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.edgeTypeModalRef(component, new EdgeTypeLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    edgeTypeModalRef(component: Component, edgeType: EdgeTypeLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.edgeType = edgeType;
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
