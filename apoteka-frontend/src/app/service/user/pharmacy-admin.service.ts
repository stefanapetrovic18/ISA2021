import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PharmacyAdmin } from 'src/app/model/user/pharmacy-admin';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PharmacyAdminService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/pharmacy-admin/';

  // HTTP zahtevi.

  public findAll(): Observable<PharmacyAdmin[]> {
    this._data = this.http.get<PharmacyAdmin[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<PharmacyAdmin> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<PharmacyAdmin>(this.URL + 'search', { params });
    return this._data;
  }

  public create(pharmacyAdmin: PharmacyAdmin): Observable<PharmacyAdmin> {
    this._data = this.http.post<PharmacyAdmin>(this.URL + 'create', pharmacyAdmin, httpOptions);
    return this._data;
  }

  public update(pharmacyAdmin: PharmacyAdmin): Observable<PharmacyAdmin> {
    this._data = this.http.post<PharmacyAdmin>(this.URL + 'update', pharmacyAdmin, httpOptions);
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
