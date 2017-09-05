/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { MapLbsDetailComponent } from '../../../../../../main/webapp/app/entities/map/map-lbs-detail.component';
import { MapLbsService } from '../../../../../../main/webapp/app/entities/map/map-lbs.service';
import { MapLbs } from '../../../../../../main/webapp/app/entities/map/map-lbs.model';

describe('Component Tests', () => {

    describe('MapLbs Management Detail Component', () => {
        let comp: MapLbsDetailComponent;
        let fixture: ComponentFixture<MapLbsDetailComponent>;
        let service: MapLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [MapLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    MapLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(MapLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MapLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MapLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new MapLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.map).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
