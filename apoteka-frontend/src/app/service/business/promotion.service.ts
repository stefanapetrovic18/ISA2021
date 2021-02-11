import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Promotion} from 'src/app/model/business/promotion';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PromotionService {

  URL = 'http://localhost:8080/api/promotion/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Promotion[]> {
    return this.http.get<Promotion[]>(this.URL);
  }

  public getOne(id: number): Observable<Promotion> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Promotion>(this.URL + 'search', {params});
  }

  public create(promotion: Promotion): Observable<Promotion> {
    return this.http.post<Promotion>(this.URL + 'create', promotion, httpOptions);
  }

  public update(promotion: Promotion): Observable<Promotion> {
    return this.http.post<Promotion>(this.URL + 'update', promotion, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
