import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pricelist } from 'src/app/model/business/pricelist';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PricelistService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/pricelist/';

  // HTTP zahtevi.

  public findAll(): Observable<Pricelist[]> {
    this._data = this.http.get<Pricelist[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Pricelist> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Pricelist>(this.URL + 'search', { params });
    return this._data;
  }

  public create(pricelist: Pricelist): Observable<Pricelist> {
    this._data = this.http.post<Pricelist>(this.URL + 'create', pricelist, httpOptions);
    return this._data;
  }

  public update(pricelist: Pricelist): Observable<Pricelist> {
    this._data = this.http.post<Pricelist>(this.URL + 'update', pricelist, httpOptions);
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
