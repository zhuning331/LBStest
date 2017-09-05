import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { RegularRegionLbs } from './regular-region-lbs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RegularRegionLbsService {

    private resourceUrl = 'api/regular-regions';
    private resourceSearchUrl = 'api/_search/regular-regions';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(regularRegion: RegularRegionLbs): Observable<RegularRegionLbs> {
        const copy = this.convert(regularRegion);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(regularRegion: RegularRegionLbs): Observable<RegularRegionLbs> {
        const copy = this.convert(regularRegion);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<RegularRegionLbs> {
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

    private convert(regularRegion: RegularRegionLbs): RegularRegionLbs {
        const copy: RegularRegionLbs = Object.assign({}, regularRegion);

        copy.createTime = this.dateUtils.toDate(regularRegion.createTime);

        copy.updateTime = this.dateUtils.toDate(regularRegion.updateTime);

        copy.deleteTime = this.dateUtils.toDate(regularRegion.deleteTime);
        return copy;
    }
}
