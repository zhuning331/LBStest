import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { POIHistoricalLocationLbs } from './poi-historical-location-lbs.model';
import { POIHistoricalLocationLbsService } from './poi-historical-location-lbs.service';

@Component({
    selector: 'jhi-poi-historical-location-lbs-detail',
    templateUrl: './poi-historical-location-lbs-detail.component.html'
})
export class POIHistoricalLocationLbsDetailComponent implements OnInit, OnDestroy {

    pOIHistoricalLocation: POIHistoricalLocationLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pOIHistoricalLocationService: POIHistoricalLocationLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPOIHistoricalLocations();
    }

    load(id) {
        this.pOIHistoricalLocationService.find(id).subscribe((pOIHistoricalLocation) => {
            this.pOIHistoricalLocation = pOIHistoricalLocation;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPOIHistoricalLocations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pOIHistoricalLocationListModification',
            (response) => this.load(this.pOIHistoricalLocation.id)
        );
    }
}
