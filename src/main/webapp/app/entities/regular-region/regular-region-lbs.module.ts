import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    RegularRegionLbsService,
    RegularRegionLbsPopupService,
    RegularRegionLbsComponent,
    RegularRegionLbsDetailComponent,
    RegularRegionLbsDialogComponent,
    RegularRegionLbsPopupComponent,
    RegularRegionLbsDeletePopupComponent,
    RegularRegionLbsDeleteDialogComponent,
    regularRegionRoute,
    regularRegionPopupRoute,
    RegularRegionLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...regularRegionRoute,
    ...regularRegionPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        RegularRegionLbsComponent,
        RegularRegionLbsDetailComponent,
        RegularRegionLbsDialogComponent,
        RegularRegionLbsDeleteDialogComponent,
        RegularRegionLbsPopupComponent,
        RegularRegionLbsDeletePopupComponent,
    ],
    entryComponents: [
        RegularRegionLbsComponent,
        RegularRegionLbsDialogComponent,
        RegularRegionLbsPopupComponent,
        RegularRegionLbsDeleteDialogComponent,
        RegularRegionLbsDeletePopupComponent,
    ],
    providers: [
        RegularRegionLbsService,
        RegularRegionLbsPopupService,
        RegularRegionLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestRegularRegionLbsModule {}
