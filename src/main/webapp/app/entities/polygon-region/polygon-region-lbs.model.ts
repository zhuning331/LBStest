import { BaseEntity } from './../../shared';

export class PolygonRegionLbs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public regionId?: number,
        public nodes?: BaseEntity[],
    ) {
    }
}
