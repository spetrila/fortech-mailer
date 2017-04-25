import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { MailReceiver } from './mail-receiver.model';
import { MailReceiverService } from './mail-receiver.service';

@Component({
    selector: 'jhi-mail-receiver-detail',
    templateUrl: './mail-receiver-detail.component.html'
})
export class MailReceiverDetailComponent implements OnInit, OnDestroy {

    mailReceiver: MailReceiver;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private mailReceiverService: MailReceiverService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['mailReceiver']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMailReceivers();
    }

    load(id) {
        this.mailReceiverService.find(id).subscribe((mailReceiver) => {
            this.mailReceiver = mailReceiver;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMailReceivers() {
        this.eventSubscriber = this.eventManager.subscribe('mailReceiverListModification', (response) => this.load(this.mailReceiver.id));
    }
}
