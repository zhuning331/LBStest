import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { POITypeLbs } from './poi-type-lbs.model';
import { POITypeLbsPopupService } from './poi-type-lbs-popup.service';
import { POITypeLbsService } from './poi-type-lbs.service';
import { WorkspaceLbs, WorkspaceLbsService } from '../workspace';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-poi-type-lbs-dialog',
    templateUrl: './poi-type-lbs-dialog.component.html'
})
export class POITypeLbsDialogComponent implements OnInit {

    pOIType: POITypeLbs;
    isSaving: boolean;

    workspaces: WorkspaceLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private pOITypeService: POITypeLbsService,
        private workspaceService: WorkspaceLbsService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.workspaceService.query()
            .subscribe((res: ResponseWrapper) => { this.workspaces = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.pOIType, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pOIType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pOITypeService.update(this.pOIType));
        } else {
            this.subscribeToSaveResponse(
                this.pOITypeService.create(this.pOIType));
        }
    }

    private subscribeToSaveResponse(result: Observable<POITypeLbs>) {
        result.subscribe((res: POITypeLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: POITypeLbs) {
        this.eventManager.broadcast({ name: 'pOITypeListModification', content: 'OK'});
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
    selector: 'jhi-poi-type-lbs-popup',
    template: ''
})
export class POITypeLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pOITypePopupService: POITypeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pOITypePopupService
                    .open(POITypeLbsDialogComponent as Component, params['id']);
            } else {
                this.pOITypePopupService
                    .open(POITypeLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
