import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ServiceConfigLbs } from './service-config-lbs.model';
import { ServiceConfigLbsPopupService } from './service-config-lbs-popup.service';
import { ServiceConfigLbsService } from './service-config-lbs.service';

@Component({
    selector: 'jhi-service-config-lbs-dialog',
    templateUrl: './service-config-lbs-dialog.component.html'
})
export class ServiceConfigLbsDialogComponent implements OnInit {

    serviceConfig: ServiceConfigLbs;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private serviceConfigService: ServiceConfigLbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.serviceConfig.id !== undefined) {
            this.subscribeToSaveResponse(
                this.serviceConfigService.update(this.serviceConfig));
        } else {
            this.subscribeToSaveResponse(
                this.serviceConfigService.create(this.serviceConfig));
        }
    }

    private subscribeToSaveResponse(result: Observable<ServiceConfigLbs>) {
        result.subscribe((res: ServiceConfigLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ServiceConfigLbs) {
        this.eventManager.broadcast({ name: 'serviceConfigListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-service-config-lbs-popup',
    template: ''
})
export class ServiceConfigLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private serviceConfigPopupService: ServiceConfigLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.serviceConfigPopupService
                    .open(ServiceConfigLbsDialogComponent as Component, params['id']);
            } else {
                this.serviceConfigPopupService
                    .open(ServiceConfigLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
