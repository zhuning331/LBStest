import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RegionLbs } from './region-lbs.model';
import { RegionLbsService } from './region-lbs.service';

@Component({
    selector: 'jhi-region-lbs-detail',
    templateUrl: './region-lbs-detail.component.html'
})
export class RegionLbsDetailComponent implements OnInit, OnDestroy {

    region: RegionLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private regionService: RegionLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRegions();
    }

    load(id) {
        this.regionService.find(id).subscribe((region) => {
            this.region = region;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRegions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'regionListModification',
            (response) => this.load(this.region.id)
        );
    }
}
