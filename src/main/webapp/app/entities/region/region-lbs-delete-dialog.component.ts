import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RegionLbs } from './region-lbs.model';
import { RegionLbsPopupService } from './region-lbs-popup.service';
import { RegionLbsService } from './region-lbs.service';

@Component({
    selector: 'jhi-region-lbs-delete-dialog',
    templateUrl: './region-lbs-delete-dialog.component.html'
})
export class RegionLbsDeleteDialogComponent {

    region: RegionLbs;

    constructor(
        private regionService: RegionLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.regionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'regionListModification',
                content: 'Deleted an region'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-region-lbs-delete-popup',
    template: ''
})
export class RegionLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regionPopupService: RegionLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.regionPopupService
                .open(RegionLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
