import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { WebTypeLbs } from './web-type-lbs.model';
import { WebTypeLbsService } from './web-type-lbs.service';

@Injectable()
export class WebTypeLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private webTypeService: WebTypeLbsService

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
                this.webTypeService.find(id).subscribe((webType) => {
                    webType.createTime = this.datePipe
                        .transform(webType.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    webType.updateTime = this.datePipe
                        .transform(webType.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    webType.deleteTime = this.datePipe
                        .transform(webType.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.webTypeModalRef(component, webType);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.webTypeModalRef(component, new WebTypeLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    webTypeModalRef(component: Component, webType: WebTypeLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.webType = webType;
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
