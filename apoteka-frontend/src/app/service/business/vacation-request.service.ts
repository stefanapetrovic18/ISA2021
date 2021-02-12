import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {VacationRequest} from 'src/app/model/business/vacation-request';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class VacationRequestService {
  URL = 'http://localhost:8080/api/vacation-request/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<VacationRequest[]> {
    return this.http.get<VacationRequest[]>(this.URL);
  }

  public getOne(id: number): Observable<VacationRequest> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<VacationRequest>(this.URL + 'search', {params});
  }

  public create(vacationRequest: VacationRequest): Observable<VacationRequest> {
    return this.http.post<VacationRequest>(this.URL + 'create', vacationRequest, httpOptions);
  }

  public accept(vacationRequest: VacationRequest): Observable<VacationRequest> {
    return this.http.post<VacationRequest>(this.URL + 'accept', vacationRequest, httpOptions);
  }

  public reject(vacationRequest: VacationRequest): Observable<VacationRequest> {
    return this.http.post<VacationRequest>(this.URL + 'reject', vacationRequest, httpOptions);
  }

  public update(vacationRequest: VacationRequest): Observable<VacationRequest> {
    return this.http.post<VacationRequest>(this.URL + 'update', vacationRequest, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
