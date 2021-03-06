import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Reservation} from 'src/app/model/business/reservation';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  URL = 'http://localhost:8080/api/reservation/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.URL);
  }

  public getOne(id: number): Observable<Reservation> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Reservation>(this.URL + 'search', {params});
  }

  public create(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.URL + 'create', reservation, httpOptions);
  }

  public reserve(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.URL + 'reserve', reservation, httpOptions);
  }

  public cancel(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.URL + 'cancel', reservation, httpOptions);
  }

  public update(reservation: Reservation): Observable<Reservation> {
    return this.http.post<Reservation>(this.URL + 'update', reservation, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
