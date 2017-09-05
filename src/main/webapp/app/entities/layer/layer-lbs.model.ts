import { BaseEntity } from './../../shared';

export class LayerLbs implements BaseEntity {
    constructor(
        public id?: number,
        public priority?: number,
        public name?: string,
        public imageContentType?: string,
        public image?: any,
        public opacity?: number,
        public rotation?: number,
        public centerX?: number,
        public centerY?: number,
        public scaleX?: number,
        public scaleY?: number,
        public cropXMin?: number,
        public cropYMin?: number,
        public cropXMax?: number,
        public centerLongitude?: number,
        public centerLatitude?: number,
        public altitude?: number,
        public createTime?: any,
        public updateTime?: any,
        public deleteTime?: any,
    ) {
    }
}
