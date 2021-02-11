import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Pricelist} from 'src/app/model/business/pricelist';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PricelistService {

  URL = 'http://localhost:8080/api/pricelist/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Pricelist[]> {
    return this.http.get<Pricelist[]>(this.URL);
  }

  public getOne(id: number): Observable<Pricelist> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Pricelist>(this.URL + 'search', {params});
  }

  public create(pricelist: Pricelist): Observable<Pricelist> {
    return this.http.post<Pricelist>(this.URL + 'create', pricelist, httpOptions);
  }

  public update(pricelist: Pricelist): Observable<Pricelist> {
    return this.http.post<Pricelist>(this.URL + 'update', pricelist, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
