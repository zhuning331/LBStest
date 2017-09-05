import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MapLbs } from './map-lbs.model';
import { MapLbsPopupService } from './map-lbs-popup.service';
import { MapLbsService } from './map-lbs.service';

@Component({
    selector: 'jhi-map-lbs-delete-dialog',
    templateUrl: './map-lbs-delete-dialog.component.html'
})
export class MapLbsDeleteDialogComponent {

    map: MapLbs;

    constructor(
        private mapService: MapLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mapService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mapListModification',
                content: 'Deleted an map'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-map-lbs-delete-popup',
    template: ''
})
export class MapLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mapPopupService: MapLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mapPopupService
                .open(MapLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
