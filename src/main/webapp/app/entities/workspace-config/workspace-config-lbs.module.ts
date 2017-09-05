import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import {
    WorkspaceConfigLbsService,
    WorkspaceConfigLbsPopupService,
    WorkspaceConfigLbsComponent,
    WorkspaceConfigLbsDetailComponent,
    WorkspaceConfigLbsDialogComponent,
    WorkspaceConfigLbsPopupComponent,
    WorkspaceConfigLbsDeletePopupComponent,
    WorkspaceConfigLbsDeleteDialogComponent,
    workspaceConfigRoute,
    workspaceConfigPopupRoute,
    WorkspaceConfigLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...workspaceConfigRoute,
    ...workspaceConfigPopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WorkspaceConfigLbsComponent,
        WorkspaceConfigLbsDetailComponent,
        WorkspaceConfigLbsDialogComponent,
        WorkspaceConfigLbsDeleteDialogComponent,
        WorkspaceConfigLbsPopupComponent,
        WorkspaceConfigLbsDeletePopupComponent,
    ],
    entryComponents: [
        WorkspaceConfigLbsComponent,
        WorkspaceConfigLbsDialogComponent,
        WorkspaceConfigLbsPopupComponent,
        WorkspaceConfigLbsDeleteDialogComponent,
        WorkspaceConfigLbsDeletePopupComponent,
    ],
    providers: [
        WorkspaceConfigLbsService,
        WorkspaceConfigLbsPopupService,
        WorkspaceConfigLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestWorkspaceConfigLbsModule {}
