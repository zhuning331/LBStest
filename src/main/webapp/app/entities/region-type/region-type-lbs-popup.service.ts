import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RegionTypeLbs } from './region-type-lbs.model';
import { RegionTypeLbsService } from './region-type-lbs.service';

@Injectable()
export class RegionTypeLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private regionTypeService: RegionTypeLbsService

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
                this.regionTypeService.find(id).subscribe((regionType) => {
                    regionType.createTime = this.datePipe
                        .transform(regionType.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    regionType.updateTime = this.datePipe
                        .transform(regionType.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    regionType.deleteTime = this.datePipe
                        .transform(regionType.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.regionTypeModalRef(component, regionType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.regionTypeModalRef(component, new RegionTypeLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    regionTypeModalRef(component: Component, regionType: RegionTypeLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.regionType = regionType;
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
