import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LayerLbs } from './layer-lbs.model';
import { LayerLbsPopupService } from './layer-lbs-popup.service';
import { LayerLbsService } from './layer-lbs.service';

@Component({
    selector: 'jhi-layer-lbs-delete-dialog',
    templateUrl: './layer-lbs-delete-dialog.component.html'
})
export class LayerLbsDeleteDialogComponent {

    layer: LayerLbs;

    constructor(
        private layerService: LayerLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.layerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'layerListModification',
                content: 'Deleted an layer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-layer-lbs-delete-popup',
    template: ''
})
export class LayerLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private layerPopupService: LayerLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.layerPopupService
                .open(LayerLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
