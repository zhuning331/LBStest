import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RegularRegionLbs } from './regular-region-lbs.model';
import { RegularRegionLbsService } from './regular-region-lbs.service';

@Injectable()
export class RegularRegionLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private regularRegionService: RegularRegionLbsService

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
                this.regularRegionService.find(id).subscribe((regularRegion) => {
                    regularRegion.createTime = this.datePipe
                        .transform(regularRegion.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    regularRegion.updateTime = this.datePipe
                        .transform(regularRegion.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    regularRegion.deleteTime = this.datePipe
                        .transform(regularRegion.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.regularRegionModalRef(component, regularRegion);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.regularRegionModalRef(component, new RegularRegionLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    regularRegionModalRef(component: Component, regularRegion: RegularRegionLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.regularRegion = regularRegion;
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
