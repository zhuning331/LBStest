import { BaseEntity } from './../../shared';

export class POILbs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public longitude?: number,
        public latitude?: number,
        public altitude?: number,
        public bearing?: number,
        public iconContentType?: string,
        public icon?: any,
        public speed?: number,
        public profile?: string,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public types?: BaseEntity[],
    ) {
    }
}
