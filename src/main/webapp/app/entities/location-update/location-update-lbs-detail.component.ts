import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LocationUpdateLbs } from './location-update-lbs.model';
import { LocationUpdateLbsService } from './location-update-lbs.service';

@Component({
    selector: 'jhi-location-update-lbs-detail',
    templateUrl: './location-update-lbs-detail.component.html'
})
export class LocationUpdateLbsDetailComponent implements OnInit, OnDestroy {

    locationUpdate: LocationUpdateLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private locationUpdateService: LocationUpdateLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLocationUpdates();
    }

    load(id) {
        this.locationUpdateService.find(id).subscribe((locationUpdate) => {
            this.locationUpdate = locationUpdate;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLocationUpdates() {
        this.eventSubscriber = this.eventManager.subscribe(
            'locationUpdateListModification',
            (response) => this.load(this.locationUpdate.id)
        );
    }
}
