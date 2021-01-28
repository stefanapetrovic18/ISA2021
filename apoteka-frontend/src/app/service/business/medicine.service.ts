import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Medicine } from 'src/app/model/business/medicine';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class MedicineService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/medicine/';

  // HTTP zahtevi.

  public findAll(): Observable<Medicine[]> {
    this._data = this.http.get<Medicine[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Medicine> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Medicine>(this.URL + 'search', { params });
    return this._data;
  }

  public create(medicine: Medicine): Observable<Medicine> {
    this._data = this.http.post<Medicine>(this.URL + 'create', medicine, httpOptions);
    return this._data;
  }

  public update(medicine: Medicine): Observable<Medicine> {
    this._data = this.http.post<Medicine>(this.URL + 'update', medicine, httpOptions);
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
