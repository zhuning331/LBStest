import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EdgeTypeLbs } from './edge-type-lbs.model';
import { EdgeTypeLbsPopupService } from './edge-type-lbs-popup.service';
import { EdgeTypeLbsService } from './edge-type-lbs.service';

@Component({
    selector: 'jhi-edge-type-lbs-delete-dialog',
    templateUrl: './edge-type-lbs-delete-dialog.component.html'
})
export class EdgeTypeLbsDeleteDialogComponent {

    edgeType: EdgeTypeLbs;

    constructor(
        private edgeTypeService: EdgeTypeLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.edgeTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'edgeTypeListModification',
                content: 'Deleted an edgeType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-edge-type-lbs-delete-popup',
    template: ''
})
export class EdgeTypeLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private edgeTypePopupService: EdgeTypeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.edgeTypePopupService
                .open(EdgeTypeLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
