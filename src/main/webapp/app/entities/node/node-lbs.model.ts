import { BaseEntity } from './../../shared';

export class NodeLbs implements BaseEntity {
    constructor(
        public id?: number,
        public order?: number,
        public longitude?: number,
        public latitude?: number,
        public altitude?: number,
        public poiId?: number,
        public layerId?: number,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public polygonRegionId?: number,
    ) {
    }
}
