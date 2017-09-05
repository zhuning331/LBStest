import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    LayerLbsService,
    LayerLbsPopupService,
    LayerLbsComponent,
    LayerLbsDetailComponent,
    LayerLbsDialogComponent,
    LayerLbsPopupComponent,
    LayerLbsDeletePopupComponent,
    LayerLbsDeleteDialogComponent,
    layerRoute,
    layerPopupRoute,
    LayerLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...layerRoute,
    ...layerPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LayerLbsComponent,
        LayerLbsDetailComponent,
        LayerLbsDialogComponent,
        LayerLbsDeleteDialogComponent,
        LayerLbsPopupComponent,
        LayerLbsDeletePopupComponent,
    ],
    entryComponents: [
        LayerLbsComponent,
        LayerLbsDialogComponent,
        LayerLbsPopupComponent,
        LayerLbsDeleteDialogComponent,
        LayerLbsDeletePopupComponent,
    ],
    providers: [
        LayerLbsService,
        LayerLbsPopupService,
        LayerLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestLayerLbsModule {}
