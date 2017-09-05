import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { LayerLbs } from './layer-lbs.model';
import { LayerLbsService } from './layer-lbs.service';

@Component({
    selector: 'jhi-layer-lbs-detail',
    templateUrl: './layer-lbs-detail.component.html'
})
export class LayerLbsDetailComponent implements OnInit, OnDestroy {

    layer: LayerLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private layerService: LayerLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLayers();
    }

    load(id) {
        this.layerService.find(id).subscribe((layer) => {
            this.layer = layer;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLayers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'layerListModification',
            (response) => this.load(this.layer.id)
        );
    }
}
