/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WebLbsDetailComponent } from '../../../../../../main/webapp/app/entities/web/web-lbs-detail.component';
import { WebLbsService } from '../../../../../../main/webapp/app/entities/web/web-lbs.service';
import { WebLbs } from '../../../../../../main/webapp/app/entities/web/web-lbs.model';

describe('Component Tests', () => {

    describe('WebLbs Management Detail Component', () => {
        let comp: WebLbsDetailComponent;
        let fixture: ComponentFixture<WebLbsDetailComponent>;
        let service: WebLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [WebLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WebLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(WebLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WebLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WebLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new WebLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.web).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
