import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    BindingPOILbsService,
    BindingPOILbsPopupService,
    BindingPOILbsComponent,
    BindingPOILbsDetailComponent,
    BindingPOILbsDialogComponent,
    BindingPOILbsPopupComponent,
    BindingPOILbsDeletePopupComponent,
    BindingPOILbsDeleteDialogComponent,
    bindingPOIRoute,
    bindingPOIPopupRoute,
    BindingPOILbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...bindingPOIRoute,
    ...bindingPOIPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BindingPOILbsComponent,
        BindingPOILbsDetailComponent,
        BindingPOILbsDialogComponent,
        BindingPOILbsDeleteDialogComponent,
        BindingPOILbsPopupComponent,
        BindingPOILbsDeletePopupComponent,
    ],
    entryComponents: [
        BindingPOILbsComponent,
        BindingPOILbsDialogComponent,
        BindingPOILbsPopupComponent,
        BindingPOILbsDeleteDialogComponent,
        BindingPOILbsDeletePopupComponent,
    ],
    providers: [
        BindingPOILbsService,
        BindingPOILbsPopupService,
        BindingPOILbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestBindingPOILbsModule {}
