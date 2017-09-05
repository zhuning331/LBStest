import { BaseEntity } from './../../shared';

export class WebLbs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public edges?: BaseEntity[],
        public types?: BaseEntity[],
    ) {
    }
}
