import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { POITypeLbs } from './poi-type-lbs.model';
import { POITypeLbsService } from './poi-type-lbs.service';

@Injectable()
export class POITypeLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private pOITypeService: POITypeLbsService

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
                this.pOITypeService.find(id).subscribe((pOIType) => {
                    pOIType.createTime = this.datePipe
                        .transform(pOIType.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    pOIType.updateTime = this.datePipe
                        .transform(pOIType.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    pOIType.deleteTime = this.datePipe
                        .transform(pOIType.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.pOITypeModalRef(component, pOIType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pOITypeModalRef(component, new POITypeLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pOITypeModalRef(component: Component, pOIType: POITypeLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pOIType = pOIType;
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
