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

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/pharmacist/';

  // HTTP zahtevi.

  public findAll(): Observable<Pharmacist[]> {
    return this.http.get<Pharmacist[]>(this.URL);
  }

  public getOne(id: number): Observable<Pharmacist> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Pharmacist>(this.URL + 'search', { params });
  }

  public create(pharmacist: Pharmacist): Observable<Pharmacist> {
    return this.http.post<Pharmacist>(this.URL + 'create', pharmacist, httpOptions);
  }

  public update(pharmacist: Pharmacist): Observable<Pharmacist> {
    return this.http.post<Pharmacist>(this.URL + 'update', pharmacist, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
