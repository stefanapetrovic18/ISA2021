import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Offer } from 'src/app/model/business/offer';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/offer/';

  // HTTP zahtevi.

  public findAll(): Observable<Offer[]> {
    return this.http.get<Offer[]>(this.URL);
  }

  public getOne(id: number): Observable<Offer> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Offer>(this.URL + 'search', { params });
  }

  public create(offer: Offer): Observable<Offer> {
    return this.http.post<Offer>(this.URL + 'create', offer, httpOptions);
  }

  public update(offer: Offer): Observable<Offer> {
    return this.http.post<Offer>(this.URL + 'update', offer, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
