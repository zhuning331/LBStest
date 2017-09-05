import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    MapLbsService,
    MapLbsPopupService,
    MapLbsComponent,
    MapLbsDetailComponent,
    MapLbsDialogComponent,
    MapLbsPopupComponent,
    MapLbsDeletePopupComponent,
    MapLbsDeleteDialogComponent,
    mapRoute,
    mapPopupRoute,
    MapLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...mapRoute,
    ...mapPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MapLbsComponent,
        MapLbsDetailComponent,
        MapLbsDialogComponent,
        MapLbsDeleteDialogComponent,
        MapLbsPopupComponent,
        MapLbsDeletePopupComponent,
    ],
    entryComponents: [
        MapLbsComponent,
        MapLbsDialogComponent,
        MapLbsPopupComponent,
        MapLbsDeleteDialogComponent,
        MapLbsDeletePopupComponent,
    ],
    providers: [
        MapLbsService,
        MapLbsPopupService,
        MapLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestMapLbsModule {}
