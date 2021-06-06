import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Pharmacist} from 'src/app/model/user/pharmacist';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PharmacistService {

  URL = 'http://localhost:8080/api/pharmacist/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Pharmacist[]> {
    return this.http.get<Pharmacist[]>(this.URL);
  }

  public findAllUnemployed(): Observable<Pharmacist[]> {
    return this.http.get<Pharmacist[]>(this.URL + 'unemployed');
  }

  public getOne(id: number): Observable<Pharmacist> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Pharmacist>(this.URL + 'search', {params});
  }

  public findAllByPharmacistFreeAt(pharmacyID: number, localDateTime: Date): Observable<Pharmacist[]> {
    let date = new Date(localDateTime);
    let isoDateTime = new Date(date.getTime() - (date.getTimezoneOffset() * 60000)).toISOString().slice(0, -1);
    const params = new HttpParams().set('pharmacyID', pharmacyID.toString()).append('localDateTime', isoDateTime);
    return this.http.get<Pharmacist[]>(this.URL + 'free', {params});
  }

  public create(pharmacist: Pharmacist): Observable<Pharmacist> {
    return this.http.post<Pharmacist>(this.URL + 'create', pharmacist, httpOptions);
  }

  public fire(pharmacist: Pharmacist): Observable<Pharmacist> {
    return this.http.post<Pharmacist>(this.URL + 'fire', pharmacist, httpOptions);
  }

  public update(pharmacist: Pharmacist): Observable<Pharmacist> {
    return this.http.post<Pharmacist>(this.URL + 'update', pharmacist, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
