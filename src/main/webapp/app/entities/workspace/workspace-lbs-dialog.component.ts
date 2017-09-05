import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WorkspaceLbs } from './workspace-lbs.model';
import { WorkspaceLbsPopupService } from './workspace-lbs-popup.service';
import { WorkspaceLbsService } from './workspace-lbs.service';
import { MapLbs, MapLbsService } from '../map';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-workspace-lbs-dialog',
    templateUrl: './workspace-lbs-dialog.component.html'
})
export class WorkspaceLbsDialogComponent implements OnInit {

    workspace: WorkspaceLbs;
    isSaving: boolean;

    maps: MapLbs[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private workspaceService: WorkspaceLbsService,
        private mapService: MapLbsService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mapService.query()
            .subscribe((res: ResponseWrapper) => { this.maps = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.workspace.id !== undefined) {
            this.subscribeToSaveResponse(
                this.workspaceService.update(this.workspace));
        } else {
            this.subscribeToSaveResponse(
                this.workspaceService.create(this.workspace));
        }
    }

    private subscribeToSaveResponse(result: Observable<WorkspaceLbs>) {
        result.subscribe((res: WorkspaceLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: WorkspaceLbs) {
        this.eventManager.broadcast({ name: 'workspaceListModification', content: 'OK'});
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

    trackMapById(index: number, item: MapLbs) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-workspace-lbs-popup',
    template: ''
})
export class WorkspaceLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workspacePopupService: WorkspaceLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.workspacePopupService
                    .open(WorkspaceLbsDialogComponent as Component, params['id']);
            } else {
                this.workspacePopupService
                    .open(WorkspaceLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
