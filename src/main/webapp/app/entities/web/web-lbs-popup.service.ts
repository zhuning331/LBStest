import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { WebLbs } from './web-lbs.model';
import { WebLbsService } from './web-lbs.service';

@Injectable()
export class WebLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private webService: WebLbsService

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
                this.webService.find(id).subscribe((web) => {
                    web.createTime = this.datePipe
                        .transform(web.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    web.updateTime = this.datePipe
                        .transform(web.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    web.deleteTime = this.datePipe
                        .transform(web.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.webModalRef(component, web);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.webModalRef(component, new WebLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    webModalRef(component: Component, web: WebLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.web = web;
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
