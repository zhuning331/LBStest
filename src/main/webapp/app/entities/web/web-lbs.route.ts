import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { WebLbsComponent } from './web-lbs.component';
import { WebLbsDetailComponent } from './web-lbs-detail.component';
import { WebLbsPopupComponent } from './web-lbs-dialog.component';
import { WebLbsDeletePopupComponent } from './web-lbs-delete-dialog.component';

@Injectable()
export class WebLbsResolvePagingParams implements Resolve<any> {

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

export const webRoute: Routes = [
    {
        path: 'web-lbs',
        component: WebLbsComponent,
        resolve: {
            'pagingParams': WebLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.web.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'web-lbs/:id',
        component: WebLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.web.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const webPopupRoute: Routes = [
    {
        path: 'web-lbs-new',
        component: WebLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.web.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'web-lbs/:id/edit',
        component: WebLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.web.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'web-lbs/:id/delete',
        component: WebLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.web.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
