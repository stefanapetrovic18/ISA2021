import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderItem } from 'src/app/model/business/order-item';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class OrderItemService {
  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/order-item/';

  // HTTP zahtevi.

  public findAll(): Observable<OrderItem[]> {
    return this.http.get<OrderItem[]>(this.URL);
  }

  public getOne(id: number): Observable<OrderItem> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<OrderItem>(this.URL + 'search', { params });
  }

  public create(orderItem: OrderItem): Observable<OrderItem> {
    return this.http.post<OrderItem>(this.URL + 'create', orderItem, httpOptions);
  }

  public update(orderItem: OrderItem): Observable<OrderItem> {
    return this.http.post<OrderItem>(this.URL + 'update', orderItem, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
