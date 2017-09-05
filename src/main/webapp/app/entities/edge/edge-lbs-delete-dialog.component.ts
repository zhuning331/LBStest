import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EdgeLbs } from './edge-lbs.model';
import { EdgeLbsPopupService } from './edge-lbs-popup.service';
import { EdgeLbsService } from './edge-lbs.service';

@Component({
    selector: 'jhi-edge-lbs-delete-dialog',
    templateUrl: './edge-lbs-delete-dialog.component.html'
})
export class EdgeLbsDeleteDialogComponent {

    edge: EdgeLbs;

    constructor(
        private edgeService: EdgeLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.edgeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'edgeListModification',
                content: 'Deleted an edge'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-edge-lbs-delete-popup',
    template: ''
})
export class EdgeLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private edgePopupService: EdgeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.edgePopupService
                .open(EdgeLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
