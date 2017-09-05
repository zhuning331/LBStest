import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ServiceConfigLbs } from './service-config-lbs.model';
import { ServiceConfigLbsService } from './service-config-lbs.service';

@Component({
    selector: 'jhi-service-config-lbs-detail',
    templateUrl: './service-config-lbs-detail.component.html'
})
export class ServiceConfigLbsDetailComponent implements OnInit, OnDestroy {

    serviceConfig: ServiceConfigLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private serviceConfigService: ServiceConfigLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInServiceConfigs();
    }

    load(id) {
        this.serviceConfigService.find(id).subscribe((serviceConfig) => {
            this.serviceConfig = serviceConfig;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInServiceConfigs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'serviceConfigListModification',
            (response) => this.load(this.serviceConfig.id)
        );
    }
}
