import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Dermatologist } from 'src/app/model/user/dermatologist';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DermatologistService {
  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/dermatologist/';

  // HTTP zahtevi.

  public findAll(): Observable<Dermatologist[]> {
    return this.http.get<Dermatologist[]>(this.URL);
  }

  public getOne(id: number): Observable<Dermatologist> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Dermatologist>(this.URL + 'search', { params });
  }

  public create(dermatologist: Dermatologist): Observable<Dermatologist> {
    return this.http.post<Dermatologist>(this.URL + 'create', dermatologist, httpOptions);
  }

  public update(dermatologist: Dermatologist): Observable<Dermatologist> {
    return this.http.post<Dermatologist>(this.URL + 'update', dermatologist, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
