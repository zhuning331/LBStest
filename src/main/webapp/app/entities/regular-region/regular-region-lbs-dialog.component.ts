import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RegularRegionLbs } from './regular-region-lbs.model';
import { RegularRegionLbsPopupService } from './regular-region-lbs-popup.service';
import { RegularRegionLbsService } from './regular-region-lbs.service';
import { RegionLbs, RegionLbsService } from '../region';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-regular-region-lbs-dialog',
    templateUrl: './regular-region-lbs-dialog.component.html'
})
export class RegularRegionLbsDialogComponent implements OnInit {

    regularRegion: RegularRegionLbs;
    isSaving: boolean;

    regions: RegionLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private regularRegionService: RegularRegionLbsService,
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
        if (this.regularRegion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.regularRegionService.update(this.regularRegion));
        } else {
            this.subscribeToSaveResponse(
                this.regularRegionService.create(this.regularRegion));
        }
    }

    private subscribeToSaveResponse(result: Observable<RegularRegionLbs>) {
        result.subscribe((res: RegularRegionLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RegularRegionLbs) {
        this.eventManager.broadcast({ name: 'regularRegionListModification', content: 'OK'});
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
    selector: 'jhi-regular-region-lbs-popup',
    template: ''
})
export class RegularRegionLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regularRegionPopupService: RegularRegionLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.regularRegionPopupService
                    .open(RegularRegionLbsDialogComponent as Component, params['id']);
            } else {
                this.regularRegionPopupService
                    .open(RegularRegionLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
