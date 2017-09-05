import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { LayerLbs } from './layer-lbs.model';
import { LayerLbsService } from './layer-lbs.service';

@Injectable()
export class LayerLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private layerService: LayerLbsService

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
                this.layerService.find(id).subscribe((layer) => {
                    layer.createTime = this.datePipe
                        .transform(layer.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    layer.updateTime = this.datePipe
                        .transform(layer.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    layer.deleteTime = this.datePipe
                        .transform(layer.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.layerModalRef(component, layer);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.layerModalRef(component, new LayerLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    layerModalRef(component: Component, layer: LayerLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.layer = layer;
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
