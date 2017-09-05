import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WebTypeLbsComponent } from './web-type-lbs.component';
import { WebTypeLbsDetailComponent } from './web-type-lbs-detail.component';
import { WebTypeLbsPopupComponent } from './web-type-lbs-dialog.component';
import { WebTypeLbsDeletePopupComponent } from './web-type-lbs-delete-dialog.component';

@Injectable()
export class WebTypeLbsResolvePagingParams implements Resolve<any> {

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

export const webTypeRoute: Routes = [
    {
        path: 'web-type-lbs',
        component: WebTypeLbsComponent,
        resolve: {
            'pagingParams': WebTypeLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.webType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'web-type-lbs/:id',
        component: WebTypeLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.webType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const webTypePopupRoute: Routes = [
    {
        path: 'web-type-lbs-new',
        component: WebTypeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.webType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'web-type-lbs/:id/edit',
        component: WebTypeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.webType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'web-type-lbs/:id/delete',
        component: WebTypeLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.webType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
