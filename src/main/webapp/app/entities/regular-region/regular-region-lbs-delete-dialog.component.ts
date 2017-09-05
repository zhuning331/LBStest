import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RegularRegionLbs } from './regular-region-lbs.model';
import { RegularRegionLbsPopupService } from './regular-region-lbs-popup.service';
import { RegularRegionLbsService } from './regular-region-lbs.service';

@Component({
    selector: 'jhi-regular-region-lbs-delete-dialog',
    templateUrl: './regular-region-lbs-delete-dialog.component.html'
})
export class RegularRegionLbsDeleteDialogComponent {

    regularRegion: RegularRegionLbs;

    constructor(
        private regularRegionService: RegularRegionLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.regularRegionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'regularRegionListModification',
                content: 'Deleted an regularRegion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-regular-region-lbs-delete-popup',
    template: ''
})
export class RegularRegionLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regularRegionPopupService: RegularRegionLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.regularRegionPopupService
                .open(RegularRegionLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
