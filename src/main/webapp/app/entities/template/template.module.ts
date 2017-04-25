import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FortechMailerSharedModule } from '../../shared';
import { FortechMailerAdminModule } from '../../admin/admin.module';
import {
    TemplateService,
    TemplatePopupService,
    TemplateComponent,
    TemplateDetailComponent,
    TemplateDialogComponent,
    TemplatePopupComponent,
    TemplateDeletePopupComponent,
    TemplateDeleteDialogComponent,
    templateRoute,
    templatePopupRoute,
} from './';

const ENTITY_STATES = [
    ...templateRoute,
    ...templatePopupRoute,
];

@NgModule({
    imports: [
        FortechMailerSharedModule,
        FortechMailerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TemplateComponent,
        TemplateDetailComponent,
        TemplateDialogComponent,
        TemplateDeleteDialogComponent,
        TemplatePopupComponent,
        TemplateDeletePopupComponent,
    ],
    entryComponents: [
        TemplateComponent,
        TemplateDialogComponent,
        TemplatePopupComponent,
        TemplateDeleteDialogComponent,
        TemplateDeletePopupComponent,
    ],
    providers: [
        TemplateService,
        TemplatePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FortechMailerTemplateModule {}
