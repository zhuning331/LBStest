import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WorkspaceConfigLbsComponent } from './workspace-config-lbs.component';
import { WorkspaceConfigLbsDetailComponent } from './workspace-config-lbs-detail.component';
import { WorkspaceConfigLbsPopupComponent } from './workspace-config-lbs-dialog.component';
import { WorkspaceConfigLbsDeletePopupComponent } from './workspace-config-lbs-delete-dialog.component';

@Injectable()
export class WorkspaceConfigLbsResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const workspaceConfigRoute: Routes = [
    {
        path: 'workspace-config-lbs',
        component: WorkspaceConfigLbsComponent,
        resolve: {
            'pagingParams': WorkspaceConfigLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspaceConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'workspace-config-lbs/:id',
        component: WorkspaceConfigLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspaceConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const workspaceConfigPopupRoute: Routes = [
    {
        path: 'workspace-config-lbs-new',
        component: WorkspaceConfigLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspaceConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'workspace-config-lbs/:id/edit',
        component: WorkspaceConfigLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspaceConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'workspace-config-lbs/:id/delete',
        component: WorkspaceConfigLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspaceConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
