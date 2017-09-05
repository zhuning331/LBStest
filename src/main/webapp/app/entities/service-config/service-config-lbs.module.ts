import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    ServiceConfigLbsService,
    ServiceConfigLbsPopupService,
    ServiceConfigLbsComponent,
    ServiceConfigLbsDetailComponent,
    ServiceConfigLbsDialogComponent,
    ServiceConfigLbsPopupComponent,
    ServiceConfigLbsDeletePopupComponent,
    ServiceConfigLbsDeleteDialogComponent,
    serviceConfigRoute,
    serviceConfigPopupRoute,
    ServiceConfigLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...serviceConfigRoute,
    ...serviceConfigPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ServiceConfigLbsComponent,
        ServiceConfigLbsDetailComponent,
        ServiceConfigLbsDialogComponent,
        ServiceConfigLbsDeleteDialogComponent,
        ServiceConfigLbsPopupComponent,
        ServiceConfigLbsDeletePopupComponent,
    ],
    entryComponents: [
        ServiceConfigLbsComponent,
        ServiceConfigLbsDialogComponent,
        ServiceConfigLbsPopupComponent,
        ServiceConfigLbsDeleteDialogComponent,
        ServiceConfigLbsDeletePopupComponent,
    ],
    providers: [
        ServiceConfigLbsService,
        ServiceConfigLbsPopupService,
        ServiceConfigLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestServiceConfigLbsModule {}
