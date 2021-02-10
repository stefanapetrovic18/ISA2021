import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { InventoryItem } from 'src/app/model/business/inventory-item';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class InventoryItemService {
  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/inventory-item/';

  // HTTP zahtevi.

  public findAll(): Observable<InventoryItem[]> {
    return this.http.get<InventoryItem[]>(this.URL);
  }

  public getOne(id: number): Observable<InventoryItem> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<InventoryItem>(this.URL + 'search', { params });
  }

  public create(inventoryItem: InventoryItem): Observable<InventoryItem> {
    return this.http.post<InventoryItem>(this.URL + 'create', inventoryItem, httpOptions);
  }

  public update(inventoryItem: InventoryItem): Observable<InventoryItem> {
    return this.http.post<InventoryItem>(this.URL + 'update', inventoryItem, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', { params });
  }

}
