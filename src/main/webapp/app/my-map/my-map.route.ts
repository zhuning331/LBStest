import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { MyMapComponent } from './';

export const MyMAP_ROUTE: Route = {
    path: 'my-map',
    component: MyMapComponent,
    data: {
        authorities: ['ROLE_USER']
    }
};
