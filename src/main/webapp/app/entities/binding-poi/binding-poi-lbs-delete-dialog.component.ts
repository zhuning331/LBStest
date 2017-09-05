import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BindingPOILbs } from './binding-poi-lbs.model';
import { BindingPOILbsPopupService } from './binding-poi-lbs-popup.service';
import { BindingPOILbsService } from './binding-poi-lbs.service';

@Component({
    selector: 'jhi-binding-poi-lbs-delete-dialog',
    templateUrl: './binding-poi-lbs-delete-dialog.component.html'
})
export class BindingPOILbsDeleteDialogComponent {

    bindingPOI: BindingPOILbs;

    constructor(
        private bindingPOIService: BindingPOILbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bindingPOIService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bindingPOIListModification',
                content: 'Deleted an bindingPOI'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-binding-poi-lbs-delete-popup',
    template: ''
})
export class BindingPOILbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bindingPOIPopupService: BindingPOILbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bindingPOIPopupService
                .open(BindingPOILbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
