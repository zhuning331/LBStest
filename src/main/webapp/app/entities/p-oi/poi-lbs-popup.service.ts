import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { POILbs } from './poi-lbs.model';
import { POILbsService } from './poi-lbs.service';

@Injectable()
export class POILbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private pOIService: POILbsService

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
                this.pOIService.find(id).subscribe((pOI) => {
                    pOI.createTime = this.datePipe
                        .transform(pOI.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    pOI.updateTime = this.datePipe
                        .transform(pOI.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    pOI.deleteTime = this.datePipe
                        .transform(pOI.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.pOIModalRef(component, pOI);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pOIModalRef(component, new POILbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pOIModalRef(component: Component, pOI: POILbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pOI = pOI;
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
