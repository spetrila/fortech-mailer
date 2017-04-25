import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { Campaign } from './campaign.model';
import { CampaignService } from './campaign.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-campaign',
    templateUrl: './campaign.component.html'
})
export class CampaignComponent implements OnInit, OnDestroy {
campaigns: Campaign[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private campaignService: CampaignService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['campaign']);
    }

    loadAll() {
        this.campaignService.query().subscribe(
            (res: Response) => {
                this.campaigns = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCampaigns();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Campaign) {
        return item.id;
    }
    registerChangeInCampaigns() {
        this.eventSubscriber = this.eventManager.subscribe('campaignListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    sendCampaign(campaign: Campaign) {
        this.campaignService.send(campaign).subscribe((res: Campaign) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Campaign) {
        this.eventManager.broadcast({ name: 'campaignListModification', content: 'OK'});
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.onError(error);
    }
}
