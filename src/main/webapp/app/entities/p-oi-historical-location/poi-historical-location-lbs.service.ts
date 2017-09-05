import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { POIHistoricalLocationLbs } from './poi-historical-location-lbs.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class POIHistoricalLocationLbsService {

    private resourceUrl = 'api/p-oi-historical-locations';
    private resourceSearchUrl = 'api/_search/p-oi-historical-locations';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pOIHistoricalLocation: POIHistoricalLocationLbs): Observable<POIHistoricalLocationLbs> {
        const copy = this.convert(pOIHistoricalLocation);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(pOIHistoricalLocation: POIHistoricalLocationLbs): Observable<POIHistoricalLocationLbs> {
        const copy = this.convert(pOIHistoricalLocation);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<POIHistoricalLocationLbs> {
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
        entity.time = this.dateUtils
            .convertDateTimeFromServer(entity.time);
    }

    private convert(pOIHistoricalLocation: POIHistoricalLocationLbs): POIHistoricalLocationLbs {
        const copy: POIHistoricalLocationLbs = Object.assign({}, pOIHistoricalLocation);

        copy.time = this.dateUtils.toDate(pOIHistoricalLocation.time);
        return copy;
    }
}
