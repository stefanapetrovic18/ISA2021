import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Order} from 'src/app/model/business/order';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  URL = 'http://localhost:8080/api/order/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Order[]> {
    return this.http.get<Order[]>(this.URL);
  }

  public getOne(id: number): Observable<Order> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Order>(this.URL + 'search', {params});
  }

  public create(order: Order): Observable<Order> {
    return this.http.post<Order>(this.URL + 'create', order, httpOptions);
  }

  public update(order: Order): Observable<Order> {
    return this.http.post<Order>(this.URL + 'update', order, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
