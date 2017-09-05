import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { POILbs } from './poi-lbs.model';
import { POILbsPopupService } from './poi-lbs-popup.service';
import { POILbsService } from './poi-lbs.service';

@Component({
    selector: 'jhi-poi-lbs-delete-dialog',
    templateUrl: './poi-lbs-delete-dialog.component.html'
})
export class POILbsDeleteDialogComponent {

    pOI: POILbs;

    constructor(
        private pOIService: POILbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pOIService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pOIListModification',
                content: 'Deleted an pOI'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-poi-lbs-delete-popup',
    template: ''
})
export class POILbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pOIPopupService: POILbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pOIPopupService
                .open(POILbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
