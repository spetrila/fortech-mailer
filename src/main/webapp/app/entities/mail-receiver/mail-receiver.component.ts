import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { MailReceiver } from './mail-receiver.model';
import { MailReceiverService } from './mail-receiver.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-mail-receiver',
    templateUrl: './mail-receiver.component.html'
})
export class MailReceiverComponent implements OnInit, OnDestroy {
mailReceivers: MailReceiver[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private mailReceiverService: MailReceiverService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['mailReceiver']);
    }

    loadAll() {
        this.mailReceiverService.query().subscribe(
            (res: Response) => {
                this.mailReceivers = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMailReceivers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MailReceiver) {
        return item.id;
    }
    registerChangeInMailReceivers() {
        this.eventSubscriber = this.eventManager.subscribe('mailReceiverListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
