import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Mensagem } from '../models/mensagem.model';
import { Usuaria } from '../models/usuaria.model';
import { MensagemRequest } from '../models/request/mensagemRequest.model';

@Injectable({
  providedIn: 'root'
})
export class MensagemService {

  private apiUrl = 'http://localhost:8080/api/mensagens';

  constructor(private http: HttpClient) {}

  enviarMensagem(request: MensagemRequest): Observable<Mensagem> {
    return this.http.post<Mensagem>(this.apiUrl, request);
  }

  buscarHistorico(id1: number, id2: number): Observable<Mensagem[]> {
    return this.http.get<Mensagem[]>(`${this.apiUrl}/historico/${id1}/${id2}`);
  }

  marcarComoLida(idMensagem: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/${idMensagem}/lida`, {});
  }

  buscarUltimaMensagem(idUsuariaLogada: number, idOutra: number): Observable<Mensagem | null> {
    return this.http.get<Mensagem | null>(`${this.apiUrl}/ultima?u1=${idUsuariaLogada}&u2=${idOutra}`);
  }

  verificarAmizade(id1: number, id2: number): Observable<boolean> {
    return this.http.get<boolean>(`http://localhost:8080/api/v1/amizades/status?u1=${id1}&u2=${id2}`)
  }

  buscarAmigas(idUsuaria: number): Observable<Usuaria[]> {
  return this.http.get<Usuaria[]>(`http://localhost:8080/api/v1/amizades/todas-amigas/${idUsuaria}`);
}
}
