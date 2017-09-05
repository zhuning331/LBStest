/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EdgeTypeLbsDetailComponent } from '../../../../../../main/webapp/app/entities/edge-type/edge-type-lbs-detail.component';
import { EdgeTypeLbsService } from '../../../../../../main/webapp/app/entities/edge-type/edge-type-lbs.service';
import { EdgeTypeLbs } from '../../../../../../main/webapp/app/entities/edge-type/edge-type-lbs.model';

describe('Component Tests', () => {

    describe('EdgeTypeLbs Management Detail Component', () => {
        let comp: EdgeTypeLbsDetailComponent;
        let fixture: ComponentFixture<EdgeTypeLbsDetailComponent>;
        let service: EdgeTypeLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [EdgeTypeLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EdgeTypeLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(EdgeTypeLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EdgeTypeLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EdgeTypeLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EdgeTypeLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.edgeType).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
