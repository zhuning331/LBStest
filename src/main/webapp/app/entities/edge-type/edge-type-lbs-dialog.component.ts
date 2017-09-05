import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EdgeTypeLbs } from './edge-type-lbs.model';
import { EdgeTypeLbsPopupService } from './edge-type-lbs-popup.service';
import { EdgeTypeLbsService } from './edge-type-lbs.service';

@Component({
    selector: 'jhi-edge-type-lbs-dialog',
    templateUrl: './edge-type-lbs-dialog.component.html'
})
export class EdgeTypeLbsDialogComponent implements OnInit {

    edgeType: EdgeTypeLbs;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private edgeTypeService: EdgeTypeLbsService,
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
        if (this.edgeType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.edgeTypeService.update(this.edgeType));
        } else {
            this.subscribeToSaveResponse(
                this.edgeTypeService.create(this.edgeType));
        }
    }

    private subscribeToSaveResponse(result: Observable<EdgeTypeLbs>) {
        result.subscribe((res: EdgeTypeLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EdgeTypeLbs) {
        this.eventManager.broadcast({ name: 'edgeTypeListModification', content: 'OK'});
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
    selector: 'jhi-edge-type-lbs-popup',
    template: ''
})
export class EdgeTypeLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private edgeTypePopupService: EdgeTypeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.edgeTypePopupService
                    .open(EdgeTypeLbsDialogComponent as Component, params['id']);
            } else {
                this.edgeTypePopupService
                    .open(EdgeTypeLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
