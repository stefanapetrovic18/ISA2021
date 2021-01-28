import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Examination } from 'src/app/model/business/examination';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ExaminationService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/examination/';

  // HTTP zahtevi.

  public findAll(): Observable<Examination[]> {
    this._data = this.http.get<Examination[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Examination> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Examination>(this.URL + 'search', { params });
    return this._data;
  }

  public create(examination: Examination): Observable<Examination> {
    this._data = this.http.post<Examination>(this.URL + 'create', examination, httpOptions);
    return this._data;
  }

  public update(examination: Examination): Observable<Examination> {
    this._data = this.http.post<Examination>(this.URL + 'update', examination, httpOptions);
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
