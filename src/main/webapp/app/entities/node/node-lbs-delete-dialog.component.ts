import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { NodeLbs } from './node-lbs.model';
import { NodeLbsPopupService } from './node-lbs-popup.service';
import { NodeLbsService } from './node-lbs.service';

@Component({
    selector: 'jhi-node-lbs-delete-dialog',
    templateUrl: './node-lbs-delete-dialog.component.html'
})
export class NodeLbsDeleteDialogComponent {

    node: NodeLbs;

    constructor(
        private nodeService: NodeLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.nodeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'nodeListModification',
                content: 'Deleted an node'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-node-lbs-delete-popup',
    template: ''
})
export class NodeLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private nodePopupService: NodeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.nodePopupService
                .open(NodeLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
