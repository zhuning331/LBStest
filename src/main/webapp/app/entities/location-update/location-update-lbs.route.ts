import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LocationUpdateLbsComponent } from './location-update-lbs.component';
import { LocationUpdateLbsDetailComponent } from './location-update-lbs-detail.component';
import { LocationUpdateLbsPopupComponent } from './location-update-lbs-dialog.component';
import { LocationUpdateLbsDeletePopupComponent } from './location-update-lbs-delete-dialog.component';

@Injectable()
export class LocationUpdateLbsResolvePagingParams implements Resolve<any> {

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

export const locationUpdateRoute: Routes = [
    {
        path: 'location-update-lbs',
        component: LocationUpdateLbsComponent,
        resolve: {
            'pagingParams': LocationUpdateLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.locationUpdate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'location-update-lbs/:id',
        component: LocationUpdateLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.locationUpdate.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const locationUpdatePopupRoute: Routes = [
    {
        path: 'location-update-lbs-new',
        component: LocationUpdateLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.locationUpdate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location-update-lbs/:id/edit',
        component: LocationUpdateLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.locationUpdate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'location-update-lbs/:id/delete',
        component: LocationUpdateLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.locationUpdate.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
