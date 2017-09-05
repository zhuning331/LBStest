import { BaseEntity } from './../../shared';

export class RegionTypeLbs implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public showRegion?: boolean,
        public borderColor?: string,
        public backgroundImageContentType?: string,
        public backgroundImage?: any,
        public backgroundImageScale?: number,
        public backgroundImageColor?: string,
        public backgroundColor?: string,
        public layerId?: number,
        public priority?: number,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public workspaceId?: number,
    ) {
        this.showRegion = false;
    }
}
