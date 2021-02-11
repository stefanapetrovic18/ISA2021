import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Item} from 'src/app/model/business/item';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  URL = 'http://localhost:8080/api/item/';

  constructor(private http: HttpClient) {
  }

  // HTTP zahtevi.

  public findAll(): Observable<Item[]> {
    return this.http.get<Item[]>(this.URL);
  }

  public getOne(id: number): Observable<Item> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.get<Item>(this.URL + 'search', {params});
  }

  public create(item: Item): Observable<Item> {
    return this.http.post<Item>(this.URL + 'create', item, httpOptions);
  }

  public update(item: Item): Observable<Item> {
    return this.http.post<Item>(this.URL + 'update', item, httpOptions);
  }

  public delete(id: number): Observable<boolean> {
    const params = new HttpParams().set('id', id.toString());
    return this.http.delete<boolean>(this.URL + 'delete', {params});
  }

}
