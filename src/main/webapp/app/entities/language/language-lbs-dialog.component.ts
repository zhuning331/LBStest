import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { LanguageLbs } from './language-lbs.model';
import { LanguageLbsPopupService } from './language-lbs-popup.service';
import { LanguageLbsService } from './language-lbs.service';

@Component({
    selector: 'jhi-language-lbs-dialog',
    templateUrl: './language-lbs-dialog.component.html'
})
export class LanguageLbsDialogComponent implements OnInit {

    language: LanguageLbs;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private languageService: LanguageLbsService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.language.id !== undefined) {
            this.subscribeToSaveResponse(
                this.languageService.update(this.language));
        } else {
            this.subscribeToSaveResponse(
                this.languageService.create(this.language));
        }
    }

    private subscribeToSaveResponse(result: Observable<LanguageLbs>) {
        result.subscribe((res: LanguageLbs) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: LanguageLbs) {
        this.eventManager.broadcast({ name: 'languageListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-language-lbs-popup',
    template: ''
})
export class LanguageLbsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private languagePopupService: LanguageLbsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.languagePopupService
                    .open(LanguageLbsDialogComponent as Component, params['id']);
            } else {
                this.languagePopupService
                    .open(LanguageLbsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
