import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { BindingPOILbsComponent } from './binding-poi-lbs.component';
import { BindingPOILbsDetailComponent } from './binding-poi-lbs-detail.component';
import { BindingPOILbsPopupComponent } from './binding-poi-lbs-dialog.component';
import { BindingPOILbsDeletePopupComponent } from './binding-poi-lbs-delete-dialog.component';

@Injectable()
export class BindingPOILbsResolvePagingParams implements Resolve<any> {

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

export const bindingPOIRoute: Routes = [
    {
        path: 'binding-poi-lbs',
        component: BindingPOILbsComponent,
        resolve: {
            'pagingParams': BindingPOILbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.bindingPOI.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'binding-poi-lbs/:id',
        component: BindingPOILbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.bindingPOI.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bindingPOIPopupRoute: Routes = [
    {
        path: 'binding-poi-lbs-new',
        component: BindingPOILbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.bindingPOI.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'binding-poi-lbs/:id/edit',
        component: BindingPOILbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.bindingPOI.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'binding-poi-lbs/:id/delete',
        component: BindingPOILbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.bindingPOI.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
