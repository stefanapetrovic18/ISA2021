import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pricelist } from 'src/app/model/business/pricelist';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PricelistService {

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/pricelist/';

  public findAll(): Observable<Pricelist[]> {
    return this.http.get<Pricelist[]>(this.URL);
  }

  public getOne(id: number): Observable<Pricelist> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<Pricelist>(this.URL + 'search', { params });
  }

  public create(pricelist: Pricelist): Observable<Pricelist> {
    return this.http.post<Pricelist>(this.URL + 'create', pricelist, httpOptions);
  }

  public update(pricelist: Pricelist): Observable<Pricelist> {
    return this.http.post<Pricelist>(this.URL + 'update', pricelist, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
