import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { Template } from './template.model';
import { TemplateService } from './template.service';

@Component({
    selector: 'jhi-template-detail',
    templateUrl: './template-detail.component.html'
})
export class TemplateDetailComponent implements OnInit, OnDestroy {

    template: Template;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private templateService: TemplateService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['template']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTemplates();
    }

    load(id) {
        this.templateService.find(id).subscribe((template) => {
            this.template = template;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTemplates() {
        this.eventSubscriber = this.eventManager.subscribe('templateListModification', (response) => this.load(this.template.id));
    }
}
