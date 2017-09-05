import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { NodeLbs } from './node-lbs.model';
import { NodeLbsPopupService } from './node-lbs-popup.service';
import { NodeLbsService } from './node-lbs.service';
import { PolygonRegionLbs, PolygonRegionLbsService } from '../polygon-region';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-node-lbs-dialog',
    templateUrl: './node-lbs-dialog.component.html'
})
export class NodeLbsDialogComponent implements OnInit {

    node: NodeLbs;
    isSaving: boolean;

    polygonregions: PolygonRegionLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private nodeService: NodeLbsService,
        private polygonRegionService: PolygonRegionLbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.polygonRegionService.query()
            .subscribe((res: ResponseWrapper) => { this.polygonregions = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.node.id !== undefined) {
            this.subscribeToSaveResponse(
                this.nodeService.update(this.node));
        } else {
            this.subscribeToSaveResponse(
                this.nodeService.create(this.node));
        }
    }

    private subscribeToSaveResponse(result: Observable<NodeLbs>) {
        result.subscribe((res: NodeLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: NodeLbs) {
        this.eventManager.broadcast({ name: 'nodeListModification', content: 'OK'});
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

    trackPolygonRegionById(index: number, item: PolygonRegionLbs) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-node-lbs-popup',
    template: ''
})
export class NodeLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nodePopupService: NodeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.nodePopupService
                    .open(NodeLbsDialogComponent as Component, params['id']);
            } else {
                this.nodePopupService
                    .open(NodeLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
