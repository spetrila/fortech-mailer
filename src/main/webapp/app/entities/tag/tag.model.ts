export class Tag {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public mailReceiversId?: number,
        public campaignsId?: number,
    ) {
    }
}
