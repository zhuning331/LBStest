import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RegionLbs } from './region-lbs.model';
import { RegionLbsService } from './region-lbs.service';

@Injectable()
export class RegionLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private regionService: RegionLbsService

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
                this.regionService.find(id).subscribe((region) => {
                    region.createTime = this.datePipe
                        .transform(region.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    region.updateTime = this.datePipe
                        .transform(region.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    region.deleteTime = this.datePipe
                        .transform(region.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.regionModalRef(component, region);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.regionModalRef(component, new RegionLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    regionModalRef(component: Component, region: RegionLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.region = region;
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
