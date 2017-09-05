import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { POITypeLbs } from './poi-type-lbs.model';
import { POITypeLbsService } from './poi-type-lbs.service';

@Component({
    selector: 'jhi-poi-type-lbs-detail',
    templateUrl: './poi-type-lbs-detail.component.html'
})
export class POITypeLbsDetailComponent implements OnInit, OnDestroy {

    pOIType: POITypeLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private pOITypeService: POITypeLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPOITypes();
    }

    load(id) {
        this.pOITypeService.find(id).subscribe((pOIType) => {
            this.pOIType = pOIType;
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

    registerChangeInPOITypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pOITypeListModification',
            (response) => this.load(this.pOIType.id)
        );
    }
}
