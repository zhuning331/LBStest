import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { POITypeLbs } from './poi-type-lbs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class POITypeLbsService {

    private resourceUrl = 'api/p-oi-types';
    private resourceSearchUrl = 'api/_search/p-oi-types';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pOIType: POITypeLbs): Observable<POITypeLbs> {
        const copy = this.convert(pOIType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(pOIType: POITypeLbs): Observable<POITypeLbs> {
        const copy = this.convert(pOIType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<POITypeLbs> {
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

    private convert(pOIType: POITypeLbs): POITypeLbs {
        const copy: POITypeLbs = Object.assign({}, pOIType);

        copy.createTime = this.dateUtils.toDate(pOIType.createTime);

        copy.updateTime = this.dateUtils.toDate(pOIType.updateTime);

        copy.deleteTime = this.dateUtils.toDate(pOIType.deleteTime);
        return copy;
    }
}
