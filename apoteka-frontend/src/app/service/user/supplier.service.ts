import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Supplier } from 'src/app/model/user/supplier';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class SupplierService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/supplier/';

  // HTTP zahtevi.

  public findAll(): Observable<Supplier[]> {
    this._data = this.http.get<Supplier[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Supplier> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Supplier>(this.URL + 'search', { params });
    return this._data;
  }

  public create(supplier: Supplier): Observable<Supplier> {
    this._data = this.http.post<Supplier>(this.URL + 'create', supplier, httpOptions);
    return this._data;
  }

  public update(supplier: Supplier): Observable<Supplier> {
    this._data = this.http.post<Supplier>(this.URL + 'update', supplier, httpOptions);
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
