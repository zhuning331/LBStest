import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { POIHistoricalLocationLbs } from './poi-historical-location-lbs.model';
import { POIHistoricalLocationLbsPopupService } from './poi-historical-location-lbs-popup.service';
import { POIHistoricalLocationLbsService } from './poi-historical-location-lbs.service';

@Component({
    selector: 'jhi-poi-historical-location-lbs-delete-dialog',
    templateUrl: './poi-historical-location-lbs-delete-dialog.component.html'
})
export class POIHistoricalLocationLbsDeleteDialogComponent {

    pOIHistoricalLocation: POIHistoricalLocationLbs;

    constructor(
        private pOIHistoricalLocationService: POIHistoricalLocationLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pOIHistoricalLocationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pOIHistoricalLocationListModification',
                content: 'Deleted an pOIHistoricalLocation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-poi-historical-location-lbs-delete-popup',
    template: ''
})
export class POIHistoricalLocationLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pOIHistoricalLocationPopupService: POIHistoricalLocationLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pOIHistoricalLocationPopupService
                .open(POIHistoricalLocationLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
