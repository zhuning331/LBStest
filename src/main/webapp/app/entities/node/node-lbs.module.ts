import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    NodeLbsService,
    NodeLbsPopupService,
    NodeLbsComponent,
    NodeLbsDetailComponent,
    NodeLbsDialogComponent,
    NodeLbsPopupComponent,
    NodeLbsDeletePopupComponent,
    NodeLbsDeleteDialogComponent,
    nodeRoute,
    nodePopupRoute,
    NodeLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...nodeRoute,
    ...nodePopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        NodeLbsComponent,
        NodeLbsDetailComponent,
        NodeLbsDialogComponent,
        NodeLbsDeleteDialogComponent,
        NodeLbsPopupComponent,
        NodeLbsDeletePopupComponent,
    ],
    entryComponents: [
        NodeLbsComponent,
        NodeLbsDialogComponent,
        NodeLbsPopupComponent,
        NodeLbsDeleteDialogComponent,
        NodeLbsDeletePopupComponent,
    ],
    providers: [
        NodeLbsService,
        NodeLbsPopupService,
        NodeLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestNodeLbsModule {}
