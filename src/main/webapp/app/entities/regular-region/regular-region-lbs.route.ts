import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { RegularRegionLbsComponent } from './regular-region-lbs.component';
import { RegularRegionLbsDetailComponent } from './regular-region-lbs-detail.component';
import { RegularRegionLbsPopupComponent } from './regular-region-lbs-dialog.component';
import { RegularRegionLbsDeletePopupComponent } from './regular-region-lbs-delete-dialog.component';

@Injectable()
export class RegularRegionLbsResolvePagingParams implements Resolve<any> {

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

export const regularRegionRoute: Routes = [
    {
        path: 'regular-region-lbs',
        component: RegularRegionLbsComponent,
        resolve: {
            'pagingParams': RegularRegionLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regularRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'regular-region-lbs/:id',
        component: RegularRegionLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regularRegion.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const regularRegionPopupRoute: Routes = [
    {
        path: 'regular-region-lbs-new',
        component: RegularRegionLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regularRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'regular-region-lbs/:id/edit',
        component: RegularRegionLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regularRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'regular-region-lbs/:id/delete',
        component: RegularRegionLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.regularRegion.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
