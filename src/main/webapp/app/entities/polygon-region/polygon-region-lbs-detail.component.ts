import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PolygonRegionLbs } from './polygon-region-lbs.model';
import { PolygonRegionLbsService } from './polygon-region-lbs.service';

@Component({
    selector: 'jhi-polygon-region-lbs-detail',
    templateUrl: './polygon-region-lbs-detail.component.html'
})
export class PolygonRegionLbsDetailComponent implements OnInit, OnDestroy {

    polygonRegion: PolygonRegionLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private polygonRegionService: PolygonRegionLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPolygonRegions();
    }

    load(id) {
        this.polygonRegionService.find(id).subscribe((polygonRegion) => {
            this.polygonRegion = polygonRegion;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPolygonRegions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'polygonRegionListModification',
            (response) => this.load(this.polygonRegion.id)
        );
    }
}
