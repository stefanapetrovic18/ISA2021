import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pharmacist } from 'src/app/model/user/pharmacist';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PharmacistService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/pharmacist/';

  // HTTP zahtevi.

  public findAll(): Observable<Pharmacist[]> {
    this._data = this.http.get<Pharmacist[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Pharmacist> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Pharmacist>(this.URL + 'search', { params });
    return this._data;
  }

  public create(pharmacist: Pharmacist): Observable<Pharmacist> {
    this._data = this.http.post<Pharmacist>(this.URL + 'create', pharmacist, httpOptions);
    return this._data;
  }

  public update(pharmacist: Pharmacist): Observable<Pharmacist> {
    this._data = this.http.post<Pharmacist>(this.URL + 'update', pharmacist, httpOptions);
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
