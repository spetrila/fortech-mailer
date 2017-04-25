import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { MailReceiver } from './mail-receiver.model';
import { MailReceiverPopupService } from './mail-receiver-popup.service';
import { MailReceiverService } from './mail-receiver.service';
import { Tag, TagService } from '../tag';

@Component({
    selector: 'jhi-mail-receiver-dialog',
    templateUrl: './mail-receiver-dialog.component.html'
})
export class MailReceiverDialogComponent implements OnInit {

    mailReceiver: MailReceiver;
    authorities: any[];
    isSaving: boolean;

    tags: Tag[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private mailReceiverService: MailReceiverService,
        private tagService: TagService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['mailReceiver']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.tagService.query().subscribe(
            (res: Response) => { this.tags = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mailReceiver.id !== undefined) {
            this.mailReceiverService.update(this.mailReceiver)
                .subscribe((res: MailReceiver) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.mailReceiverService.create(this.mailReceiver)
                .subscribe((res: MailReceiver) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: MailReceiver) {
        this.eventManager.broadcast({ name: 'mailReceiverListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackTagById(index: number, item: Tag) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-mail-receiver-popup',
    template: ''
})
export class MailReceiverPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mailReceiverPopupService: MailReceiverPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.mailReceiverPopupService
                    .open(MailReceiverDialogComponent, params['id']);
            } else {
                this.modalRef = this.mailReceiverPopupService
                    .open(MailReceiverDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
