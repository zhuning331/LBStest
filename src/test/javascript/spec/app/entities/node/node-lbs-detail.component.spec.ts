/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { NodeLbsDetailComponent } from '../../../../../../main/webapp/app/entities/node/node-lbs-detail.component';
import { NodeLbsService } from '../../../../../../main/webapp/app/entities/node/node-lbs.service';
import { NodeLbs } from '../../../../../../main/webapp/app/entities/node/node-lbs.model';

describe('Component Tests', () => {

    describe('NodeLbs Management Detail Component', () => {
        let comp: NodeLbsDetailComponent;
        let fixture: ComponentFixture<NodeLbsDetailComponent>;
        let service: NodeLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [NodeLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    NodeLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(NodeLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NodeLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NodeLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new NodeLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.node).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
