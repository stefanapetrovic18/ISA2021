import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prescription } from 'src/app/model/business/prescription';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PrescriptionService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/prescription/';

  // HTTP zahtevi.

  public findAll(): Observable<Prescription[]> {
    this._data = this.http.get<Prescription[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Prescription> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Prescription>(this.URL + 'search', { params });
    return this._data;
  }

  public create(prescription: Prescription): Observable<Prescription> {
    this._data = this.http.post<Prescription>(this.URL + 'create', prescription, httpOptions);
    return this._data;
  }

  public update(prescription: Prescription): Observable<Prescription> {
    this._data = this.http.post<Prescription>(this.URL + 'update', prescription, httpOptions);
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
