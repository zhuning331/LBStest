import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { WorkspaceConfigLbs } from './workspace-config-lbs.model';
import { WorkspaceConfigLbsService } from './workspace-config-lbs.service';

@Component({
    selector: 'jhi-workspace-config-lbs-detail',
    templateUrl: './workspace-config-lbs-detail.component.html'
})
export class WorkspaceConfigLbsDetailComponent implements OnInit, OnDestroy {

    workspaceConfig: WorkspaceConfigLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private workspaceConfigService: WorkspaceConfigLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWorkspaceConfigs();
    }

    load(id) {
        this.workspaceConfigService.find(id).subscribe((workspaceConfig) => {
            this.workspaceConfig = workspaceConfig;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWorkspaceConfigs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'workspaceConfigListModification',
            (response) => this.load(this.workspaceConfig.id)
        );
    }
}
