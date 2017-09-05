import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { POIHistoricalLocationLbsComponent } from './poi-historical-location-lbs.component';
import { POIHistoricalLocationLbsDetailComponent } from './poi-historical-location-lbs-detail.component';
import { POIHistoricalLocationLbsPopupComponent } from './poi-historical-location-lbs-dialog.component';
import { POIHistoricalLocationLbsDeletePopupComponent } from './poi-historical-location-lbs-delete-dialog.component';

@Injectable()
export class POIHistoricalLocationLbsResolvePagingParams implements Resolve<any> {

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

export const pOIHistoricalLocationRoute: Routes = [
    {
        path: 'poi-historical-location-lbs',
        component: POIHistoricalLocationLbsComponent,
        resolve: {
            'pagingParams': POIHistoricalLocationLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIHistoricalLocation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'poi-historical-location-lbs/:id',
        component: POIHistoricalLocationLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIHistoricalLocation.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pOIHistoricalLocationPopupRoute: Routes = [
    {
        path: 'poi-historical-location-lbs-new',
        component: POIHistoricalLocationLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIHistoricalLocation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poi-historical-location-lbs/:id/edit',
        component: POIHistoricalLocationLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIHistoricalLocation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'poi-historical-location-lbs/:id/delete',
        component: POIHistoricalLocationLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.pOIHistoricalLocation.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
