import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Examination } from 'src/app/model/business/examination';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ExaminationService {

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/examination/';

  public findAll(): Observable<Examination[]> {
    return this.http.get<Examination[]>(this.URL);
  }

  public getOne(id: number): Observable<Examination> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.get<Examination>(this.URL + 'search', { params });
  }

  public create(examination: Examination): Observable<Examination> {
    return this.http.post<Examination>(this.URL + 'create', examination, httpOptions);
  }

  public update(examination: Examination): Observable<Examination> {
    return this.http.post<Examination>(this.URL + 'update', examination, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    let params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
