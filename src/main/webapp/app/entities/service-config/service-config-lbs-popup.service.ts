import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ServiceConfigLbs } from './service-config-lbs.model';
import { ServiceConfigLbsService } from './service-config-lbs.service';

@Injectable()
export class ServiceConfigLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private serviceConfigService: ServiceConfigLbsService

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
                this.serviceConfigService.find(id).subscribe((serviceConfig) => {
                    serviceConfig.createTime = this.datePipe
                        .transform(serviceConfig.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    serviceConfig.updateTime = this.datePipe
                        .transform(serviceConfig.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    serviceConfig.deleteTime = this.datePipe
                        .transform(serviceConfig.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.serviceConfigModalRef(component, serviceConfig);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.serviceConfigModalRef(component, new ServiceConfigLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    serviceConfigModalRef(component: Component, serviceConfig: ServiceConfigLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.serviceConfig = serviceConfig;
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
