import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Reservation } from 'src/app/model/business/reservation';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/reservation/';

  // HTTP zahtevi.

  public findAll(): Observable<Reservation[]> {
    this._data = this.http.get<Reservation[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Reservation> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Reservation>(this.URL + 'search', { params });
    return this._data;
  }

  public create(reservation: Reservation): Observable<Reservation> {
    this._data = this.http.post<Reservation>(this.URL + 'create', reservation, httpOptions);
    return this._data;
  }

  public update(reservation: Reservation): Observable<Reservation> {
    this._data = this.http.post<Reservation>(this.URL + 'update', reservation, httpOptions);
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
