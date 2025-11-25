import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private apiUrl = 'http://localhost:8080/api/usuarias';

  constructor(private http: HttpClient) {}

  buscar(term: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}?nome=${term}`);
  }
}