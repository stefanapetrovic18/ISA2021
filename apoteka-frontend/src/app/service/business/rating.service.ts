import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Rating} from 'src/app/model/business/rating';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  URL = 'http://localhost:8080/api/rating/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Rating[]> {
    return this.http.get<Rating[]>(this.URL);
  }

  public getOne(id: number): Observable<Rating> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Rating>(this.URL + 'search', {params});
  }

  public filterByID(id: number): Observable<Rating[]> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public filterByPatientID(id: number): Observable<Rating[]> {
    const params = new HttpParams().set('patientID', id.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public filterByPharmacistID(id: number): Observable<Rating[]> {
    const params = new HttpParams().set('pharmacistID', id.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public filterByDermatologistID(id: number): Observable<Rating[]> {
    const params = new HttpParams().set('dermatologistID', id.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public filterByPharmacyID(id: number): Observable<Rating[]> {
    const params = new HttpParams().set('pharmacyID', id.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public filterByMedicineID(id: number): Observable<Rating[]> {
    const params = new HttpParams().set('medicineID', id.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public filterByPatientIDAndPharmacyID(patientID: number, pharmacyID: number): Observable<Rating[]> {
    const params = new HttpParams().set('patientID', patientID.toString()).append('pharmacyID', pharmacyID.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public filterByPatientIDAndPharmacistID(patientID: number, pharmacistID: number): Observable<Rating[]> {
    const params = new HttpParams().set('patientID', patientID.toString()).append('pharmacistID', pharmacistID.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public filterByPatientIDAndDermatologistID(patientID: number, dermatologistID: number): Observable<Rating[]> {
    const params = new HttpParams().set('patientID', patientID.toString()).append('dermatologistID', dermatologistID.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public filterByPatientIDAndMedicineID(patientID: number, medicineID: number): Observable<Rating[]> {
    const params = new HttpParams().set('patientID', patientID.toString()).append('medicineID', medicineID.toString());
    return this.http.get<Rating[]>(this.URL + 'filter', {params});
  }

  public create(rating: Rating): Observable<Rating> {
    return this.http.post<Rating>(this.URL + 'create', rating, httpOptions);
  }

  public ratePharmacy(rating: Rating, pharmacyID: number): Observable<Rating> {
    return this.http.post<Rating>(this.URL + 'rate/pharmacy?pharmacyID=' + pharmacyID, rating, httpOptions);
  }

  public ratePharmacist(rating: Rating, pharmacistID: number): Observable<Rating> {
    return this.http.post<Rating>(this.URL + 'rate/pharmacist?pharmacistID=' + pharmacistID, rating, httpOptions);
  }

  public rateMedicine(rating: Rating, medicineID: number): Observable<Rating> {
    return this.http.post<Rating>(this.URL + 'rate/medicine?medicineID=' + medicineID, rating, httpOptions);
  }

  public rateDermatologist(rating: Rating, dermatologistID: number): Observable<Rating> {
    return this.http.post<Rating>(this.URL + 'rate/dermatologist?dermatologistID=' + dermatologistID, rating, httpOptions);
  }

  public update(rating: Rating): Observable<Rating> {
    return this.http.post<Rating>(this.URL + 'update', rating, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
