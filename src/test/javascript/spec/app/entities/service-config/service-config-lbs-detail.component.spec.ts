/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ServiceConfigLbsDetailComponent } from '../../../../../../main/webapp/app/entities/service-config/service-config-lbs-detail.component';
import { ServiceConfigLbsService } from '../../../../../../main/webapp/app/entities/service-config/service-config-lbs.service';
import { ServiceConfigLbs } from '../../../../../../main/webapp/app/entities/service-config/service-config-lbs.model';

describe('Component Tests', () => {

    describe('ServiceConfigLbs Management Detail Component', () => {
        let comp: ServiceConfigLbsDetailComponent;
        let fixture: ComponentFixture<ServiceConfigLbsDetailComponent>;
        let service: ServiceConfigLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [ServiceConfigLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ServiceConfigLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(ServiceConfigLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ServiceConfigLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServiceConfigLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ServiceConfigLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.serviceConfig).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
