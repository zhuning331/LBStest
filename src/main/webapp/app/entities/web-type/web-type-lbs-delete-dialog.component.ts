import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WebTypeLbs } from './web-type-lbs.model';
import { WebTypeLbsPopupService } from './web-type-lbs-popup.service';
import { WebTypeLbsService } from './web-type-lbs.service';

@Component({
    selector: 'jhi-web-type-lbs-delete-dialog',
    templateUrl: './web-type-lbs-delete-dialog.component.html'
})
export class WebTypeLbsDeleteDialogComponent {

    webType: WebTypeLbs;

    constructor(
        private webTypeService: WebTypeLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.webTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'webTypeListModification',
                content: 'Deleted an webType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-web-type-lbs-delete-popup',
    template: ''
})
export class WebTypeLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private webTypePopupService: WebTypeLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.webTypePopupService
                .open(WebTypeLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
