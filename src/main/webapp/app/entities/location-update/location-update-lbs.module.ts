import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    LocationUpdateLbsService,
    LocationUpdateLbsPopupService,
    LocationUpdateLbsComponent,
    LocationUpdateLbsDetailComponent,
    LocationUpdateLbsDialogComponent,
    LocationUpdateLbsPopupComponent,
    LocationUpdateLbsDeletePopupComponent,
    LocationUpdateLbsDeleteDialogComponent,
    locationUpdateRoute,
    locationUpdatePopupRoute,
    LocationUpdateLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...locationUpdateRoute,
    ...locationUpdatePopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LocationUpdateLbsComponent,
        LocationUpdateLbsDetailComponent,
        LocationUpdateLbsDialogComponent,
        LocationUpdateLbsDeleteDialogComponent,
        LocationUpdateLbsPopupComponent,
        LocationUpdateLbsDeletePopupComponent,
    ],
    entryComponents: [
        LocationUpdateLbsComponent,
        LocationUpdateLbsDialogComponent,
        LocationUpdateLbsPopupComponent,
        LocationUpdateLbsDeleteDialogComponent,
        LocationUpdateLbsDeletePopupComponent,
    ],
    providers: [
        LocationUpdateLbsService,
        LocationUpdateLbsPopupService,
        LocationUpdateLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestLocationUpdateLbsModule {}
