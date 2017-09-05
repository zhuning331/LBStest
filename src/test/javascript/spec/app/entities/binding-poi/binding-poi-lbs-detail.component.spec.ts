/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BindingPOILbsDetailComponent } from '../../../../../../main/webapp/app/entities/binding-poi/binding-poi-lbs-detail.component';
import { BindingPOILbsService } from '../../../../../../main/webapp/app/entities/binding-poi/binding-poi-lbs.service';
import { BindingPOILbs } from '../../../../../../main/webapp/app/entities/binding-poi/binding-poi-lbs.model';

describe('Component Tests', () => {

    describe('BindingPOILbs Management Detail Component', () => {
        let comp: BindingPOILbsDetailComponent;
        let fixture: ComponentFixture<BindingPOILbsDetailComponent>;
        let service: BindingPOILbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [BindingPOILbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BindingPOILbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(BindingPOILbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BindingPOILbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BindingPOILbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new BindingPOILbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.bindingPOI).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
