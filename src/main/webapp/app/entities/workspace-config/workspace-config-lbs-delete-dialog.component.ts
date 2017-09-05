import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WorkspaceConfigLbs } from './workspace-config-lbs.model';
import { WorkspaceConfigLbsPopupService } from './workspace-config-lbs-popup.service';
import { WorkspaceConfigLbsService } from './workspace-config-lbs.service';

@Component({
    selector: 'jhi-workspace-config-lbs-delete-dialog',
    templateUrl: './workspace-config-lbs-delete-dialog.component.html'
})
export class WorkspaceConfigLbsDeleteDialogComponent {

    workspaceConfig: WorkspaceConfigLbs;

    constructor(
        private workspaceConfigService: WorkspaceConfigLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.workspaceConfigService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'workspaceConfigListModification',
                content: 'Deleted an workspaceConfig'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-workspace-config-lbs-delete-popup',
    template: ''
})
export class WorkspaceConfigLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workspaceConfigPopupService: WorkspaceConfigLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.workspaceConfigPopupService
                .open(WorkspaceConfigLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
