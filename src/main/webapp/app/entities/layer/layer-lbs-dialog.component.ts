import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { LayerLbs } from './layer-lbs.model';
import { LayerLbsPopupService } from './layer-lbs-popup.service';
import { LayerLbsService } from './layer-lbs.service';

@Component({
    selector: 'jhi-layer-lbs-dialog',
    templateUrl: './layer-lbs-dialog.component.html'
})
export class LayerLbsDialogComponent implements OnInit {

    layer: LayerLbs;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private layerService: LayerLbsService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
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
        this.dataUtils.clearInputImage(this.layer, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.layer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.layerService.update(this.layer));
        } else {
            this.subscribeToSaveResponse(
                this.layerService.create(this.layer));
        }
    }

    private subscribeToSaveResponse(result: Observable<LayerLbs>) {
        result.subscribe((res: LayerLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: LayerLbs) {
        this.eventManager.broadcast({ name: 'layerListModification', content: 'OK'});
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
    selector: 'jhi-layer-lbs-popup',
    template: ''
})
export class LayerLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private layerPopupService: LayerLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.layerPopupService
                    .open(LayerLbsDialogComponent as Component, params['id']);
            } else {
                this.layerPopupService
                    .open(LayerLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
