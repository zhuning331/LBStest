import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LocationUpdateLbs } from './location-update-lbs.model';
import { LocationUpdateLbsPopupService } from './location-update-lbs-popup.service';
import { LocationUpdateLbsService } from './location-update-lbs.service';

@Component({
    selector: 'jhi-location-update-lbs-dialog',
    templateUrl: './location-update-lbs-dialog.component.html'
})
export class LocationUpdateLbsDialogComponent implements OnInit {

    locationUpdate: LocationUpdateLbs;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private locationUpdateService: LocationUpdateLbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.locationUpdate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.locationUpdateService.update(this.locationUpdate));
        } else {
            this.subscribeToSaveResponse(
                this.locationUpdateService.create(this.locationUpdate));
        }
    }

    private subscribeToSaveResponse(result: Observable<LocationUpdateLbs>) {
        result.subscribe((res: LocationUpdateLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: LocationUpdateLbs) {
        this.eventManager.broadcast({ name: 'locationUpdateListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-location-update-lbs-popup',
    template: ''
})
export class LocationUpdateLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationUpdatePopupService: LocationUpdateLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.locationUpdatePopupService
                    .open(LocationUpdateLbsDialogComponent as Component, params['id']);
            } else {
                this.locationUpdatePopupService
                    .open(LocationUpdateLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
