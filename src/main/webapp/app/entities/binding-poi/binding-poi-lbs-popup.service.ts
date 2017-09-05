import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BindingPOILbs } from './binding-poi-lbs.model';
import { BindingPOILbsService } from './binding-poi-lbs.service';

@Injectable()
export class BindingPOILbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bindingPOIService: BindingPOILbsService

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
                this.bindingPOIService.find(id).subscribe((bindingPOI) => {
                    bindingPOI.createTime = this.datePipe
                        .transform(bindingPOI.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    bindingPOI.updateTime = this.datePipe
                        .transform(bindingPOI.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    bindingPOI.deleteTime = this.datePipe
                        .transform(bindingPOI.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.bindingPOIModalRef(component, bindingPOI);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.bindingPOIModalRef(component, new BindingPOILbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    bindingPOIModalRef(component: Component, bindingPOI: BindingPOILbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bindingPOI = bindingPOI;
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
