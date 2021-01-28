import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Consultation } from 'src/app/model/business/consultation';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ConsultationService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/consultation/';

  // HTTP zahtevi.

  public findAll(): Observable<Consultation[]> {
    this._data = this.http.get<Consultation[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Consultation> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Consultation>(this.URL + 'search', { params });
    return this._data;
  }

  public create(consultation: Consultation): Observable<Consultation> {
    this._data = this.http.post<Consultation>(this.URL + 'create', consultation, httpOptions);
    return this._data;
  }

  public update(consultation: Consultation): Observable<Consultation> {
    this._data = this.http.post<Consultation>(this.URL + 'update', consultation, httpOptions);
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
