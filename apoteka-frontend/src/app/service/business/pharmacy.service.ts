import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pharmacy } from 'src/app/model/business/pharmacy';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PharmacyService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/pharmacy/';

  // HTTP zahtevi.

  public findAll(): Observable<Pharmacy[]> {
    this._data = this.http.get<Pharmacy[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Pharmacy> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Pharmacy>(this.URL + 'search', { params });
    return this._data;
  }

  public create(pharmacy: Pharmacy): Observable<Pharmacy> {
    this._data = this.http.post<Pharmacy>(this.URL + 'create', pharmacy, httpOptions);
    console.log(this.data);
    return this._data;
  }

  public update(pharmacy: Pharmacy): Observable<Pharmacy> {
    this._data = this.http.post<Pharmacy>(this.URL + 'update', pharmacy, httpOptions);
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
