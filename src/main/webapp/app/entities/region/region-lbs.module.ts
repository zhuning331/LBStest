import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    RegionLbsService,
    RegionLbsPopupService,
    RegionLbsComponent,
    RegionLbsDetailComponent,
    RegionLbsDialogComponent,
    RegionLbsPopupComponent,
    RegionLbsDeletePopupComponent,
    RegionLbsDeleteDialogComponent,
    regionRoute,
    regionPopupRoute,
    RegionLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...regionRoute,
    ...regionPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RegionLbsComponent,
        RegionLbsDetailComponent,
        RegionLbsDialogComponent,
        RegionLbsDeleteDialogComponent,
        RegionLbsPopupComponent,
        RegionLbsDeletePopupComponent,
    ],
    entryComponents: [
        RegionLbsComponent,
        RegionLbsDialogComponent,
        RegionLbsPopupComponent,
        RegionLbsDeleteDialogComponent,
        RegionLbsDeletePopupComponent,
    ],
    providers: [
        RegionLbsService,
        RegionLbsPopupService,
        RegionLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestRegionLbsModule {}
