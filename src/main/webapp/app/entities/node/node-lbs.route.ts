import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { NodeLbsComponent } from './node-lbs.component';
import { NodeLbsDetailComponent } from './node-lbs-detail.component';
import { NodeLbsPopupComponent } from './node-lbs-dialog.component';
import { NodeLbsDeletePopupComponent } from './node-lbs-delete-dialog.component';

@Injectable()
export class NodeLbsResolvePagingParams implements Resolve<any> {

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

export const nodeRoute: Routes = [
    {
        path: 'node-lbs',
        component: NodeLbsComponent,
        resolve: {
            'pagingParams': NodeLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.node.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'node-lbs/:id',
        component: NodeLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.node.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const nodePopupRoute: Routes = [
    {
        path: 'node-lbs-new',
        component: NodeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.node.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'node-lbs/:id/edit',
        component: NodeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.node.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'node-lbs/:id/delete',
        component: NodeLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.node.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
