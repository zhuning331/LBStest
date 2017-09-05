import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { RegionLbs } from './region-lbs.model';
import { RegionLbsPopupService } from './region-lbs-popup.service';
import { RegionLbsService } from './region-lbs.service';
import { RegionTypeLbs, RegionTypeLbsService } from '../region-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-region-lbs-dialog',
    templateUrl: './region-lbs-dialog.component.html'
})
export class RegionLbsDialogComponent implements OnInit {

    region: RegionLbs;
    isSaving: boolean;

    regiontypes: RegionTypeLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private regionService: RegionLbsService,
        private regionTypeService: RegionTypeLbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.regionTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.regiontypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.region.id !== undefined) {
            this.subscribeToSaveResponse(
                this.regionService.update(this.region));
        } else {
            this.subscribeToSaveResponse(
                this.regionService.create(this.region));
        }
    }

    private subscribeToSaveResponse(result: Observable<RegionLbs>) {
        result.subscribe((res: RegionLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: RegionLbs) {
        this.eventManager.broadcast({ name: 'regionListModification', content: 'OK'});
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

    trackRegionTypeById(index: number, item: RegionTypeLbs) {
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
    selector: 'jhi-region-lbs-popup',
    template: ''
})
export class RegionLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regionPopupService: RegionLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.regionPopupService
                    .open(RegionLbsDialogComponent as Component, params['id']);
            } else {
                this.regionPopupService
                    .open(RegionLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
