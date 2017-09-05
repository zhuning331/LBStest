import { BaseEntity } from './../../shared';

export class RegularRegionLbs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public sides?: number,
        public centerLongitude?: number,
        public centerLatitude?: number,
        public altitude?: number,
        public cornerLongitude?: number,
        public cornerLatitude?: number,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public regionId?: number,
    ) {
    }
}
