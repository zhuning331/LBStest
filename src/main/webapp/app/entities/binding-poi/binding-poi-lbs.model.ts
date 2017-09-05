import { BaseEntity } from './../../shared';

export class BindingPOILbs implements BaseEntity {
    constructor(
        public id?: number,
        public bindingType?: string,
        public bindingKey?: string,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public poiId?: number,
    ) {
    }
}
