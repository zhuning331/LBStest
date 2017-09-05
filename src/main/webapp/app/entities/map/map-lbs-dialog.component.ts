import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { MapLbs } from './map-lbs.model';
import { MapLbsPopupService } from './map-lbs-popup.service';
import { MapLbsService } from './map-lbs.service';
import { WorkspaceLbs, WorkspaceLbsService } from '../workspace';
import { LayerLbs, LayerLbsService } from '../layer';
import { WebLbs, WebLbsService } from '../web';
import { RegionLbs, RegionLbsService } from '../region';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-map-lbs-dialog',
    templateUrl: './map-lbs-dialog.component.html'
})
export class MapLbsDialogComponent implements OnInit {

    map: MapLbs;
    isSaving: boolean;

    workspaces: WorkspaceLbs[];

    layers: LayerLbs[];

    webs: WebLbs[];

    regions: RegionLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private mapService: MapLbsService,
        private workspaceService: WorkspaceLbsService,
        private layerService: LayerLbsService,
        private webService: WebLbsService,
        private regionService: RegionLbsService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.workspaceService.query()
            .subscribe((res: ResponseWrapper) => { this.workspaces = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.layerService.query()
            .subscribe((res: ResponseWrapper) => { this.layers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.webService.query()
            .subscribe((res: ResponseWrapper) => { this.webs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.regionService.query()
            .subscribe((res: ResponseWrapper) => { this.regions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        this.dataUtils.clearInputImage(this.map, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.map.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mapService.update(this.map));
        } else {
            this.subscribeToSaveResponse(
                this.mapService.create(this.map));
        }
    }

    private subscribeToSaveResponse(result: Observable<MapLbs>) {
        result.subscribe((res: MapLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: MapLbs) {
        this.eventManager.broadcast({ name: 'mapListModification', content: 'OK'});
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

    trackWorkspaceById(index: number, item: WorkspaceLbs) {
        return item.id;
    }

    trackLayerById(index: number, item: LayerLbs) {
        return item.id;
    }

    trackWebById(index: number, item: WebLbs) {
        return item.id;
    }

    trackRegionById(index: number, item: RegionLbs) {
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
    selector: 'jhi-map-lbs-popup',
    template: ''
})
export class MapLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mapPopupService: MapLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mapPopupService
                    .open(MapLbsDialogComponent as Component, params['id']);
            } else {
                this.mapPopupService
                    .open(MapLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
