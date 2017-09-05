import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ServiceConfigLbs } from './service-config-lbs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ServiceConfigLbsService {

    private resourceUrl = 'api/service-configs';
    private resourceSearchUrl = 'api/_search/service-configs';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(serviceConfig: ServiceConfigLbs): Observable<ServiceConfigLbs> {
        const copy = this.convert(serviceConfig);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(serviceConfig: ServiceConfigLbs): Observable<ServiceConfigLbs> {
        const copy = this.convert(serviceConfig);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ServiceConfigLbs> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.createTime = this.dateUtils
            .convertDateTimeFromServer(entity.createTime);
        entity.updateTime = this.dateUtils
            .convertDateTimeFromServer(entity.updateTime);
        entity.deleteTime = this.dateUtils
            .convertDateTimeFromServer(entity.deleteTime);
    }

    private convert(serviceConfig: ServiceConfigLbs): ServiceConfigLbs {
        const copy: ServiceConfigLbs = Object.assign({}, serviceConfig);

        copy.createTime = this.dateUtils.toDate(serviceConfig.createTime);

        copy.updateTime = this.dateUtils.toDate(serviceConfig.updateTime);

        copy.deleteTime = this.dateUtils.toDate(serviceConfig.deleteTime);
        return copy;
    }
}
