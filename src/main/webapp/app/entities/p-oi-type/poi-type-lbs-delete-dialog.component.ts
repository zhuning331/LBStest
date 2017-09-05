import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { POITypeLbs } from './poi-type-lbs.model';
import { POITypeLbsPopupService } from './poi-type-lbs-popup.service';
import { POITypeLbsService } from './poi-type-lbs.service';

@Component({
    selector: 'jhi-poi-type-lbs-delete-dialog',
    templateUrl: './poi-type-lbs-delete-dialog.component.html'
})
export class POITypeLbsDeleteDialogComponent {

    pOIType: POITypeLbs;

    constructor(
        private pOITypeService: POITypeLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pOITypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pOITypeListModification',
                content: 'Deleted an pOIType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-poi-type-lbs-delete-popup',
    template: ''
})
export class POITypeLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pOITypePopupService: POITypeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pOITypePopupService
                .open(POITypeLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
