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

  public getOne(id: number): Observable<Pharmacy> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Pharmacy>(this.URL + 'search', {params});
  }

  public getBusinessReport(profitFrom: Date, profitUntil: Date, year: number): Observable<BusinessReport> {
    const params = new HttpParams()
      .set('profitFrom', new Date(profitFrom).toISOString())
      .append('profitUntil', new Date(profitUntil).toISOString())
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

}
