import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WorkspaceConfigLbs } from './workspace-config-lbs.model';
import { WorkspaceConfigLbsPopupService } from './workspace-config-lbs-popup.service';
import { WorkspaceConfigLbsService } from './workspace-config-lbs.service';
import { WorkspaceLbs, WorkspaceLbsService } from '../workspace';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-workspace-config-lbs-dialog',
    templateUrl: './workspace-config-lbs-dialog.component.html'
})
export class WorkspaceConfigLbsDialogComponent implements OnInit {

    workspaceConfig: WorkspaceConfigLbs;
    isSaving: boolean;

    workspaces: WorkspaceLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private workspaceConfigService: WorkspaceConfigLbsService,
        private workspaceService: WorkspaceLbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.workspaceService.query()
            .subscribe((res: ResponseWrapper) => { this.workspaces = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.workspaceConfig.id !== undefined) {
            this.subscribeToSaveResponse(
                this.workspaceConfigService.update(this.workspaceConfig));
        } else {
            this.subscribeToSaveResponse(
                this.workspaceConfigService.create(this.workspaceConfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<WorkspaceConfigLbs>) {
        result.subscribe((res: WorkspaceConfigLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: WorkspaceConfigLbs) {
        this.eventManager.broadcast({ name: 'workspaceConfigListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackWorkspaceById(index: number, item: WorkspaceLbs) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-workspace-config-lbs-popup',
    template: ''
})
export class WorkspaceConfigLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workspaceConfigPopupService: WorkspaceConfigLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.workspaceConfigPopupService
                    .open(WorkspaceConfigLbsDialogComponent as Component, params['id']);
            } else {
                this.workspaceConfigPopupService
                    .open(WorkspaceConfigLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
