import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { WorkspaceConfigLbs } from './workspace-config-lbs.model';
import { WorkspaceConfigLbsService } from './workspace-config-lbs.service';

@Injectable()
export class WorkspaceConfigLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private workspaceConfigService: WorkspaceConfigLbsService

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
                this.workspaceConfigService.find(id).subscribe((workspaceConfig) => {
                    workspaceConfig.createTime = this.datePipe
                        .transform(workspaceConfig.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    workspaceConfig.updateTime = this.datePipe
                        .transform(workspaceConfig.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    workspaceConfig.deleteTime = this.datePipe
                        .transform(workspaceConfig.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.workspaceConfigModalRef(component, workspaceConfig);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.workspaceConfigModalRef(component, new WorkspaceConfigLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    workspaceConfigModalRef(component: Component, workspaceConfig: WorkspaceConfigLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.workspaceConfig = workspaceConfig;
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
