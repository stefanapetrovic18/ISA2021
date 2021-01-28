import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { WorkingHours } from 'src/app/model/business/working-hours';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class WorkingHoursService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/working-hours/';

  // HTTP zahtevi.

  public findAll(): Observable<WorkingHours[]> {
    this._data = this.http.get<WorkingHours[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<WorkingHours> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<WorkingHours>(this.URL + 'search', { params });
    return this._data;
  }

  public create(workinghours: WorkingHours): Observable<WorkingHours> {
    this._data = this.http.post<WorkingHours>(this.URL + 'create', workinghours, httpOptions);
    return this._data;
  }

  public update(workinghours: WorkingHours): Observable<WorkingHours> {
    this._data = this.http.post<WorkingHours>(this.URL + 'update', workinghours, httpOptions);
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
