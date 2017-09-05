/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PolygonRegionLbsDetailComponent } from '../../../../../../main/webapp/app/entities/polygon-region/polygon-region-lbs-detail.component';
import { PolygonRegionLbsService } from '../../../../../../main/webapp/app/entities/polygon-region/polygon-region-lbs.service';
import { PolygonRegionLbs } from '../../../../../../main/webapp/app/entities/polygon-region/polygon-region-lbs.model';

describe('Component Tests', () => {

    describe('PolygonRegionLbs Management Detail Component', () => {
        let comp: PolygonRegionLbsDetailComponent;
        let fixture: ComponentFixture<PolygonRegionLbsDetailComponent>;
        let service: PolygonRegionLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [PolygonRegionLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PolygonRegionLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(PolygonRegionLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PolygonRegionLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PolygonRegionLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PolygonRegionLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.polygonRegion).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
