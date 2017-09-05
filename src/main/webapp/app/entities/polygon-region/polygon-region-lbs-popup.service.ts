import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PolygonRegionLbs } from './polygon-region-lbs.model';
import { PolygonRegionLbsService } from './polygon-region-lbs.service';

@Injectable()
export class PolygonRegionLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private polygonRegionService: PolygonRegionLbsService

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
                this.polygonRegionService.find(id).subscribe((polygonRegion) => {
                    polygonRegion.createTime = this.datePipe
                        .transform(polygonRegion.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    polygonRegion.updateTime = this.datePipe
                        .transform(polygonRegion.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    polygonRegion.deleteTime = this.datePipe
                        .transform(polygonRegion.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.polygonRegionModalRef(component, polygonRegion);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.polygonRegionModalRef(component, new PolygonRegionLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    polygonRegionModalRef(component: Component, polygonRegion: PolygonRegionLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.polygonRegion = polygonRegion;
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
