import { BaseEntity } from './../../shared';

const enum LayerDisplayMode {
    'SINGLE',
    'MULTIPLE'
}

export class MapLbs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public layerDisplayMode?: LayerDisplayMode,
        public tileURL?: string,
        public longitude?: number,
        public latitude?: number,
        public altitude?: number,
        public zoomLevel?: number,
        public rotation?: number,
        public fixRotation?: boolean,
        public showMap?: boolean,
        public showLayer?: boolean,
        public showCenterAsPOI?: boolean,
        public iconContentType?: string,
        public icon?: any,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public workspaceId?: number,
        public initLayerId?: number,
        public layers?: BaseEntity[],
        public webs?: BaseEntity[],
        public regions?: BaseEntity[],
    ) {
        this.fixRotation = false;
        this.showMap = false;
        this.showLayer = false;
        this.showCenterAsPOI = false;
    }
}
