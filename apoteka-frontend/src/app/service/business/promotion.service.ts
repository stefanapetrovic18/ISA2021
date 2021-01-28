import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Promotion } from 'src/app/model/business/promotion';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PromotionService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/promotion/';

  // HTTP zahtevi.

  public findAll(): Observable<Promotion[]> {
    this._data = this.http.get<Promotion[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Promotion> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Promotion>(this.URL + 'search', { params });
    return this._data;
  }

  public create(promotion: Promotion): Observable<Promotion> {
    this._data = this.http.post<Promotion>(this.URL + 'create', promotion, httpOptions);
    return this._data;
  }

  public update(promotion: Promotion): Observable<Promotion> {
    this._data = this.http.post<Promotion>(this.URL + 'update', promotion, httpOptions);
    return this._data;
  }

  public delete(id: number): Observable<boolean> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.delete<boolean>(this.URL + 'delete', { params });
    return this._data;
  }

  // Get/Set metode.

  public get data() {
    return this._data;
  }

  public setData(data: any) {
    this._data = data;
  }

}
