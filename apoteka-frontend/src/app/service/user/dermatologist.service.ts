import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Dermatologist} from 'src/app/model/user/dermatologist';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class DermatologistService {
  URL = 'http://localhost:8080/api/dermatologist/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Dermatologist[]> {
    return this.http.get<Dermatologist[]>(this.URL);
  }

  public getOne(id: number): Observable<Dermatologist> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Dermatologist>(this.URL + 'search', {params});
  }

  public findAllNotEmployedInPharmacy(pharmacyID: number): Observable<Dermatologist[]> {
    const params = new HttpParams().set('pharmacyID', pharmacyID.toString());
    return this.http.get<Dermatologist[]>(this.URL + 'not-employed-here', {params});
  }

  public findAllByPharmaciesContaining(pharmacyID: number): Observable<Dermatologist[]> {
    const params = new HttpParams().set('pharmacyID', pharmacyID.toString());
    return this.http.get<Dermatologist[]>(this.URL + 'pharmacy', { params });
  }

  public create(dermatologist: Dermatologist): Observable<Dermatologist> {
    return this.http.post<Dermatologist>(this.URL + 'create', dermatologist, httpOptions);
  }

  public fire(dermatologist: Dermatologist): Observable<Dermatologist> {
    return this.http.post<Dermatologist>(this.URL + 'fire', dermatologist, httpOptions);
  }

  public update(dermatologist: Dermatologist): Observable<Dermatologist> {
    return this.http.post<Dermatologist>(this.URL + 'update', dermatologist, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
