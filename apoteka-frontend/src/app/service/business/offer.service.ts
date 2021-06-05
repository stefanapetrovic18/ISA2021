import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Offer} from 'src/app/model/business/offer';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  URL = 'http://localhost:8080/api/offer/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.URL);
  }

  public getOne(id: number): Observable<Offer> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Offer>(this.URL + 'search', {params});
  }

  public findByOrder(id: number): Observable<Offer[]> {
    const params = new HttpParams().set('orderID', id.toString());
    return this.http.get<Offer[]>(this.URL + 'filter', {params});
  }

  public create(offer: Offer): Observable<Offer> {
    return this.http.post<Offer>(this.URL + 'create', offer, httpOptions);
  }

  public update(offer: Offer): Observable<Offer> {
    return this.http.post<Offer>(this.URL + 'update', offer, httpOptions);
  }

  public accept(offer: Offer): Observable<Offer> {
    return this.http.post<Offer>(this.URL + 'accept', offer, httpOptions);
  }

  public reject(offer: Offer): Observable<Offer> {
    return this.http.post<Offer>(this.URL + 'reject', offer, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
