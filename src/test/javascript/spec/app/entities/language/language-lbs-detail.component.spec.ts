/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { LbStestTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LanguageLbsDetailComponent } from '../../../../../../main/webapp/app/entities/language/language-lbs-detail.component';
import { LanguageLbsService } from '../../../../../../main/webapp/app/entities/language/language-lbs.service';
import { LanguageLbs } from '../../../../../../main/webapp/app/entities/language/language-lbs.model';

describe('Component Tests', () => {

    describe('LanguageLbs Management Detail Component', () => {
        let comp: LanguageLbsDetailComponent;
        let fixture: ComponentFixture<LanguageLbsDetailComponent>;
        let service: LanguageLbsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [LbStestTestModule],
                declarations: [LanguageLbsDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LanguageLbsService,
                    JhiEventManager
                ]
            }).overrideTemplate(LanguageLbsDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LanguageLbsDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LanguageLbsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new LanguageLbs(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.language).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
