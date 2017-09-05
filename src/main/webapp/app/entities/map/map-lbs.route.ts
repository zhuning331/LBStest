import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { MapLbsComponent } from './map-lbs.component';
import { MapLbsDetailComponent } from './map-lbs-detail.component';
import { MapLbsPopupComponent } from './map-lbs-dialog.component';
import { MapLbsDeletePopupComponent } from './map-lbs-delete-dialog.component';

@Injectable()
export class MapLbsResolvePagingParams implements Resolve<any> {

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

export const mapRoute: Routes = [
    {
        path: 'map-lbs',
        component: MapLbsComponent,
        resolve: {
            'pagingParams': MapLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.map.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'map-lbs/:id',
        component: MapLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.map.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mapPopupRoute: Routes = [
    {
        path: 'map-lbs-new',
        component: MapLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.map.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'map-lbs/:id/edit',
        component: MapLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.map.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'map-lbs/:id/delete',
        component: MapLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.map.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
