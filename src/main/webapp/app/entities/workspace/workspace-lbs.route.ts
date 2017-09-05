import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WorkspaceLbsComponent } from './workspace-lbs.component';
import { WorkspaceLbsDetailComponent } from './workspace-lbs-detail.component';
import { WorkspaceLbsPopupComponent } from './workspace-lbs-dialog.component';
import { WorkspaceLbsDeletePopupComponent } from './workspace-lbs-delete-dialog.component';

@Injectable()
export class WorkspaceLbsResolvePagingParams implements Resolve<any> {

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

export const workspaceRoute: Routes = [
    {
        path: 'workspace-lbs',
        component: WorkspaceLbsComponent,
        resolve: {
            'pagingParams': WorkspaceLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspace.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'workspace-lbs/:id',
        component: WorkspaceLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspace.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const workspacePopupRoute: Routes = [
    {
        path: 'workspace-lbs-new',
        component: WorkspaceLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspace.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'workspace-lbs/:id/edit',
        component: WorkspaceLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspace.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'workspace-lbs/:id/delete',
        component: WorkspaceLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.workspace.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
