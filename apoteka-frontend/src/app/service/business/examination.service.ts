import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Examination} from 'src/app/model/business/examination';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ExaminationService {

  URL = 'http://localhost:8080/api/examination/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Examination[]> {
    return this.http.get<Examination[]>(this.URL);
  }

  public findAllFree(): Observable<Examination[]> {
    return this.http.get<Examination[]>(this.URL + 'free');
  }

  public findAllReserved(): Observable<Examination[]> {
    return this.http.get<Examination[]>(this.URL + 'reserved');
  }

  public getOne(id: number): Observable<Examination> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Examination>(this.URL + 'search', {params});
  }

  public findAllByPharmacy(id: number): Observable<Examination[]> {
    const params = new HttpParams().set('pharmacyID', id.toString());
    return this.http.get<Examination[]>(this.URL + 'filter', {params});
  }

  public findAllByPatient(id: number): Observable<Examination[]> {
    const params = new HttpParams().set('patientID', id.toString());
    return this.http.get<Examination[]>(this.URL + 'filter', {params});
  }

  public findAllByDermatologist(id: number): Observable<Examination[]> {
    const params = new HttpParams().set('dermatologistID', id.toString());
    return this.http.get<Examination[]>(this.URL + 'filter', {params});
  }

  public create(examination: Examination): Observable<Examination> {
    return this.http.post<Examination>(this.URL + 'create', examination, httpOptions);
  }

  public quickReserve(examination: Examination): Observable<Examination> {
    return this.http.post<Examination>(this.URL + 'quick-reserve', examination, httpOptions);
  }

  public cancel(examination: Examination): Observable<Examination> {
    return this.http.post<Examination>(this.URL + 'cancel', examination, httpOptions);
  }

  public update(examination: Examination): Observable<Examination> {
    return this.http.post<Examination>(this.URL + 'update', examination, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
