import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { POITypeLbsComponent } from './poi-type-lbs.component';
import { POITypeLbsDetailComponent } from './poi-type-lbs-detail.component';
import { POITypeLbsPopupComponent } from './poi-type-lbs-dialog.component';
import { POITypeLbsDeletePopupComponent } from './poi-type-lbs-delete-dialog.component';

@Injectable()
export class POITypeLbsResolvePagingParams implements Resolve<any> {

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

export const pOITypeRoute: Routes = [
    {
        path: 'poi-type-lbs',
        component: POITypeLbsComponent,
        resolve: {
            'pagingParams': POITypeLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'poi-type-lbs/:id',
        component: POITypeLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pOITypePopupRoute: Routes = [
    {
        path: 'poi-type-lbs-new',
        component: POITypeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poi-type-lbs/:id/edit',
        component: POITypeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poi-type-lbs/:id/delete',
        component: POITypeLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
