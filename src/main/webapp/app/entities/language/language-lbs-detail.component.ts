import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { LanguageLbs } from './language-lbs.model';
import { LanguageLbsService } from './language-lbs.service';

@Component({
    selector: 'jhi-language-lbs-detail',
    templateUrl: './language-lbs-detail.component.html'
})
export class LanguageLbsDetailComponent implements OnInit, OnDestroy {

    language: LanguageLbs;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private languageService: LanguageLbsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLanguages();
    }

    load(id) {
        this.languageService.find(id).subscribe((language) => {
            this.language = language;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLanguages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'languageListModification',
            (response) => this.load(this.language.id)
        );
    }
}
