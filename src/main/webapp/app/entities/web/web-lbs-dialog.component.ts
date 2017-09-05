import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { WebLbs } from './web-lbs.model';
import { WebLbsPopupService } from './web-lbs-popup.service';
import { WebLbsService } from './web-lbs.service';
import { WebTypeLbs, WebTypeLbsService } from '../web-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-web-lbs-dialog',
    templateUrl: './web-lbs-dialog.component.html'
})
export class WebLbsDialogComponent implements OnInit {

    web: WebLbs;
    isSaving: boolean;

    webtypes: WebTypeLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private webService: WebLbsService,
        private webTypeService: WebTypeLbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.webTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.webtypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.web.id !== undefined) {
            this.subscribeToSaveResponse(
                this.webService.update(this.web));
        } else {
            this.subscribeToSaveResponse(
                this.webService.create(this.web));
        }
    }

    private subscribeToSaveResponse(result: Observable<WebLbs>) {
        result.subscribe((res: WebLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: WebLbs) {
        this.eventManager.broadcast({ name: 'webListModification', content: 'OK'});
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

    trackWebTypeById(index: number, item: WebTypeLbs) {
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
    selector: 'jhi-web-lbs-popup',
    template: ''
})
export class WebLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private webPopupService: WebLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.webPopupService
                    .open(WebLbsDialogComponent as Component, params['id']);
            } else {
                this.webPopupService
                    .open(WebLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
