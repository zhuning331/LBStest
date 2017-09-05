/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RegionLbsDetailComponent } from '../../../../../../main/webapp/app/entities/region/region-lbs-detail.component';
import { RegionLbsService } from '../../../../../../main/webapp/app/entities/region/region-lbs.service';
import { RegionLbs } from '../../../../../../main/webapp/app/entities/region/region-lbs.model';

describe('Component Tests', () => {

    describe('RegionLbs Management Detail Component', () => {
        let comp: RegionLbsDetailComponent;
        let fixture: ComponentFixture<RegionLbsDetailComponent>;
        let service: RegionLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [RegionLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RegionLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(RegionLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RegionLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegionLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RegionLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.region).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
