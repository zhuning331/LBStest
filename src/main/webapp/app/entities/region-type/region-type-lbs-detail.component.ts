import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { RegionTypeLbs } from './region-type-lbs.model';
import { RegionTypeLbsService } from './region-type-lbs.service';

@Component({
    selector: 'jhi-region-type-lbs-detail',
    templateUrl: './region-type-lbs-detail.component.html'
})
export class RegionTypeLbsDetailComponent implements OnInit, OnDestroy {

    regionType: RegionTypeLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private regionTypeService: RegionTypeLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInRegionTypes();
    }

    load(id) {
        this.regionTypeService.find(id).subscribe((regionType) => {
            this.regionType = regionType;
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

    registerChangeInRegionTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'regionTypeListModification',
            (response) => this.load(this.regionType.id)
        );
    }
}
