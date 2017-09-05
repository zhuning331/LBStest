import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BindingPOILbs } from './binding-poi-lbs.model';
import { BindingPOILbsPopupService } from './binding-poi-lbs-popup.service';
import { BindingPOILbsService } from './binding-poi-lbs.service';
import { POILbs, POILbsService } from '../p-oi';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-binding-poi-lbs-dialog',
    templateUrl: './binding-poi-lbs-dialog.component.html'
})
export class BindingPOILbsDialogComponent implements OnInit {

    bindingPOI: BindingPOILbs;
    isSaving: boolean;

    pois: POILbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private bindingPOIService: BindingPOILbsService,
        private pOIService: POILbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.pOIService.query()
            .subscribe((res: ResponseWrapper) => { this.pois = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bindingPOI.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bindingPOIService.update(this.bindingPOI));
        } else {
            this.subscribeToSaveResponse(
                this.bindingPOIService.create(this.bindingPOI));
        }
    }

    private subscribeToSaveResponse(result: Observable<BindingPOILbs>) {
        result.subscribe((res: BindingPOILbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BindingPOILbs) {
        this.eventManager.broadcast({ name: 'bindingPOIListModification', content: 'OK'});
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

    trackPOIById(index: number, item: POILbs) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-binding-poi-lbs-popup',
    template: ''
})
export class BindingPOILbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bindingPOIPopupService: BindingPOILbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bindingPOIPopupService
                    .open(BindingPOILbsDialogComponent as Component, params['id']);
            } else {
                this.bindingPOIPopupService
                    .open(BindingPOILbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
