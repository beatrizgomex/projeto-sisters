import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/usuarias';

  constructor(private http: HttpClient) {}

  buscarPorId(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  buscarPorNome(nome: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}?nome=${nome}`);
  }

  solicitarAcolhimento(idPrograma: number, afilhadaId: number) {
  return this.http.post(
    `http://localhost:8080/programas/${idPrograma}/solicitacoes?afilhadaId=${afilhadaId}`,
    {}
  );
  }
}