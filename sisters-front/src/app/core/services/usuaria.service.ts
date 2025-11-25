import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { UsuariaResponse } from '../models/response/usuariaResponse.model';

@Injectable({
  providedIn: 'root',
})
export class UsuariaService {

  private api = 'http://localhost:8080/api/usuarias';

  constructor(private http: HttpClient) {}

  cadastrar(data: any): Observable<UsuariaResponse> {
    return this.http.post<UsuariaResponse>(`${this.api}`, data);
  }

  login(credentials: { email: string, senha: string }): Observable<UsuariaResponse> {
    return this.http.post<UsuariaResponse>(`${this.api}/login`, credentials)
      .pipe(
        tap(usuaria => {
          localStorage.setItem('usuariaLogada', JSON.stringify(usuaria));
        })
      );
  }

  logout() {
    localStorage.removeItem('usuariaLogada');
  }

  buscarNome(nome: string): Observable<UsuariaResponse[]> {
    return this.http.get<UsuariaResponse[]>(`${this.api}?nome=${nome}`);
  }

  buscarPorId(id: number): Observable<UsuariaResponse> {
    return this.http.get<UsuariaResponse>(`${this.api}/${id}`);
  }

  buscarTodas(): Observable<UsuariaResponse[]> {
    return this.http.get<UsuariaResponse[]>(`${this.api}/todas`);
  }

  atualizarPerfil(id: number, data: any): Observable<void> {
    return this.http.put<void>(`${this.api}/${id}`, data);
  }

  excluir(id: number): Observable<void> {
    return this.http.delete<void>(`${this.api}/${id}`);
  }
}
