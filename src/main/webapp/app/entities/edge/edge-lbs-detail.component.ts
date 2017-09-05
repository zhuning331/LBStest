import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EdgeLbs } from './edge-lbs.model';
import { EdgeLbsService } from './edge-lbs.service';

@Component({
    selector: 'jhi-edge-lbs-detail',
    templateUrl: './edge-lbs-detail.component.html'
})
export class EdgeLbsDetailComponent implements OnInit, OnDestroy {

    edge: EdgeLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private edgeService: EdgeLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEdges();
    }

    load(id) {
        this.edgeService.find(id).subscribe((edge) => {
            this.edge = edge;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEdges() {
        this.eventSubscriber = this.eventManager.subscribe(
            'edgeListModification',
            (response) => this.load(this.edge.id)
        );
    }
}
