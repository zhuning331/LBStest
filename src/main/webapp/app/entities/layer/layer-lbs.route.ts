import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LayerLbsComponent } from './layer-lbs.component';
import { LayerLbsDetailComponent } from './layer-lbs-detail.component';
import { LayerLbsPopupComponent } from './layer-lbs-dialog.component';
import { LayerLbsDeletePopupComponent } from './layer-lbs-delete-dialog.component';

@Injectable()
export class LayerLbsResolvePagingParams implements Resolve<any> {

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

export const layerRoute: Routes = [
    {
        path: 'layer-lbs',
        component: LayerLbsComponent,
        resolve: {
            'pagingParams': LayerLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'layer-lbs/:id',
        component: LayerLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const layerPopupRoute: Routes = [
    {
        path: 'layer-lbs-new',
        component: LayerLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'layer-lbs/:id/edit',
        component: LayerLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'layer-lbs/:id/delete',
        component: LayerLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.layer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
