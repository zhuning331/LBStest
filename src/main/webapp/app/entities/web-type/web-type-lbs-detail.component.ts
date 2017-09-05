import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { WebTypeLbs } from './web-type-lbs.model';
import { WebTypeLbsService } from './web-type-lbs.service';

@Component({
    selector: 'jhi-web-type-lbs-detail',
    templateUrl: './web-type-lbs-detail.component.html'
})
export class WebTypeLbsDetailComponent implements OnInit, OnDestroy {

    webType: WebTypeLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private webTypeService: WebTypeLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWebTypes();
    }

    load(id) {
        this.webTypeService.find(id).subscribe((webType) => {
            this.webType = webType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWebTypes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'webTypeListModification',
            (response) => this.load(this.webType.id)
        );
    }
}
