import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from 'src/app/model/user/patient';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PatientService {

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/patient/';

  public findAll(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.URL);
  }

  public getOne(id: number): Observable<Patient> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<Patient>(this.URL + 'search', { params });
  }

  public create(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(this.URL + 'create', patient, httpOptions);
  }

  public update(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(this.URL + 'update', patient, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
