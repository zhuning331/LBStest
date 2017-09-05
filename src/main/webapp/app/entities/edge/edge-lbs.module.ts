import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    EdgeLbsService,
    EdgeLbsPopupService,
    EdgeLbsComponent,
    EdgeLbsDetailComponent,
    EdgeLbsDialogComponent,
    EdgeLbsPopupComponent,
    EdgeLbsDeletePopupComponent,
    EdgeLbsDeleteDialogComponent,
    edgeRoute,
    edgePopupRoute,
    EdgeLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...edgeRoute,
    ...edgePopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EdgeLbsComponent,
        EdgeLbsDetailComponent,
        EdgeLbsDialogComponent,
        EdgeLbsDeleteDialogComponent,
        EdgeLbsPopupComponent,
        EdgeLbsDeletePopupComponent,
    ],
    entryComponents: [
        EdgeLbsComponent,
        EdgeLbsDialogComponent,
        EdgeLbsPopupComponent,
        EdgeLbsDeleteDialogComponent,
        EdgeLbsDeletePopupComponent,
    ],
    providers: [
        EdgeLbsService,
        EdgeLbsPopupService,
        EdgeLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestEdgeLbsModule {}
