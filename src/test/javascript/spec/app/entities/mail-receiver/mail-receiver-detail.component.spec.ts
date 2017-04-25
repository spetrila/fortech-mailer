import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { FortechMailerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MailReceiverDetailComponent } from '../../../../../../main/webapp/app/entities/mail-receiver/mail-receiver-detail.component';
import { MailReceiverService } from '../../../../../../main/webapp/app/entities/mail-receiver/mail-receiver.service';
import { MailReceiver } from '../../../../../../main/webapp/app/entities/mail-receiver/mail-receiver.model';

describe('Component Tests', () => {

    describe('MailReceiver Management Detail Component', () => {
        let comp: MailReceiverDetailComponent;
        let fixture: ComponentFixture<MailReceiverDetailComponent>;
        let service: MailReceiverService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FortechMailerTestModule],
                declarations: [MailReceiverDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MailReceiverService,
                    EventManager
                ]
            }).overrideComponent(MailReceiverDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MailReceiverDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MailReceiverService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MailReceiver(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.mailReceiver).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
