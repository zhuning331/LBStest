import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { WebLbs } from './web-lbs.model';
import { WebLbsService } from './web-lbs.service';

@Component({
    selector: 'jhi-web-lbs-detail',
    templateUrl: './web-lbs-detail.component.html'
})
export class WebLbsDetailComponent implements OnInit, OnDestroy {

    web: WebLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private webService: WebLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWebs();
    }

    load(id) {
        this.webService.find(id).subscribe((web) => {
            this.web = web;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWebs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'webListModification',
            (response) => this.load(this.web.id)
        );
    }
}
