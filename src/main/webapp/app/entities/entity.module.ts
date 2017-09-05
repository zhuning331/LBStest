import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { LbStestServiceConfigLbsModule } from './service-config/service-config-lbs.module';
import { LbStestWorkspaceConfigLbsModule } from './workspace-config/workspace-config-lbs.module';
import { LbStestLanguageLbsModule } from './language/language-lbs.module';
import { LbStestWorkspaceLbsModule } from './workspace/workspace-lbs.module';
import { LbStestMapLbsModule } from './map/map-lbs.module';
import { LbStestLayerLbsModule } from './layer/layer-lbs.module';
import { LbStestPOILbsModule } from './p-oi/poi-lbs.module';
import { LbStestPOITypeLbsModule } from './p-oi-type/poi-type-lbs.module';
import { LbStestPOIHistoricalLocationLbsModule } from './p-oi-historical-location/poi-historical-location-lbs.module';
import { LbStestNodeLbsModule } from './node/node-lbs.module';
import { LbStestEdgeLbsModule } from './edge/edge-lbs.module';
import { LbStestRegionLbsModule } from './region/region-lbs.module';
import { LbStestRegionTypeLbsModule } from './region-type/region-type-lbs.module';
import { LbStestPolygonRegionLbsModule } from './polygon-region/polygon-region-lbs.module';
import { LbStestRegularRegionLbsModule } from './regular-region/regular-region-lbs.module';
import { LbStestWebLbsModule } from './web/web-lbs.module';
import { LbStestWebTypeLbsModule } from './web-type/web-type-lbs.module';
import { LbStestEdgeTypeLbsModule } from './edge-type/edge-type-lbs.module';
import { LbStestBindingPOILbsModule } from './binding-poi/binding-poi-lbs.module';
import { LbStestLocationUpdateLbsModule } from './location-update/location-update-lbs.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        LbStestServiceConfigLbsModule,
        LbStestWorkspaceConfigLbsModule,
        LbStestLanguageLbsModule,
        LbStestWorkspaceLbsModule,
        LbStestMapLbsModule,
        LbStestLayerLbsModule,
        LbStestPOILbsModule,
        LbStestPOITypeLbsModule,
        LbStestPOIHistoricalLocationLbsModule,
        LbStestNodeLbsModule,
        LbStestEdgeLbsModule,
        LbStestRegionLbsModule,
        LbStestRegionTypeLbsModule,
        LbStestPolygonRegionLbsModule,
        LbStestRegularRegionLbsModule,
        LbStestWebLbsModule,
        LbStestWebTypeLbsModule,
        LbStestEdgeTypeLbsModule,
        LbStestBindingPOILbsModule,
        LbStestLocationUpdateLbsModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LbStestEntityModule {}
