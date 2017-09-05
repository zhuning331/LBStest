import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { NodeLbs } from './node-lbs.model';
import { NodeLbsService } from './node-lbs.service';

@Component({
    selector: 'jhi-node-lbs-detail',
    templateUrl: './node-lbs-detail.component.html'
})
export class NodeLbsDetailComponent implements OnInit, OnDestroy {

    node: NodeLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private nodeService: NodeLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInNodes();
    }

    load(id) {
        this.nodeService.find(id).subscribe((node) => {
            this.node = node;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInNodes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'nodeListModification',
            (response) => this.load(this.node.id)
        );
    }
}
