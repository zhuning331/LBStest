import { BaseEntity } from './../../shared';

export class EdgeTypeLbs implements BaseEntity {
    constructor(
        public id?: number,
        public priority?: number,
        public type?: string,
        public color?: string,
        public width?: number,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
    ) {
    }
}
