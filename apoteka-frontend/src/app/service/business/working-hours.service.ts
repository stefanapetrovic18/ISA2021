import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {WorkingHours} from 'src/app/model/business/working-hours';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class WorkingHoursService {

  URL = 'http://localhost:8080/api/working-hours/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<WorkingHours[]> {
    return this.http.get<WorkingHours[]>(this.URL);
  }

  public getOne(id: number): Observable<WorkingHours> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<WorkingHours>(this.URL + 'search', {params});
  }

  public create(workinghours: WorkingHours): Observable<WorkingHours> {
    return this.http.post<WorkingHours>(this.URL + 'create', workinghours, httpOptions);
  }

  public update(workinghours: WorkingHours): Observable<WorkingHours> {
    return this.http.post<WorkingHours>(this.URL + 'update', workinghours, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
