import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LocationUpdateLbs } from './location-update-lbs.model';
import { LocationUpdateLbsPopupService } from './location-update-lbs-popup.service';
import { LocationUpdateLbsService } from './location-update-lbs.service';

@Component({
    selector: 'jhi-location-update-lbs-delete-dialog',
    templateUrl: './location-update-lbs-delete-dialog.component.html'
})
export class LocationUpdateLbsDeleteDialogComponent {

    locationUpdate: LocationUpdateLbs;

    constructor(
        private locationUpdateService: LocationUpdateLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.locationUpdateService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'locationUpdateListModification',
                content: 'Deleted an locationUpdate'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-location-update-lbs-delete-popup',
    template: ''
})
export class LocationUpdateLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private locationUpdatePopupService: LocationUpdateLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.locationUpdatePopupService
                .open(LocationUpdateLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
