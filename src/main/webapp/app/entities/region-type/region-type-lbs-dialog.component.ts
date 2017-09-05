import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { RegionTypeLbs } from './region-type-lbs.model';
import { RegionTypeLbsPopupService } from './region-type-lbs-popup.service';
import { RegionTypeLbsService } from './region-type-lbs.service';
import { WorkspaceLbs, WorkspaceLbsService } from '../workspace';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-region-type-lbs-dialog',
    templateUrl: './region-type-lbs-dialog.component.html'
})
export class RegionTypeLbsDialogComponent implements OnInit {

    regionType: RegionTypeLbs;
    isSaving: boolean;

    workspaces: WorkspaceLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private regionTypeService: RegionTypeLbsService,
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
        this.dataUtils.clearInputImage(this.regionType, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.regionType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.regionTypeService.update(this.regionType));
        } else {
            this.subscribeToSaveResponse(
                this.regionTypeService.create(this.regionType));
        }
    }

    private subscribeToSaveResponse(result: Observable<RegionTypeLbs>) {
        result.subscribe((res: RegionTypeLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RegionTypeLbs) {
        this.eventManager.broadcast({ name: 'regionTypeListModification', content: 'OK'});
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
    selector: 'jhi-region-type-lbs-popup',
    template: ''
})
export class RegionTypeLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regionTypePopupService: RegionTypeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.regionTypePopupService
                    .open(RegionTypeLbsDialogComponent as Component, params['id']);
            } else {
                this.regionTypePopupService
                    .open(RegionTypeLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
