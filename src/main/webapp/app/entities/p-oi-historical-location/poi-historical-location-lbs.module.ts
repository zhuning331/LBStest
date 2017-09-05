import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    POIHistoricalLocationLbsService,
    POIHistoricalLocationLbsPopupService,
    POIHistoricalLocationLbsComponent,
    POIHistoricalLocationLbsDetailComponent,
    POIHistoricalLocationLbsDialogComponent,
    POIHistoricalLocationLbsPopupComponent,
    POIHistoricalLocationLbsDeletePopupComponent,
    POIHistoricalLocationLbsDeleteDialogComponent,
    pOIHistoricalLocationRoute,
    pOIHistoricalLocationPopupRoute,
    POIHistoricalLocationLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pOIHistoricalLocationRoute,
    ...pOIHistoricalLocationPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        POIHistoricalLocationLbsComponent,
        POIHistoricalLocationLbsDetailComponent,
        POIHistoricalLocationLbsDialogComponent,
        POIHistoricalLocationLbsDeleteDialogComponent,
        POIHistoricalLocationLbsPopupComponent,
        POIHistoricalLocationLbsDeletePopupComponent,
    ],
    entryComponents: [
        POIHistoricalLocationLbsComponent,
        POIHistoricalLocationLbsDialogComponent,
        POIHistoricalLocationLbsPopupComponent,
        POIHistoricalLocationLbsDeleteDialogComponent,
        POIHistoricalLocationLbsDeletePopupComponent,
    ],
    providers: [
        POIHistoricalLocationLbsService,
        POIHistoricalLocationLbsPopupService,
        POIHistoricalLocationLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestPOIHistoricalLocationLbsModule {}
