import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    PolygonRegionLbsService,
    PolygonRegionLbsPopupService,
    PolygonRegionLbsComponent,
    PolygonRegionLbsDetailComponent,
    PolygonRegionLbsDialogComponent,
    PolygonRegionLbsPopupComponent,
    PolygonRegionLbsDeletePopupComponent,
    PolygonRegionLbsDeleteDialogComponent,
    polygonRegionRoute,
    polygonRegionPopupRoute,
    PolygonRegionLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...polygonRegionRoute,
    ...polygonRegionPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PolygonRegionLbsComponent,
        PolygonRegionLbsDetailComponent,
        PolygonRegionLbsDialogComponent,
        PolygonRegionLbsDeleteDialogComponent,
        PolygonRegionLbsPopupComponent,
        PolygonRegionLbsDeletePopupComponent,
    ],
    entryComponents: [
        PolygonRegionLbsComponent,
        PolygonRegionLbsDialogComponent,
        PolygonRegionLbsPopupComponent,
        PolygonRegionLbsDeleteDialogComponent,
        PolygonRegionLbsDeletePopupComponent,
    ],
    providers: [
        PolygonRegionLbsService,
        PolygonRegionLbsPopupService,
        PolygonRegionLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestPolygonRegionLbsModule {}
