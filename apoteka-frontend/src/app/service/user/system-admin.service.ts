import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SystemAdmin } from 'src/app/model/user/system-admin';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class SystemAdminService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/system-admin/';

  // HTTP zahtevi.

  public findAll(): Observable<SystemAdmin[]> {
    this._data = this.http.get<SystemAdmin[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<SystemAdmin> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<SystemAdmin>(this.URL + 'search', { params });
    return this._data;
  }

  public create(systemAdmin: SystemAdmin): Observable<SystemAdmin> {
    this._data = this.http.post<SystemAdmin>(this.URL + 'create', systemAdmin, httpOptions);
    return this._data;
  }

  public update(systemAdmin: SystemAdmin): Observable<SystemAdmin> {
    this._data = this.http.post<SystemAdmin>(this.URL + 'update', systemAdmin, httpOptions);
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
