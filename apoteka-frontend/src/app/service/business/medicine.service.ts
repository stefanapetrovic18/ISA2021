import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Medicine} from 'src/app/model/business/medicine';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class MedicineService {
  URL = 'http://localhost:8080/api/medicine/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Medicine[]> {
    return this.http.get<Medicine[]>(this.URL);
  }

  public findAllByPharmacyID(pharmacyID: number): Observable<Medicine[]> {
    const params = new HttpParams().set('pharmacyID', pharmacyID.toString());
    return this.http.get<Medicine[]>(this.URL + 'search/pharmacy', {params})
  }

  public getOne(id: number): Observable<Medicine> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Medicine>(this.URL + 'search', {params});
  }

  public create(medicine: Medicine): Observable<Medicine> {
    return this.http.post<Medicine>(this.URL + 'create', medicine, httpOptions);
  }

  public update(medicine: Medicine): Observable<Medicine> {
    return this.http.post<Medicine>(this.URL + 'update', medicine, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
