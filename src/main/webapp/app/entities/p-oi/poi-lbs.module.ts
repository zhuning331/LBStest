import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    POILbsService,
    POILbsPopupService,
    POILbsComponent,
    POILbsDetailComponent,
    POILbsDialogComponent,
    POILbsPopupComponent,
    POILbsDeletePopupComponent,
    POILbsDeleteDialogComponent,
    pOIRoute,
    pOIPopupRoute,
    POILbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...pOIRoute,
    ...pOIPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        POILbsComponent,
        POILbsDetailComponent,
        POILbsDialogComponent,
        POILbsDeleteDialogComponent,
        POILbsPopupComponent,
        POILbsDeletePopupComponent,
    ],
    entryComponents: [
        POILbsComponent,
        POILbsDialogComponent,
        POILbsPopupComponent,
        POILbsDeleteDialogComponent,
        POILbsDeletePopupComponent,
    ],
    providers: [
        POILbsService,
        POILbsPopupService,
        POILbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestPOILbsModule {}
