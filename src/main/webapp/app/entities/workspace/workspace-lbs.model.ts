import { BaseEntity } from './../../shared';

export class WorkspaceLbs implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public token?: string,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public configs?: BaseEntity[],
        public maps?: BaseEntity[],
        public initMapId?: number,
        public ownerId?: number,
    ) {
    }
}
