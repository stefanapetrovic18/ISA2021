import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Pharmacy} from 'src/app/model/business/pharmacy';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class PharmacyService {
  URL = 'http://localhost:8080/api/pharmacy/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Pharmacy[]> {
    return this.http.get<Pharmacy[]>(this.URL);
  }

  public getOne(id: number): Observable<Pharmacy> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Pharmacy>(this.URL + 'search', {params});
  }

  public findAllByPharmacistFreeAt(localDateTime: Date): Observable<Pharmacy[]> {
    const params = new HttpParams().set('localDateTime', localDateTime.toISOString());
    return this.http.get<Pharmacy[]>(this.URL + 'free', {params});
  }

  public create(pharmacy: Pharmacy): Observable<Pharmacy> {
    return this.http.post<Pharmacy>(this.URL + 'create', pharmacy, httpOptions);
  }

  public update(pharmacy: Pharmacy): Observable<Pharmacy> {
    return this.http.post<Pharmacy>(this.URL + 'update', pharmacy, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
