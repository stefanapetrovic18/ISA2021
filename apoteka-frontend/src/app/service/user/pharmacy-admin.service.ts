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

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/pharmacy-admin/';

  public findAll(): Observable<PharmacyAdmin[]> {
    return this.http.get<PharmacyAdmin[]>(this.URL);
  }

  public getOne(id: number): Observable<PharmacyAdmin> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<PharmacyAdmin>(this.URL + 'search', { params });
  }

  public create(pharmacyadmin: PharmacyAdmin): Observable<PharmacyAdmin> {
    return this.http.post<PharmacyAdmin>(this.URL + 'create', pharmacyadmin, httpOptions);
  }

  public update(pharmacyadmin: PharmacyAdmin): Observable<PharmacyAdmin> {
    return this.http.post<PharmacyAdmin>(this.URL + 'update', pharmacyadmin, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
