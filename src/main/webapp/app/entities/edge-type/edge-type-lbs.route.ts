import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EdgeTypeLbsComponent } from './edge-type-lbs.component';
import { EdgeTypeLbsDetailComponent } from './edge-type-lbs-detail.component';
import { EdgeTypeLbsPopupComponent } from './edge-type-lbs-dialog.component';
import { EdgeTypeLbsDeletePopupComponent } from './edge-type-lbs-delete-dialog.component';

@Injectable()
export class EdgeTypeLbsResolvePagingParams implements Resolve<any> {

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

export const edgeTypeRoute: Routes = [
    {
        path: 'edge-type-lbs',
        component: EdgeTypeLbsComponent,
        resolve: {
            'pagingParams': EdgeTypeLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edgeType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'edge-type-lbs/:id',
        component: EdgeTypeLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edgeType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const edgeTypePopupRoute: Routes = [
    {
        path: 'edge-type-lbs-new',
        component: EdgeTypeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edgeType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'edge-type-lbs/:id/edit',
        component: EdgeTypeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edgeType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'edge-type-lbs/:id/delete',
        component: EdgeTypeLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edgeType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
