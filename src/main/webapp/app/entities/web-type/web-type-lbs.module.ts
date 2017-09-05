import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    WebTypeLbsService,
    WebTypeLbsPopupService,
    WebTypeLbsComponent,
    WebTypeLbsDetailComponent,
    WebTypeLbsDialogComponent,
    WebTypeLbsPopupComponent,
    WebTypeLbsDeletePopupComponent,
    WebTypeLbsDeleteDialogComponent,
    webTypeRoute,
    webTypePopupRoute,
    WebTypeLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...webTypeRoute,
    ...webTypePopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WebTypeLbsComponent,
        WebTypeLbsDetailComponent,
        WebTypeLbsDialogComponent,
        WebTypeLbsDeleteDialogComponent,
        WebTypeLbsPopupComponent,
        WebTypeLbsDeletePopupComponent,
    ],
    entryComponents: [
        WebTypeLbsComponent,
        WebTypeLbsDialogComponent,
        WebTypeLbsPopupComponent,
        WebTypeLbsDeleteDialogComponent,
        WebTypeLbsDeletePopupComponent,
    ],
    providers: [
        WebTypeLbsService,
        WebTypeLbsPopupService,
        WebTypeLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestWebTypeLbsModule {}
