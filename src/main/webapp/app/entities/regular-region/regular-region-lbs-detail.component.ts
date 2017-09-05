import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { RegularRegionLbs } from './regular-region-lbs.model';
import { RegularRegionLbsService } from './regular-region-lbs.service';

@Component({
    selector: 'jhi-regular-region-lbs-detail',
    templateUrl: './regular-region-lbs-detail.component.html'
})
export class RegularRegionLbsDetailComponent implements OnInit, OnDestroy {

    regularRegion: RegularRegionLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private regularRegionService: RegularRegionLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRegularRegions();
    }

    load(id) {
        this.regularRegionService.find(id).subscribe((regularRegion) => {
            this.regularRegion = regularRegion;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInRegularRegions() {
        this.eventSubscriber = this.eventManager.subscribe(
            'regularRegionListModification',
            (response) => this.load(this.regularRegion.id)
        );
    }
}
