import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../../shared';
import { LbStestAdminModule } from '../../admin/admin.module';
import {
    WorkspaceLbsService,
    WorkspaceLbsPopupService,
    WorkspaceLbsComponent,
    WorkspaceLbsDetailComponent,
    WorkspaceLbsDialogComponent,
    WorkspaceLbsPopupComponent,
    WorkspaceLbsDeletePopupComponent,
    WorkspaceLbsDeleteDialogComponent,
    workspaceRoute,
    workspacePopupRoute,
    WorkspaceLbsResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...workspaceRoute,
    ...workspacePopupRoute,
];

@NgModule({
    imports: [
        LbStestSharedModule,
        LbStestAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        WorkspaceLbsComponent,
        WorkspaceLbsDetailComponent,
        WorkspaceLbsDialogComponent,
        WorkspaceLbsDeleteDialogComponent,
        WorkspaceLbsPopupComponent,
        WorkspaceLbsDeletePopupComponent,
    ],
    entryComponents: [
        WorkspaceLbsComponent,
        WorkspaceLbsDialogComponent,
        WorkspaceLbsPopupComponent,
        WorkspaceLbsDeleteDialogComponent,
        WorkspaceLbsDeletePopupComponent,
    ],
    providers: [
        WorkspaceLbsService,
        WorkspaceLbsPopupService,
        WorkspaceLbsResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestWorkspaceLbsModule {}
