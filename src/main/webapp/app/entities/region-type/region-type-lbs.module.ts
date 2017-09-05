import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    RegionTypeLbsService,
    RegionTypeLbsPopupService,
    RegionTypeLbsComponent,
    RegionTypeLbsDetailComponent,
    RegionTypeLbsDialogComponent,
    RegionTypeLbsPopupComponent,
    RegionTypeLbsDeletePopupComponent,
    RegionTypeLbsDeleteDialogComponent,
    regionTypeRoute,
    regionTypePopupRoute,
    RegionTypeLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...regionTypeRoute,
    ...regionTypePopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RegionTypeLbsComponent,
        RegionTypeLbsDetailComponent,
        RegionTypeLbsDialogComponent,
        RegionTypeLbsDeleteDialogComponent,
        RegionTypeLbsPopupComponent,
        RegionTypeLbsDeletePopupComponent,
    ],
    entryComponents: [
        RegionTypeLbsComponent,
        RegionTypeLbsDialogComponent,
        RegionTypeLbsPopupComponent,
        RegionTypeLbsDeleteDialogComponent,
        RegionTypeLbsDeletePopupComponent,
    ],
    providers: [
        RegionTypeLbsService,
        RegionTypeLbsPopupService,
        RegionTypeLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestRegionTypeLbsModule {}
