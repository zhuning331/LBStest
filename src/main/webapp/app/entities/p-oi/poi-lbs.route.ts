import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { POILbsComponent } from './poi-lbs.component';
import { POILbsDetailComponent } from './poi-lbs-detail.component';
import { POILbsPopupComponent } from './poi-lbs-dialog.component';
import { POILbsDeletePopupComponent } from './poi-lbs-delete-dialog.component';

@Injectable()
export class POILbsResolvePagingParams implements Resolve<any> {

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

export const pOIRoute: Routes = [
    {
        path: 'poi-lbs',
        component: POILbsComponent,
        resolve: {
            'pagingParams': POILbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOI.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'poi-lbs/:id',
        component: POILbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOI.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pOIPopupRoute: Routes = [
    {
        path: 'poi-lbs-new',
        component: POILbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOI.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poi-lbs/:id/edit',
        component: POILbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOI.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poi-lbs/:id/delete',
        component: POILbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOI.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
