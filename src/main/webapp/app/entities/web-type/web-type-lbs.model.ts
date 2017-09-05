import { BaseEntity } from './../../shared';

export class WebTypeLbs implements BaseEntity {
    constructor(
        public id?: number,
        public type?: string,
        public showWeb?: boolean,
        public color?: string,
        public width?: number,
        public layerId?: number,
        public priority?: number,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
        public workspaceId?: number,
    ) {
        this.showWeb = false;
    }
}
