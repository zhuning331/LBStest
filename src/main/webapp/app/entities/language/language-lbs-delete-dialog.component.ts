import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LanguageLbs } from './language-lbs.model';
import { LanguageLbsPopupService } from './language-lbs-popup.service';
import { LanguageLbsService } from './language-lbs.service';

@Component({
    selector: 'jhi-language-lbs-delete-dialog',
    templateUrl: './language-lbs-delete-dialog.component.html'
})
export class LanguageLbsDeleteDialogComponent {

    language: LanguageLbs;

    constructor(
        private languageService: LanguageLbsService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.languageService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'languageListModification',
                content: 'Deleted an language'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-language-lbs-delete-popup',
    template: ''
})
export class LanguageLbsDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private languagePopupService: LanguageLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.languagePopupService
                .open(LanguageLbsDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
