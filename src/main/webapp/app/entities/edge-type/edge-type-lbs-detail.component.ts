import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EdgeTypeLbs } from './edge-type-lbs.model';
import { EdgeTypeLbsService } from './edge-type-lbs.service';

@Component({
    selector: 'jhi-edge-type-lbs-detail',
    templateUrl: './edge-type-lbs-detail.component.html'
})
export class EdgeTypeLbsDetailComponent implements OnInit, OnDestroy {

    edgeType: EdgeTypeLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private edgeTypeService: EdgeTypeLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEdgeTypes();
    }

    load(id) {
        this.edgeTypeService.find(id).subscribe((edgeType) => {
            this.edgeType = edgeType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEdgeTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'edgeTypeListModification',
            (response) => this.load(this.edgeType.id)
        );
    }
}
