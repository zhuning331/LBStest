import { BaseEntity } from './../../shared';

export class EdgeLbs implements BaseEntity {
    constructor(
        public id?: number,
        public order?: number,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public fromId?: number,
        public toId?: number,
        public types?: BaseEntity[],
        public webId?: number,
    ) {
    }
}
