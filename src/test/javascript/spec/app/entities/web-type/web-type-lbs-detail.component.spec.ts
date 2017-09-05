/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WebTypeLbsDetailComponent } from '../../../../../../main/webapp/app/entities/web-type/web-type-lbs-detail.component';
import { WebTypeLbsService } from '../../../../../../main/webapp/app/entities/web-type/web-type-lbs.service';
import { WebTypeLbs } from '../../../../../../main/webapp/app/entities/web-type/web-type-lbs.model';

describe('Component Tests', () => {

    describe('WebTypeLbs Management Detail Component', () => {
        let comp: WebTypeLbsDetailComponent;
        let fixture: ComponentFixture<WebTypeLbsDetailComponent>;
        let service: WebTypeLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [WebTypeLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WebTypeLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(WebTypeLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WebTypeLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WebTypeLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new WebTypeLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.webType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
