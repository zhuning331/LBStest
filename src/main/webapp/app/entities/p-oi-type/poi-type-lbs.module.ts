import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    POITypeLbsService,
    POITypeLbsPopupService,
    POITypeLbsComponent,
    POITypeLbsDetailComponent,
    POITypeLbsDialogComponent,
    POITypeLbsPopupComponent,
    POITypeLbsDeletePopupComponent,
    POITypeLbsDeleteDialogComponent,
    pOITypeRoute,
    pOITypePopupRoute,
    POITypeLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pOITypeRoute,
    ...pOITypePopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        POITypeLbsComponent,
        POITypeLbsDetailComponent,
        POITypeLbsDialogComponent,
        POITypeLbsDeleteDialogComponent,
        POITypeLbsPopupComponent,
        POITypeLbsDeletePopupComponent,
    ],
    entryComponents: [
        POITypeLbsComponent,
        POITypeLbsDialogComponent,
        POITypeLbsPopupComponent,
        POITypeLbsDeleteDialogComponent,
        POITypeLbsDeletePopupComponent,
    ],
    providers: [
        POITypeLbsService,
        POITypeLbsPopupService,
        POITypeLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestPOITypeLbsModule {}
