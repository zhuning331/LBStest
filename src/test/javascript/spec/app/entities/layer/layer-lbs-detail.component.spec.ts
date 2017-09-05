/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LayerLbsDetailComponent } from '../../../../../../main/webapp/app/entities/layer/layer-lbs-detail.component';
import { LayerLbsService } from '../../../../../../main/webapp/app/entities/layer/layer-lbs.service';
import { LayerLbs } from '../../../../../../main/webapp/app/entities/layer/layer-lbs.model';

describe('Component Tests', () => {

    describe('LayerLbs Management Detail Component', () => {
        let comp: LayerLbsDetailComponent;
        let fixture: ComponentFixture<LayerLbsDetailComponent>;
        let service: LayerLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [LayerLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LayerLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(LayerLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LayerLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LayerLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LayerLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.layer).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
