import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WorkspaceLbs } from './workspace-lbs.model';
import { WorkspaceLbsPopupService } from './workspace-lbs-popup.service';
import { WorkspaceLbsService } from './workspace-lbs.service';

@Component({
    selector: 'jhi-workspace-lbs-delete-dialog',
    templateUrl: './workspace-lbs-delete-dialog.component.html'
})
export class WorkspaceLbsDeleteDialogComponent {

    workspace: WorkspaceLbs;

    constructor(
        private workspaceService: WorkspaceLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.workspaceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'workspaceListModification',
                content: 'Deleted an workspace'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-workspace-lbs-delete-popup',
    template: ''
})
export class WorkspaceLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workspacePopupService: WorkspaceLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.workspacePopupService
                .open(WorkspaceLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
