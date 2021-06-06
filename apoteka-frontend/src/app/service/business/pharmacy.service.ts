import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import { BusinessReport } from 'src/app/dto/business-report';
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
  public findSubs(): Observable<Pharmacy[]> {
    return this.http.get<Pharmacy[]>(this.URL + 'subs');
  }
  public checkSubStatus(pharmacyID: number): Observable<any> {
    const params = new HttpParams().set('pharmacyID', pharmacyID.toString());
    return this.http.get<any>(this.URL + 'check-sub-status', {params});
  }
  public getRating(pharmacyID: number): Observable<any> {
    const params = new HttpParams().set('pharmacyID', pharmacyID.toString());
    return this.http.get<any>(this.URL + 'get-rating', {params});
  }

  public subscribe(id: number): Observable<Pharmacy> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Pharmacy>(this.URL + 'subscribe', {params});
  }

  public unsubscribe(id: number): Observable<Pharmacy> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Pharmacy>(this.URL + 'unsubscribe', {params});
  }

  public getOne(id: number): Observable<Pharmacy> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Pharmacy>(this.URL + 'search', {params});
  }

  public getBusinessReport(profitFrom: Date, profitUntil: Date, year: number): Observable<BusinessReport> {
    const params = new HttpParams()
      .set('profitFrom', profitFrom.toISOString().substr(0, 10))
      .append('profitUntil', profitUntil.toISOString().substr(0, 10))
      .append('year', year.toString());
    return this.http.get<BusinessReport>(this.URL + 'business-report', {params});
  }

  public findAllContainingMedicine(medicineID: number): Observable<Pharmacy[]> {
    const params = new HttpParams().set('medicineID', medicineID.toString());
    return this.http.get<Pharmacy[]>(this.URL + 'medicine', {params});
  }

  public findAllByPharmacistFreeAt(localDateTime: Date): Observable<Pharmacy[]> {
    const params = new HttpParams().set('localDateTime', new Date(localDateTime).toISOString());
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
  // https://nominatim.openstreetmap.org/search?format=json&q=address
  public getCoordinatesFromAddress(address: string): Observable<any> {
    const params = new HttpParams()
          .set('format', 'json')
          .append('q', address);
          // .append('viewbox', '-25.0000%2C70.0000%2C50.0000%2C40.0000')
          // .append('bounded', '1');
    return this.http.get<any>('https://nominatim.openstreetmap.org/search', {params});
  }

}
