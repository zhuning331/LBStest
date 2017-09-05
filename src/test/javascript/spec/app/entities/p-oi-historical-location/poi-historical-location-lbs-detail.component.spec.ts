/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { POIHistoricalLocationLbsDetailComponent } from '../../../../../../main/webapp/app/entities/p-oi-historical-location/poi-historical-location-lbs-detail.component';
import { POIHistoricalLocationLbsService } from '../../../../../../main/webapp/app/entities/p-oi-historical-location/poi-historical-location-lbs.service';
import { POIHistoricalLocationLbs } from '../../../../../../main/webapp/app/entities/p-oi-historical-location/poi-historical-location-lbs.model';

describe('Component Tests', () => {

    describe('POIHistoricalLocationLbs Management Detail Component', () => {
        let comp: POIHistoricalLocationLbsDetailComponent;
        let fixture: ComponentFixture<POIHistoricalLocationLbsDetailComponent>;
        let service: POIHistoricalLocationLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [POIHistoricalLocationLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    POIHistoricalLocationLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(POIHistoricalLocationLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(POIHistoricalLocationLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(POIHistoricalLocationLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new POIHistoricalLocationLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pOIHistoricalLocation).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
