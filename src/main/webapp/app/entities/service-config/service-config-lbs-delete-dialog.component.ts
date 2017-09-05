import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ServiceConfigLbs } from './service-config-lbs.model';
import { ServiceConfigLbsPopupService } from './service-config-lbs-popup.service';
import { ServiceConfigLbsService } from './service-config-lbs.service';

@Component({
    selector: 'jhi-service-config-lbs-delete-dialog',
    templateUrl: './service-config-lbs-delete-dialog.component.html'
})
export class ServiceConfigLbsDeleteDialogComponent {

    serviceConfig: ServiceConfigLbs;

    constructor(
        private serviceConfigService: ServiceConfigLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.serviceConfigService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'serviceConfigListModification',
                content: 'Deleted an serviceConfig'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-service-config-lbs-delete-popup',
    template: ''
})
export class ServiceConfigLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private serviceConfigPopupService: ServiceConfigLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.serviceConfigPopupService
                .open(ServiceConfigLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
