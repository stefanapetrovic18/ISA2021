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

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/patient/';

  // HTTP zahtevi.

  public findAll(): Observable<Patient[]> {
    this._data = this.http.get<Patient[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Patient> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Patient>(this.URL + 'search', { params });
    return this._data;
  }

  public create(patient: Patient): Observable<Patient> {
    this._data = this.http.post<Patient>(this.URL + 'create', patient, httpOptions);
    return this._data;
  }

  public update(patient: Patient): Observable<Patient> {
    this._data = this.http.post<Patient>(this.URL + 'update', patient, httpOptions);
    return this._data;
  }

  public delete(id: number): Observable<boolean> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.delete<boolean>(this.URL + 'delete', { params });
    return this._data;
  }

  // Get/Set metode.

  public get data() {
    return this._data;
  }

  public setData(data: any) {
    this._data = data;
  }

}
