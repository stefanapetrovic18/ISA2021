import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Inventory } from 'src/app/model/business/inventory';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/inventory/';

  // HTTP zahtevi.

  public findAll(): Observable<Inventory[]> {
    return this.http.get<Inventory[]>(this.URL);
  }

  public getOne(id: number): Observable<Inventory> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Inventory>(this.URL + 'search', { params });
  }

  public create(inventory: Inventory): Observable<Inventory> {
    return this.http.post<Inventory>(this.URL + 'create', inventory, httpOptions);
  }

  public update(inventory: Inventory): Observable<Inventory> {
    return this.http.post<Inventory>(this.URL + 'update', inventory, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
