import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { LocationUpdateLbs } from './location-update-lbs.model';
import { LocationUpdateLbsService } from './location-update-lbs.service';

@Injectable()
export class LocationUpdateLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private locationUpdateService: LocationUpdateLbsService

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
                this.locationUpdateService.find(id).subscribe((locationUpdate) => {
                    locationUpdate.time = this.datePipe
                        .transform(locationUpdate.time, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.locationUpdateModalRef(component, locationUpdate);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.locationUpdateModalRef(component, new LocationUpdateLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    locationUpdateModalRef(component: Component, locationUpdate: LocationUpdateLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.locationUpdate = locationUpdate;
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
