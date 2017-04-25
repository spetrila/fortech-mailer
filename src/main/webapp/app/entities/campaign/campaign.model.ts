export class Campaign {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public subject?: string,
        public templateId?: number,
        public userId?: number,
        public tagId?: number,
    ) {
    }
}
