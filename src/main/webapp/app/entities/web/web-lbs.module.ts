import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    WebLbsService,
    WebLbsPopupService,
    WebLbsComponent,
    WebLbsDetailComponent,
    WebLbsDialogComponent,
    WebLbsPopupComponent,
    WebLbsDeletePopupComponent,
    WebLbsDeleteDialogComponent,
    webRoute,
    webPopupRoute,
    WebLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...webRoute,
    ...webPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WebLbsComponent,
        WebLbsDetailComponent,
        WebLbsDialogComponent,
        WebLbsDeleteDialogComponent,
        WebLbsPopupComponent,
        WebLbsDeletePopupComponent,
    ],
    entryComponents: [
        WebLbsComponent,
        WebLbsDialogComponent,
        WebLbsPopupComponent,
        WebLbsDeleteDialogComponent,
        WebLbsDeletePopupComponent,
    ],
    providers: [
        WebLbsService,
        WebLbsPopupService,
        WebLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestWebLbsModule {}
