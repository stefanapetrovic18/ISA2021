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

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/system-admin/';

  // HTTP zahtevi.

  public findAll(): Observable<SystemAdmin[]> {
    return this.http.get<SystemAdmin[]>(this.URL);
  }

  public getOne(id: number): Observable<SystemAdmin> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<SystemAdmin>(this.URL + 'search', { params });
  }

  public create(systemAdmin: SystemAdmin): Observable<SystemAdmin> {
    return this.http.post<SystemAdmin>(this.URL + 'create', systemAdmin, httpOptions);
  }

  public update(systemAdmin: SystemAdmin): Observable<SystemAdmin> {
    return this.http.post<SystemAdmin>(this.URL + 'update', systemAdmin, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
