import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { MapLbs } from './map-lbs.model';
import { MapLbsService } from './map-lbs.service';

@Injectable()
export class MapLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private mapService: MapLbsService

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
                this.mapService.find(id).subscribe((map) => {
                    map.createTime = this.datePipe
                        .transform(map.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    map.updateTime = this.datePipe
                        .transform(map.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    map.deleteTime = this.datePipe
                        .transform(map.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.mapModalRef(component, map);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.mapModalRef(component, new MapLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    mapModalRef(component: Component, map: MapLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.map = map;
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
