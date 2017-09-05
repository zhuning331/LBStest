import { BaseEntity } from './../../shared';

export class WorkspaceConfigLbs implements BaseEntity {
    constructor(
        public id?: number,
        public key?: string,
        public value?: string,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public workspaceId?: number,
    ) {
    }
}
