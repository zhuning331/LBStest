import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BindingPOILbs } from './binding-poi-lbs.model';
import { BindingPOILbsService } from './binding-poi-lbs.service';

@Component({
    selector: 'jhi-binding-poi-lbs-detail',
    templateUrl: './binding-poi-lbs-detail.component.html'
})
export class BindingPOILbsDetailComponent implements OnInit, OnDestroy {

    bindingPOI: BindingPOILbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bindingPOIService: BindingPOILbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBindingPOIS();
    }

    load(id) {
        this.bindingPOIService.find(id).subscribe((bindingPOI) => {
            this.bindingPOI = bindingPOI;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBindingPOIS() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bindingPOIListModification',
            (response) => this.load(this.bindingPOI.id)
        );
    }
}
