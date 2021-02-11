import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Prescription} from 'src/app/model/business/prescription';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PrescriptionService {
  URL = 'http://localhost:8080/api/prescription/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Prescription[]> {
    return this.http.get<Prescription[]>(this.URL);
  }

  public getOne(id: number): Observable<Prescription> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Prescription>(this.URL + 'search', {params});
  }

  public create(prescription: Prescription): Observable<Prescription> {
    return this.http.post<Prescription>(this.URL + 'create', prescription, httpOptions);
  }

  public update(prescription: Prescription): Observable<Prescription> {
    return this.http.post<Prescription>(this.URL + 'update', prescription, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
