import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import { ComplaintResponse } from 'src/app/dto/ComplaintResponse';
import {Complaint} from 'src/app/model/business/complaint';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ComplaintService {
  URL = 'http://localhost:8080/api/complaint/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Complaint[]> {
    return this.http.get<Complaint[]>(this.URL);
  }

  public getOne(id: number): Observable<Complaint> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Complaint>(this.URL + 'search', {params});
  }

  public filterByPatientID(id: number): Observable<Complaint[]> {
    const params = new HttpParams().set('patientID', id.toString());
    return this.http.get<Complaint[]>(this.URL + 'filter', {params});
  }

  public filterByResolved(resolved: boolean): Observable<Complaint[]> {
    const params = new HttpParams().set('resolved', resolved.toString());
    return this.http.get<Complaint[]>(this.URL + 'filter', {params});
  }

  public filterByResolvedAndPatientID(patientID: number, resolved: boolean): Observable<Complaint[]> {
    const params = new HttpParams().set('patientID', patientID.toString()).append('resolved', resolved.toString());
    return this.http.get<Complaint[]>(this.URL + 'filter', {params});
  }

  public create(complaint: Complaint): Observable<Complaint> {
    return this.http.post<Complaint>(this.URL + 'create', complaint, httpOptions);
  }

  public submitPharmacy(complaint: Complaint, pharmacyID: number): Observable<Complaint> {
    return this.http.post<Complaint>(this.URL + 'submit/pharmacy?pharmacyID=' + pharmacyID, complaint, httpOptions);
  }

  public submitPharmacist(complaint: Complaint, pharmacistID: number): Observable<Complaint> {
    return this.http.post<Complaint>(this.URL + 'submit/pharmacist?pharmacistID=' + pharmacistID, complaint, httpOptions);
  }

  public submitDermatologist(complaint: Complaint, dermatologistID: number): Observable<Complaint> {
    return this.http.post<Complaint>(this.URL + 'submit/dermatologist?dermatologistID=' + dermatologistID, complaint, httpOptions);
  }
  public answer(response: ComplaintResponse): Observable<Complaint> {
    return this.http.post<any>(this.URL + 'answer', response, httpOptions);
  }

  public update(complaint: Complaint): Observable<Complaint> {
    return this.http.post<Complaint>(this.URL + 'update', complaint, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
