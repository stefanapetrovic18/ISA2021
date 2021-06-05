import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Patient} from 'src/app/model/user/patient';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  URL = 'http://localhost:8080/api/patient/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Patient[]> {
    return this.http.get<Patient[]>(this.URL);
  }

  public getOne(id: number): Observable<Patient> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Patient>(this.URL + 'search', {params});
  }

  public findByUsername(username: string): Observable<Patient> {
    const params = new HttpParams().set('username', username);
    return this.http.get<Patient>(this.URL + 'search-by-username', {params});
  }

  public create(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(this.URL + 'create', patient, httpOptions);
  }

  public update(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(this.URL + 'update', patient, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
