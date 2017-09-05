import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PolygonRegionLbsComponent } from './polygon-region-lbs.component';
import { PolygonRegionLbsDetailComponent } from './polygon-region-lbs-detail.component';
import { PolygonRegionLbsPopupComponent } from './polygon-region-lbs-dialog.component';
import { PolygonRegionLbsDeletePopupComponent } from './polygon-region-lbs-delete-dialog.component';

@Injectable()
export class PolygonRegionLbsResolvePagingParams implements Resolve<any> {

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

export const polygonRegionRoute: Routes = [
    {
        path: 'polygon-region-lbs',
        component: PolygonRegionLbsComponent,
        resolve: {
            'pagingParams': PolygonRegionLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.polygonRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'polygon-region-lbs/:id',
        component: PolygonRegionLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.polygonRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const polygonRegionPopupRoute: Routes = [
    {
        path: 'polygon-region-lbs-new',
        component: PolygonRegionLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.polygonRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'polygon-region-lbs/:id/edit',
        component: PolygonRegionLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.polygonRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'polygon-region-lbs/:id/delete',
        component: PolygonRegionLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.polygonRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
