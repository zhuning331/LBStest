import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { RegionTypeLbs } from './region-type-lbs.model';
import { RegionTypeLbsPopupService } from './region-type-lbs-popup.service';
import { RegionTypeLbsService } from './region-type-lbs.service';

@Component({
    selector: 'jhi-region-type-lbs-delete-dialog',
    templateUrl: './region-type-lbs-delete-dialog.component.html'
})
export class RegionTypeLbsDeleteDialogComponent {

    regionType: RegionTypeLbs;

    constructor(
        private regionTypeService: RegionTypeLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.regionTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'regionTypeListModification',
                content: 'Deleted an regionType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-region-type-lbs-delete-popup',
    template: ''
})
export class RegionTypeLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private regionTypePopupService: RegionTypeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.regionTypePopupService
                .open(RegionTypeLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
