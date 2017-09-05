import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { WebLbs } from './web-lbs.model';
import { WebLbsPopupService } from './web-lbs-popup.service';
import { WebLbsService } from './web-lbs.service';

@Component({
    selector: 'jhi-web-lbs-delete-dialog',
    templateUrl: './web-lbs-delete-dialog.component.html'
})
export class WebLbsDeleteDialogComponent {

    web: WebLbs;

    constructor(
        private webService: WebLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.webService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'webListModification',
                content: 'Deleted an web'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-web-lbs-delete-popup',
    template: ''
})
export class WebLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private webPopupService: WebLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.webPopupService
                .open(WebLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
