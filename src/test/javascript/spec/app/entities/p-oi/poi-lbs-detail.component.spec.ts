/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { POILbsDetailComponent } from '../../../../../../main/webapp/app/entities/p-oi/poi-lbs-detail.component';
import { POILbsService } from '../../../../../../main/webapp/app/entities/p-oi/poi-lbs.service';
import { POILbs } from '../../../../../../main/webapp/app/entities/p-oi/poi-lbs.model';

describe('Component Tests', () => {

    describe('POILbs Management Detail Component', () => {
        let comp: POILbsDetailComponent;
        let fixture: ComponentFixture<POILbsDetailComponent>;
        let service: POILbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [POILbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    POILbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(POILbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(POILbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(POILbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new POILbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pOI).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
