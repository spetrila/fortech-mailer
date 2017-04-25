import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FortechMailerSharedModule } from '../../shared';
import {
    MailReceiverService,
    MailReceiverPopupService,
    MailReceiverComponent,
    MailReceiverDetailComponent,
    MailReceiverDialogComponent,
    MailReceiverPopupComponent,
    MailReceiverDeletePopupComponent,
    MailReceiverDeleteDialogComponent,
    mailReceiverRoute,
    mailReceiverPopupRoute,
} from './';

const ENTITY_STATES = [
    ...mailReceiverRoute,
    ...mailReceiverPopupRoute,
];

@NgModule({
    imports: [
        FortechMailerSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        MailReceiverComponent,
        MailReceiverDetailComponent,
        MailReceiverDialogComponent,
        MailReceiverDeleteDialogComponent,
        MailReceiverPopupComponent,
        MailReceiverDeletePopupComponent,
    ],
    entryComponents: [
        MailReceiverComponent,
        MailReceiverDialogComponent,
        MailReceiverPopupComponent,
        MailReceiverDeleteDialogComponent,
        MailReceiverDeletePopupComponent,
    ],
    providers: [
        MailReceiverService,
        MailReceiverPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FortechMailerMailReceiverModule {}
