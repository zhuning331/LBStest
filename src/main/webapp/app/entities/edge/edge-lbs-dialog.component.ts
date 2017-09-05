import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EdgeLbs } from './edge-lbs.model';
import { EdgeLbsPopupService } from './edge-lbs-popup.service';
import { EdgeLbsService } from './edge-lbs.service';
import { NodeLbs, NodeLbsService } from '../node';
import { EdgeTypeLbs, EdgeTypeLbsService } from '../edge-type';
import { WebLbs, WebLbsService } from '../web';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-edge-lbs-dialog',
    templateUrl: './edge-lbs-dialog.component.html'
})
export class EdgeLbsDialogComponent implements OnInit {

    edge: EdgeLbs;
    isSaving: boolean;

    nodes: NodeLbs[];

    edgetypes: EdgeTypeLbs[];

    webs: WebLbs[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private edgeService: EdgeLbsService,
        private nodeService: NodeLbsService,
        private edgeTypeService: EdgeTypeLbsService,
        private webService: WebLbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.nodeService.query()
            .subscribe((res: ResponseWrapper) => { this.nodes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.edgeTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.edgetypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.webService.query()
            .subscribe((res: ResponseWrapper) => { this.webs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.edge.id !== undefined) {
            this.subscribeToSaveResponse(
                this.edgeService.update(this.edge));
        } else {
            this.subscribeToSaveResponse(
                this.edgeService.create(this.edge));
        }
    }

    private subscribeToSaveResponse(result: Observable<EdgeLbs>) {
        result.subscribe((res: EdgeLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EdgeLbs) {
        this.eventManager.broadcast({ name: 'edgeListModification', content: 'OK'});
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

    trackNodeById(index: number, item: NodeLbs) {
        return item.id;
    }

    trackEdgeTypeById(index: number, item: EdgeTypeLbs) {
        return item.id;
    }

    trackWebById(index: number, item: WebLbs) {
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
    selector: 'jhi-edge-lbs-popup',
    template: ''
})
export class EdgeLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private edgePopupService: EdgeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.edgePopupService
                    .open(EdgeLbsDialogComponent as Component, params['id']);
            } else {
                this.edgePopupService
                    .open(EdgeLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
