import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    EdgeTypeLbsService,
    EdgeTypeLbsPopupService,
    EdgeTypeLbsComponent,
    EdgeTypeLbsDetailComponent,
    EdgeTypeLbsDialogComponent,
    EdgeTypeLbsPopupComponent,
    EdgeTypeLbsDeletePopupComponent,
    EdgeTypeLbsDeleteDialogComponent,
    edgeTypeRoute,
    edgeTypePopupRoute,
    EdgeTypeLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...edgeTypeRoute,
    ...edgeTypePopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EdgeTypeLbsComponent,
        EdgeTypeLbsDetailComponent,
        EdgeTypeLbsDialogComponent,
        EdgeTypeLbsDeleteDialogComponent,
        EdgeTypeLbsPopupComponent,
        EdgeTypeLbsDeletePopupComponent,
    ],
    entryComponents: [
        EdgeTypeLbsComponent,
        EdgeTypeLbsDialogComponent,
        EdgeTypeLbsPopupComponent,
        EdgeTypeLbsDeleteDialogComponent,
        EdgeTypeLbsDeletePopupComponent,
    ],
    providers: [
        EdgeTypeLbsService,
        EdgeTypeLbsPopupService,
        EdgeTypeLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestEdgeTypeLbsModule {}
