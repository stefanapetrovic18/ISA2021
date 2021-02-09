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

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/supplier/';

  // HTTP zahtevi.

  public findAll(): Observable<Supplier[]> {
    return this.http.get<Supplier[]>(this.URL);
  }

  public getOne(id: number): Observable<Supplier> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Supplier>(this.URL + 'search', { params });
  }

  public create(supplier: Supplier): Observable<Supplier> {
    return this.http.post<Supplier>(this.URL + 'create', supplier, httpOptions);
  }

  public update(supplier: Supplier): Observable<Supplier> {
    return this.http.post<Supplier>(this.URL + 'update', supplier, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
