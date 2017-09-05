/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WorkspaceLbsDetailComponent } from '../../../../../../main/webapp/app/entities/workspace/workspace-lbs-detail.component';
import { WorkspaceLbsService } from '../../../../../../main/webapp/app/entities/workspace/workspace-lbs.service';
import { WorkspaceLbs } from '../../../../../../main/webapp/app/entities/workspace/workspace-lbs.model';

describe('Component Tests', () => {

    describe('WorkspaceLbs Management Detail Component', () => {
        let comp: WorkspaceLbsDetailComponent;
        let fixture: ComponentFixture<WorkspaceLbsDetailComponent>;
        let service: WorkspaceLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [WorkspaceLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WorkspaceLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(WorkspaceLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WorkspaceLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkspaceLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new WorkspaceLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.workspace).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
