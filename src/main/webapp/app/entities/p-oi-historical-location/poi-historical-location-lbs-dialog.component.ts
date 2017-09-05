import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { POIHistoricalLocationLbs } from './poi-historical-location-lbs.model';
import { POIHistoricalLocationLbsPopupService } from './poi-historical-location-lbs-popup.service';
import { POIHistoricalLocationLbsService } from './poi-historical-location-lbs.service';
import { POITypeLbs, POITypeLbsService } from '../p-oi-type';
import { POILbs, POILbsService } from '../p-oi';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-poi-historical-location-lbs-dialog',
    templateUrl: './poi-historical-location-lbs-dialog.component.html'
})
export class POIHistoricalLocationLbsDialogComponent implements OnInit {

    pOIHistoricalLocation: POIHistoricalLocationLbs;
    isSaving: boolean;

    poitypes: POITypeLbs[];

    pois: POILbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private pOIHistoricalLocationService: POIHistoricalLocationLbsService,
        private pOITypeService: POITypeLbsService,
        private pOIService: POILbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pOITypeService.query()
            .subscribe((res: ResponseWrapper) => { this.poitypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.pOIService.query()
            .subscribe((res: ResponseWrapper) => { this.pois = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pOIHistoricalLocation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pOIHistoricalLocationService.update(this.pOIHistoricalLocation));
        } else {
            this.subscribeToSaveResponse(
                this.pOIHistoricalLocationService.create(this.pOIHistoricalLocation));
        }
    }

    private subscribeToSaveResponse(result: Observable<POIHistoricalLocationLbs>) {
        result.subscribe((res: POIHistoricalLocationLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: POIHistoricalLocationLbs) {
        this.eventManager.broadcast({ name: 'pOIHistoricalLocationListModification', content: 'OK'});
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

    trackPOIById(index: number, item: POILbs) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-poi-historical-location-lbs-popup',
    template: ''
})
export class POIHistoricalLocationLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pOIHistoricalLocationPopupService: POIHistoricalLocationLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pOIHistoricalLocationPopupService
                    .open(POIHistoricalLocationLbsDialogComponent as Component, params['id']);
            } else {
                this.pOIHistoricalLocationPopupService
                    .open(POIHistoricalLocationLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
