import { BaseEntity } from './../../shared';

export class POITypeLbs implements BaseEntity {
    constructor(
        public id?: number,
        public priority?: number,
        public type?: string,
        public iconContentType?: string,
        public icon?: any,
        public showPOI?: boolean,
        public layerId?: number,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public workspaceId?: number,
    ) {
        this.showPOI = false;
    }
}
