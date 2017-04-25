import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FortechMailerSharedModule } from '../../shared';
import { FortechMailerAdminModule } from '../../admin/admin.module';
import {
    CampaignService,
    CampaignPopupService,
    CampaignComponent,
    CampaignDetailComponent,
    CampaignDialogComponent,
    CampaignPopupComponent,
    CampaignDeletePopupComponent,
    CampaignDeleteDialogComponent,
    campaignRoute,
    campaignPopupRoute,
} from './';

const ENTITY_STATES = [
    ...campaignRoute,
    ...campaignPopupRoute,
];

@NgModule({
    imports: [
        FortechMailerSharedModule,
        FortechMailerAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CampaignComponent,
        CampaignDetailComponent,
        CampaignDialogComponent,
        CampaignDeleteDialogComponent,
        CampaignPopupComponent,
        CampaignDeletePopupComponent,
    ],
    entryComponents: [
        CampaignComponent,
        CampaignDialogComponent,
        CampaignPopupComponent,
        CampaignDeleteDialogComponent,
        CampaignDeletePopupComponent,
    ],
    providers: [
        CampaignService,
        CampaignPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FortechMailerCampaignModule {}
