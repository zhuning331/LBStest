import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { POILbs } from './poi-lbs.model';
import { POILbsPopupService } from './poi-lbs-popup.service';
import { POILbsService } from './poi-lbs.service';
import { POITypeLbs, POITypeLbsService } from '../p-oi-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-poi-lbs-dialog',
    templateUrl: './poi-lbs-dialog.component.html'
})
export class POILbsDialogComponent implements OnInit {

    pOI: POILbs;
    isSaving: boolean;

    poitypes: POITypeLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private pOIService: POILbsService,
        private pOITypeService: POITypeLbsService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pOITypeService.query()
            .subscribe((res: ResponseWrapper) => { this.poitypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        this.dataUtils.clearInputImage(this.pOI, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pOI.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pOIService.update(this.pOI));
        } else {
            this.subscribeToSaveResponse(
                this.pOIService.create(this.pOI));
        }
    }

    private subscribeToSaveResponse(result: Observable<POILbs>) {
        result.subscribe((res: POILbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: POILbs) {
        this.eventManager.broadcast({ name: 'pOIListModification', content: 'OK'});
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

    trackPOITypeById(index: number, item: POITypeLbs) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-poi-lbs-popup',
    template: ''
})
export class POILbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pOIPopupService: POILbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pOIPopupService
                    .open(POILbsDialogComponent as Component, params['id']);
            } else {
                this.pOIPopupService
                    .open(POILbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
