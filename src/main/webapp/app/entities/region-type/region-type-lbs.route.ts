import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RegionTypeLbsComponent } from './region-type-lbs.component';
import { RegionTypeLbsDetailComponent } from './region-type-lbs-detail.component';
import { RegionTypeLbsPopupComponent } from './region-type-lbs-dialog.component';
import { RegionTypeLbsDeletePopupComponent } from './region-type-lbs-delete-dialog.component';

@Injectable()
export class RegionTypeLbsResolvePagingParams implements Resolve<any> {

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

export const regionTypeRoute: Routes = [
    {
        path: 'region-type-lbs',
        component: RegionTypeLbsComponent,
        resolve: {
            'pagingParams': RegionTypeLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'region-type-lbs/:id',
        component: RegionTypeLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regionType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regionTypePopupRoute: Routes = [
    {
        path: 'region-type-lbs-new',
        component: RegionTypeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regionType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'region-type-lbs/:id/edit',
        component: RegionTypeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regionType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'region-type-lbs/:id/delete',
        component: RegionTypeLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regionType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
