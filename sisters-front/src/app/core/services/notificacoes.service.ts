import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Notificacao } from './../../notificacoes/notificacao.model';

@Injectable({ providedIn: 'root' })
export class NotificacoesService {

  private apiUrl = 'http://localhost:8080/notificacoes';

  private notificacoes = new BehaviorSubject<Notificacao[]>([]);
  notificacoes$ = this.notificacoes.asObservable();

  constructor(private http: HttpClient) {}

  carregarNotificacoes(usuariaId: number) {
    return this.http.get<Notificacao[]>(`${this.apiUrl}?usuariaId=${usuariaId}`)
      .subscribe(lista => this.notificacoes.next(lista));
  }

  marcarComoLida(id: number) {
    return this.http.put(`${this.apiUrl}/${id}/marcar-lida`, {})
      .subscribe(() => {
        const listaAtual = this.notificacoes.getValue().map(n =>
          n.id === id ? { ...n, lida: true } : n
        );
        this.notificacoes.next(listaAtual);
      });
  }

  numeroNaoLidas() {
    return this.notificacoes.getValue().filter(n => !n.lida).length;
  }

  listarNotificacoes(usuariaId: number): Observable<Notificacao[]> {
    return this.http.get<Notificacao[]>(`${this.apiUrl}?usuariaId=${usuariaId}`);
  }

  setLocalNotificacoes(lista: Notificacao[]) {
    this.notificacoes.next(lista);
  }
  marcarNotificacaoComoLidaLocal(id: number) {
  const listaAtualizada = this.notificacoes.getValue().map(n => 
    n.id === id ? { ...n, lida: true } : n
  );

  this.notificacoes.next(listaAtualizada);
  }

  adicionarNotificacaoListaInicial(lista: Notificacao[]) {
    this.notificacoes.next(lista);
  }

  adicionarNotificacao(notificacao: Notificacao) {
    const lista = this.notificacoes.getValue();
    this.notificacoes.next([...lista, notificacao]);
  }
}