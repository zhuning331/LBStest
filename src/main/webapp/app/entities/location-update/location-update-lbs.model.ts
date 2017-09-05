import { BaseEntity } from './../../shared';

export class LocationUpdateLbs implements BaseEntity {
    constructor(
        public id?: number,
        public longitude?: number,
        public latitude?: number,
        public altitude?: number,
        public bearing?: number,
        public speed?: number,
        public layerId?: number,
        public time?: any,
    ) {
    }
}
