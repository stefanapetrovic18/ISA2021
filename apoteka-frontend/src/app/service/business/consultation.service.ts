import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Consultation} from 'src/app/model/business/consultation';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ConsultationService {

  URL = 'http://localhost:8080/api/consultation/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Consultation[]> {
    return this.http.get<Consultation[]>(this.URL);
  }

  public getOne(id: number): Observable<Consultation> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Consultation>(this.URL + 'search', {params});
  }

  public create(consultation: Consultation): Observable<Consultation> {
    return this.http.post<Consultation>(this.URL + 'create', consultation, httpOptions);
  }

  public update(consultation: Consultation): Observable<Consultation> {
    return this.http.post<Consultation>(this.URL + 'update', consultation, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
