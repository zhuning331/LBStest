import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { WorkspaceLbs } from './workspace-lbs.model';
import { WorkspaceLbsService } from './workspace-lbs.service';

@Injectable()
export class WorkspaceLbsPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private workspaceService: WorkspaceLbsService

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
                this.workspaceService.find(id).subscribe((workspace) => {
                    workspace.createTime = this.datePipe
                        .transform(workspace.createTime, 'yyyy-MM-ddTHH:mm:ss');
                    workspace.updateTime = this.datePipe
                        .transform(workspace.updateTime, 'yyyy-MM-ddTHH:mm:ss');
                    workspace.deleteTime = this.datePipe
                        .transform(workspace.deleteTime, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.workspaceModalRef(component, workspace);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.workspaceModalRef(component, new WorkspaceLbs());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    workspaceModalRef(component: Component, workspace: WorkspaceLbs): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.workspace = workspace;
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
