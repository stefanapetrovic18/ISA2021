import { HttpHeaders, HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Item } from 'src/app/model/business/item';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  private _data = new Observable<any>();

  constructor(private http: HttpClient) { }

  URL = 'http://localhost:8080/api/item/';

  // HTTP zahtevi.

  public findAll(): Observable<Item[]> {
    this._data = this.http.get<Item[]>(this.URL);
    return this._data;
  }

  public getOne(id: number): Observable<Item> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.get<Item>(this.URL + 'search', { params });
    return this._data;
  }

  public create(item: Item): Observable<Item> {
    this._data = this.http.post<Item>(this.URL + 'create', item, httpOptions);
    return this._data;
  }

  public update(item: Item): Observable<Item> {
    this._data = this.http.post<Item>(this.URL + 'update', item, httpOptions);
    return this._data;
  }

  public delete(id: number): Observable<boolean> {
    let params = new HttpParams().set('id', id.toString());
    this._data = this.http.delete<boolean>(this.URL + 'delete', { params });
    return this._data;
  }

  // Get/Set metode.

  public get data() {
    return this._data;
  }

  public setData(data: any) {
    this._data = data;
  }

}
