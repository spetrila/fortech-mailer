import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FortechMailerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TemplateDetailComponent } from '../../../../../../main/webapp/app/entities/template/template-detail.component';
import { TemplateService } from '../../../../../../main/webapp/app/entities/template/template.service';
import { Template } from '../../../../../../main/webapp/app/entities/template/template.model';

describe('Component Tests', () => {

    describe('Template Management Detail Component', () => {
        let comp: TemplateDetailComponent;
        let fixture: ComponentFixture<TemplateDetailComponent>;
        let service: TemplateService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FortechMailerTestModule],
                declarations: [TemplateDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TemplateService,
                    EventManager
                ]
            }).overrideComponent(TemplateDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TemplateDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TemplateService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Template(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.template).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
