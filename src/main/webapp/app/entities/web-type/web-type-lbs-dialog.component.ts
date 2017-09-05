import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WebTypeLbs } from './web-type-lbs.model';
import { WebTypeLbsPopupService } from './web-type-lbs-popup.service';
import { WebTypeLbsService } from './web-type-lbs.service';
import { WorkspaceLbs, WorkspaceLbsService } from '../workspace';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-web-type-lbs-dialog',
    templateUrl: './web-type-lbs-dialog.component.html'
})
export class WebTypeLbsDialogComponent implements OnInit {

    webType: WebTypeLbs;
    isSaving: boolean;

    workspaces: WorkspaceLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private webTypeService: WebTypeLbsService,
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
        if (this.webType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.webTypeService.update(this.webType));
        } else {
            this.subscribeToSaveResponse(
                this.webTypeService.create(this.webType));
        }
    }

    private subscribeToSaveResponse(result: Observable<WebTypeLbs>) {
        result.subscribe((res: WebTypeLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: WebTypeLbs) {
        this.eventManager.broadcast({ name: 'webTypeListModification', content: 'OK'});
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
    selector: 'jhi-web-type-lbs-popup',
    template: ''
})
export class WebTypeLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private webTypePopupService: WebTypeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.webTypePopupService
                    .open(WebTypeLbsDialogComponent as Component, params['id']);
            } else {
                this.webTypePopupService
                    .open(WebTypeLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
