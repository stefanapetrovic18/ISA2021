import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dermatologist } from 'src/app/model/user/dermatologist';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DermatologistService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/dermatologist/';

  // HTTP zahtevi.

  public findAll(): Observable<Dermatologist[]> {
    this._data = this.http.get<Dermatologist[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Dermatologist> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Dermatologist>(this.URL + 'search', { params });
    return this._data;
  }

  public create(dermatologist: Dermatologist): Observable<Dermatologist> {
    this._data = this.http.post<Dermatologist>(this.URL + 'create', dermatologist, httpOptions);
    return this._data;
  }

  public update(dermatologist: Dermatologist): Observable<Dermatologist> {
    this._data = this.http.post<Dermatologist>(this.URL + 'update', dermatologist, httpOptions);
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
