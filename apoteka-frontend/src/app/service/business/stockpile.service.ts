import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Stockpile} from 'src/app/model/business/stockpile';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class StockpileService {
  URL = 'http://localhost:8080/api/stockpile/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Stockpile[]> {
    return this.http.get<Stockpile[]>(this.URL);
  }

  public getOne(id: number): Observable<Stockpile> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Stockpile>(this.URL + 'search', {params});
  }

  public filterByID(id: number): Observable<Stockpile[]> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Stockpile[]>(this.URL + 'filter', {params});
  }

  public filterByQuantity(quantity: number): Observable<Stockpile[]> {
    const params = new HttpParams().set('quantity', quantity.toString());
    return this.http.get<Stockpile[]>(this.URL + 'filter', {params});
  }

  public filterByQuantityFrom(quantityFrom: number): Observable<Stockpile[]> {
    const params = new HttpParams().set('quantityFrom', quantityFrom.toString());
    return this.http.get<Stockpile[]>(this.URL + 'filter', {params});
  }

  public filterByQuantityTo(quantityTo: number): Observable<Stockpile[]> {
    const params = new HttpParams().set('quantityTo', quantityTo.toString());
    return this.http.get<Stockpile[]>(this.URL + 'filter', {params});
  }

  public filterByPharmacyID(id: number): Observable<Stockpile[]> {
    const params = new HttpParams().set('pharmacyID', id.toString());
    return this.http.get<Stockpile[]>(this.URL + 'filter', {params});
  }

  public filterByMedicineID(id: number): Observable<Stockpile[]> {
    const params = new HttpParams().set('medicineID', id.toString());
    return this.http.get<Stockpile[]>(this.URL + 'filter', {params});
  }

  public filterByQuantityFromAndQuantityTo(quantityFrom: number, quantityTo: number): Observable<Stockpile[]> {
    const params = new HttpParams().set('quantityFrom', quantityFrom.toString()).append('quantityTo', quantityTo.toString());
    return this.http.get<Stockpile[]>(this.URL + 'filter', {params});
  }

  public filterByPharmacyIDQuantityFromAndQuantityTo(pharmacyID: number, quantityFrom: number, quantityTo: number): Observable<Stockpile[]> {
    const params = new HttpParams().set('quantityFrom', quantityFrom.toString()).append('quantityTo', quantityTo.toString())
    .append('pharmacyID', pharmacyID.toString());
    return this.http.get<Stockpile[]>(this.URL + 'filter', {params});
  }

  public filterByPharmacyIDAndMedicineID(pharmacyID: number, medicineID: number): Observable<Stockpile[]> {
    const params = new HttpParams().set('pharmacyID', pharmacyID.toString()).append('medicineID', medicineID.toString());
    return this.http.get<Stockpile[]>(this.URL + 'filter', {params});
  }

  public create(stockpile: Stockpile): Observable<Stockpile> {
    return this.http.post<Stockpile>(this.URL + 'create', stockpile, httpOptions);
  }

  public update(stockpile: Stockpile): Observable<Stockpile> {
    return this.http.post<Stockpile>(this.URL + 'update', stockpile, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
