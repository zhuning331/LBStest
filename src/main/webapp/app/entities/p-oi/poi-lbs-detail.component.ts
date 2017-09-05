import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { POILbs } from './poi-lbs.model';
import { POILbsService } from './poi-lbs.service';

@Component({
    selector: 'jhi-poi-lbs-detail',
    templateUrl: './poi-lbs-detail.component.html'
})
export class POILbsDetailComponent implements OnInit, OnDestroy {

    pOI: POILbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private pOIService: POILbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPOIS();
    }

    load(id) {
        this.pOIService.find(id).subscribe((pOI) => {
            this.pOI = pOI;
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

    registerChangeInPOIS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pOIListModification',
            (response) => this.load(this.pOI.id)
        );
    }
}
