import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RegionLbsComponent } from './region-lbs.component';
import { RegionLbsDetailComponent } from './region-lbs-detail.component';
import { RegionLbsPopupComponent } from './region-lbs-dialog.component';
import { RegionLbsDeletePopupComponent } from './region-lbs-delete-dialog.component';

@Injectable()
export class RegionLbsResolvePagingParams implements Resolve<any> {

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

export const regionRoute: Routes = [
    {
        path: 'region-lbs',
        component: RegionLbsComponent,
        resolve: {
            'pagingParams': RegionLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.region.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'region-lbs/:id',
        component: RegionLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.region.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regionPopupRoute: Routes = [
    {
        path: 'region-lbs-new',
        component: RegionLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.region.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'region-lbs/:id/edit',
        component: RegionLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.region.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'region-lbs/:id/delete',
        component: RegionLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.region.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
