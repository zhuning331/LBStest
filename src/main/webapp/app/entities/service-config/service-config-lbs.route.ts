import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ServiceConfigLbsComponent } from './service-config-lbs.component';
import { ServiceConfigLbsDetailComponent } from './service-config-lbs-detail.component';
import { ServiceConfigLbsPopupComponent } from './service-config-lbs-dialog.component';
import { ServiceConfigLbsDeletePopupComponent } from './service-config-lbs-delete-dialog.component';

@Injectable()
export class ServiceConfigLbsResolvePagingParams implements Resolve<any> {

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

export const serviceConfigRoute: Routes = [
    {
        path: 'service-config-lbs',
        component: ServiceConfigLbsComponent,
        resolve: {
            'pagingParams': ServiceConfigLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.serviceConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'service-config-lbs/:id',
        component: ServiceConfigLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.serviceConfig.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const serviceConfigPopupRoute: Routes = [
    {
        path: 'service-config-lbs-new',
        component: ServiceConfigLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.serviceConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service-config-lbs/:id/edit',
        component: ServiceConfigLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.serviceConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'service-config-lbs/:id/delete',
        component: ServiceConfigLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.serviceConfig.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
