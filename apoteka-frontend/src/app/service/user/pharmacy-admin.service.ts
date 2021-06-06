import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {PharmacyAdmin} from 'src/app/model/user/pharmacy-admin';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PharmacyAdminService {
  URL = 'http://localhost:8080/api/pharmacy-admin/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<PharmacyAdmin[]> {
    return this.http.get<PharmacyAdmin[]>(this.URL);
  }

  public getOne(id: number): Observable<PharmacyAdmin> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<PharmacyAdmin>(this.URL + 'search', {params});
  }

  public findByUsername(username: string): Observable<PharmacyAdmin> {
    const params = new HttpParams().set('username', username);
    return this.http.get<PharmacyAdmin>(this.URL + 'find-by-username', {params});
  }

  public create(pharmacyAdmin: PharmacyAdmin): Observable<PharmacyAdmin> {
    return this.http.post<PharmacyAdmin>(this.URL + 'create', pharmacyAdmin, httpOptions);
  }

  public update(pharmacyAdmin: PharmacyAdmin): Observable<PharmacyAdmin> {
    return this.http.post<PharmacyAdmin>(this.URL + 'update', pharmacyAdmin, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
