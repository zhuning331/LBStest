import { BaseEntity } from './../../shared';

export class RegionLbs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public polygonRegions?: BaseEntity[],
        public regularRegions?: BaseEntity[],
        public types?: BaseEntity[],
    ) {
    }
}
