import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Medicine } from 'src/app/model/business/medicine';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class MedicineService {

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/medicine/';

  public findAll(): Observable<Medicine[]> {
    return this.http.get<Medicine[]>(this.URL);
  }

  public getOne(id: number): Observable<Medicine> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<Medicine>(this.URL + 'search', { params });
  }

  public create(medicine: Medicine): Observable<Medicine> {
    return this.http.post<Medicine>(this.URL + 'create', medicine, httpOptions);
  }

  public update(medicine: Medicine): Observable<Medicine> {
    return this.http.post<Medicine>(this.URL + 'update', medicine, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
