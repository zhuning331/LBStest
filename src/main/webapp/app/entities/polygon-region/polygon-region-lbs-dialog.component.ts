import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PolygonRegionLbs } from './polygon-region-lbs.model';
import { PolygonRegionLbsPopupService } from './polygon-region-lbs-popup.service';
import { PolygonRegionLbsService } from './polygon-region-lbs.service';
import { RegionLbs, RegionLbsService } from '../region';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-polygon-region-lbs-dialog',
    templateUrl: './polygon-region-lbs-dialog.component.html'
})
export class PolygonRegionLbsDialogComponent implements OnInit {

    polygonRegion: PolygonRegionLbs;
    isSaving: boolean;

    regions: RegionLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private polygonRegionService: PolygonRegionLbsService,
        private regionService: RegionLbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.regionService.query()
            .subscribe((res: ResponseWrapper) => { this.regions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.polygonRegion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.polygonRegionService.update(this.polygonRegion));
        } else {
            this.subscribeToSaveResponse(
                this.polygonRegionService.create(this.polygonRegion));
        }
    }

    private subscribeToSaveResponse(result: Observable<PolygonRegionLbs>) {
        result.subscribe((res: PolygonRegionLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PolygonRegionLbs) {
        this.eventManager.broadcast({ name: 'polygonRegionListModification', content: 'OK'});
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

    trackRegionById(index: number, item: RegionLbs) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-polygon-region-lbs-popup',
    template: ''
})
export class PolygonRegionLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private polygonRegionPopupService: PolygonRegionLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.polygonRegionPopupService
                    .open(PolygonRegionLbsDialogComponent as Component, params['id']);
            } else {
                this.polygonRegionPopupService
                    .open(PolygonRegionLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
