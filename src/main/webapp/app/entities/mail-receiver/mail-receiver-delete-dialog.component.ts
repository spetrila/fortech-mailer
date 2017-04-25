import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { MailReceiver } from './mail-receiver.model';
import { MailReceiverPopupService } from './mail-receiver-popup.service';
import { MailReceiverService } from './mail-receiver.service';

@Component({
    selector: 'jhi-mail-receiver-delete-dialog',
    templateUrl: './mail-receiver-delete-dialog.component.html'
})
export class MailReceiverDeleteDialogComponent {

    mailReceiver: MailReceiver;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private mailReceiverService: MailReceiverService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['mailReceiver']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mailReceiverService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mailReceiverListModification',
                content: 'Deleted an mailReceiver'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mail-receiver-delete-popup',
    template: ''
})
export class MailReceiverDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mailReceiverPopupService: MailReceiverPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.mailReceiverPopupService
                .open(MailReceiverDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
