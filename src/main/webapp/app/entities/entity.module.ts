import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FortechMailerMailReceiverModule } from './mail-receiver/mail-receiver.module';
import { FortechMailerTagModule } from './tag/tag.module';
import { FortechMailerTemplateModule } from './template/template.module';
import { FortechMailerCampaignModule } from './campaign/campaign.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        FortechMailerMailReceiverModule,
        FortechMailerTagModule,
        FortechMailerTemplateModule,
        FortechMailerCampaignModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FortechMailerEntityModule {}
