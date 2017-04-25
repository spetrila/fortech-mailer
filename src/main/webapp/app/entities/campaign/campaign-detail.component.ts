import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Campaign } from './campaign.model';
import { CampaignService } from './campaign.service';

@Component({
    selector: 'jhi-campaign-detail',
    templateUrl: './campaign-detail.component.html'
})
export class CampaignDetailComponent implements OnInit, OnDestroy {

    campaign: Campaign;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private campaignService: CampaignService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['campaign']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCampaigns();
    }

    load(id) {
        this.campaignService.find(id).subscribe((campaign) => {
            this.campaign = campaign;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCampaigns() {
        this.eventSubscriber = this.eventManager.subscribe('campaignListModification', (response) => this.load(this.campaign.id));
    }
}
