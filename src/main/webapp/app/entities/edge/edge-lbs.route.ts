import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EdgeLbsComponent } from './edge-lbs.component';
import { EdgeLbsDetailComponent } from './edge-lbs-detail.component';
import { EdgeLbsPopupComponent } from './edge-lbs-dialog.component';
import { EdgeLbsDeletePopupComponent } from './edge-lbs-delete-dialog.component';

@Injectable()
export class EdgeLbsResolvePagingParams implements Resolve<any> {

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

export const edgeRoute: Routes = [
    {
        path: 'edge-lbs',
        component: EdgeLbsComponent,
        resolve: {
            'pagingParams': EdgeLbsResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'edge-lbs/:id',
        component: EdgeLbsDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const edgePopupRoute: Routes = [
    {
        path: 'edge-lbs-new',
        component: EdgeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'edge-lbs/:id/edit',
        component: EdgeLbsPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'edge-lbs/:id/delete',
        component: EdgeLbsDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'lbStestApp.edge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
