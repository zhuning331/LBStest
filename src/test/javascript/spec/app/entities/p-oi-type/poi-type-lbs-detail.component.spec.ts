/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { POITypeLbsDetailComponent } from '../../../../../../main/webapp/app/entities/p-oi-type/poi-type-lbs-detail.component';
import { POITypeLbsService } from '../../../../../../main/webapp/app/entities/p-oi-type/poi-type-lbs.service';
import { POITypeLbs } from '../../../../../../main/webapp/app/entities/p-oi-type/poi-type-lbs.model';

describe('Component Tests', () => {

    describe('POITypeLbs Management Detail Component', () => {
        let comp: POITypeLbsDetailComponent;
        let fixture: ComponentFixture<POITypeLbsDetailComponent>;
        let service: POITypeLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [POITypeLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    POITypeLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(POITypeLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(POITypeLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(POITypeLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new POITypeLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pOIType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
