/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LocationUpdateLbsDetailComponent } from '../../../../../../main/webapp/app/entities/location-update/location-update-lbs-detail.component';
import { LocationUpdateLbsService } from '../../../../../../main/webapp/app/entities/location-update/location-update-lbs.service';
import { LocationUpdateLbs } from '../../../../../../main/webapp/app/entities/location-update/location-update-lbs.model';

describe('Component Tests', () => {

    describe('LocationUpdateLbs Management Detail Component', () => {
        let comp: LocationUpdateLbsDetailComponent;
        let fixture: ComponentFixture<LocationUpdateLbsDetailComponent>;
        let service: LocationUpdateLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [LocationUpdateLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LocationUpdateLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(LocationUpdateLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationUpdateLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationUpdateLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LocationUpdateLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.locationUpdate).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
