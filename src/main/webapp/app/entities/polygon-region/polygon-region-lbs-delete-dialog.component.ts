import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PolygonRegionLbs } from './polygon-region-lbs.model';
import { PolygonRegionLbsPopupService } from './polygon-region-lbs-popup.service';
import { PolygonRegionLbsService } from './polygon-region-lbs.service';

@Component({
    selector: 'jhi-polygon-region-lbs-delete-dialog',
    templateUrl: './polygon-region-lbs-delete-dialog.component.html'
})
export class PolygonRegionLbsDeleteDialogComponent {

    polygonRegion: PolygonRegionLbs;

    constructor(
        private polygonRegionService: PolygonRegionLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.polygonRegionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'polygonRegionListModification',
                content: 'Deleted an polygonRegion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-polygon-region-lbs-delete-popup',
    template: ''
})
export class PolygonRegionLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private polygonRegionPopupService: PolygonRegionLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.polygonRegionPopupService
                .open(PolygonRegionLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
