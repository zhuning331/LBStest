import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { POIHistoricalLocationLbs } from './poi-historical-location-lbs.model';
import { POIHistoricalLocationLbsService } from './poi-historical-location-lbs.service';

@Injectable()
export class POIHistoricalLocationLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private pOIHistoricalLocationService: POIHistoricalLocationLbsService

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
                this.pOIHistoricalLocationService.find(id).subscribe((pOIHistoricalLocation) => {
                    pOIHistoricalLocation.time = this.datePipe
                        .transform(pOIHistoricalLocation.time, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.pOIHistoricalLocationModalRef(component, pOIHistoricalLocation);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.pOIHistoricalLocationModalRef(component, new POIHistoricalLocationLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    pOIHistoricalLocationModalRef(component: Component, pOIHistoricalLocation: POIHistoricalLocationLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pOIHistoricalLocation = pOIHistoricalLocation;
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
