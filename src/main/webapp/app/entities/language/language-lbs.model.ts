import { BaseEntity } from './../../shared';

export class LanguageLbs implements BaseEntity {
    constructor(
        public id?: number,
        public workspaceToken?: string,
        public type?: string,
        public identity?: string,
        public value?: string,
        public target?: string,
        public code?: string,
    ) {
    }
}
