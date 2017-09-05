/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { WorkspaceConfigLbsDetailComponent } from '../../../../../../main/webapp/app/entities/workspace-config/workspace-config-lbs-detail.component';
import { WorkspaceConfigLbsService } from '../../../../../../main/webapp/app/entities/workspace-config/workspace-config-lbs.service';
import { WorkspaceConfigLbs } from '../../../../../../main/webapp/app/entities/workspace-config/workspace-config-lbs.model';

describe('Component Tests', () => {

    describe('WorkspaceConfigLbs Management Detail Component', () => {
        let comp: WorkspaceConfigLbsDetailComponent;
        let fixture: ComponentFixture<WorkspaceConfigLbsDetailComponent>;
        let service: WorkspaceConfigLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [WorkspaceConfigLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    WorkspaceConfigLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(WorkspaceConfigLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(WorkspaceConfigLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WorkspaceConfigLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new WorkspaceConfigLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.workspaceConfig).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
