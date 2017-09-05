import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    LanguageLbsService,
    LanguageLbsPopupService,
    LanguageLbsComponent,
    LanguageLbsDetailComponent,
    LanguageLbsDialogComponent,
    LanguageLbsPopupComponent,
    LanguageLbsDeletePopupComponent,
    LanguageLbsDeleteDialogComponent,
    languageRoute,
    languagePopupRoute,
    LanguageLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...languageRoute,
    ...languagePopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LanguageLbsComponent,
        LanguageLbsDetailComponent,
        LanguageLbsDialogComponent,
        LanguageLbsDeleteDialogComponent,
        LanguageLbsPopupComponent,
        LanguageLbsDeletePopupComponent,
    ],
    entryComponents: [
        LanguageLbsComponent,
        LanguageLbsDialogComponent,
        LanguageLbsPopupComponent,
        LanguageLbsDeleteDialogComponent,
        LanguageLbsDeletePopupComponent,
    ],
    providers: [
        LanguageLbsService,
        LanguageLbsPopupService,
        LanguageLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestLanguageLbsModule {}
