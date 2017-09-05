/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RegularRegionLbsDetailComponent } from '../../../../../../main/webapp/app/entities/regular-region/regular-region-lbs-detail.component';
import { RegularRegionLbsService } from '../../../../../../main/webapp/app/entities/regular-region/regular-region-lbs.service';
import { RegularRegionLbs } from '../../../../../../main/webapp/app/entities/regular-region/regular-region-lbs.model';

describe('Component Tests', () => {

    describe('RegularRegionLbs Management Detail Component', () => {
        let comp: RegularRegionLbsDetailComponent;
        let fixture: ComponentFixture<RegularRegionLbsDetailComponent>;
        let service: RegularRegionLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [RegularRegionLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RegularRegionLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(RegularRegionLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RegularRegionLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegularRegionLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RegularRegionLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.regularRegion).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
