import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LbStestSharedModule } from '../shared';

import { MyMAP_ROUTE, MyMapComponent } from './';

@NgModule({
    imports: [
        LbStestSharedModule,
        RouterModule.forRoot([ MyMAP_ROUTE ], { useHash: true })
    ],
    declarations: [
        MyMapComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestMyMapModule {}
