import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Campaign } from './campaign.model';
import { CampaignPopupService } from './campaign-popup.service';
import { CampaignService } from './campaign.service';
import { Template, TemplateService } from '../template';
import { User, UserService } from '../../shared';
import { Tag, TagService } from '../tag';

@Component({
    selector: 'jhi-campaign-dialog',
    templateUrl: './campaign-dialog.component.html'
})
export class CampaignDialogComponent implements OnInit {

    campaign: Campaign;
    authorities: any[];
    isSaving: boolean;

    templates: Template[];

    users: User[];

    tags: Tag[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private campaignService: CampaignService,
        private templateService: TemplateService,
        private userService: UserService,
        private tagService: TagService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['campaign']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.templateService.query({filter: 'campaign-is-null'}).subscribe((res: Response) => {
            if (!this.campaign.templateId) {
                this.templates = res.json();
            } else {
                this.templateService.find(this.campaign.templateId).subscribe((subRes: Template) => {
                    this.templates = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
        this.tagService.query().subscribe(
            (res: Response) => { this.tags = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.campaign.id !== undefined) {
            this.campaignService.update(this.campaign)
                .subscribe((res: Campaign) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.campaignService.create(this.campaign)
                .subscribe((res: Campaign) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Campaign) {
        this.eventManager.broadcast({ name: 'campaignListModification', content: 'OK'});
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

    trackTemplateById(index: number, item: Template) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
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
    selector: 'jhi-campaign-popup',
    template: ''
})
export class CampaignPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private campaignPopupService: CampaignPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.campaignPopupService
                    .open(CampaignDialogComponent, params['id']);
            } else {
                this.modalRef = this.campaignPopupService
                    .open(CampaignDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
