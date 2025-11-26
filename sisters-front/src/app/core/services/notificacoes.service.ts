import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Notificacao } from './../../notificacoes/notificacao.model';

@Injectable({ providedIn: 'root' })
export class NotificacoesService {

  private notificacoes = new BehaviorSubject<Notificacao[]>([]);
  notificacoes$ = this.notificacoes.asObservable();

  adicionarNotificacao(notificacao: Notificacao) {
    const lista = this.notificacoes.getValue();
    this.notificacoes.next([...lista, notificacao]);
  }

  marcarTodasComoLidas() {
    const lista = this.notificacoes.getValue()
      .map(n => ({ ...n, lida: true }));
    this.notificacoes.next(lista);
  }

  numeroNaoLidas() {
    return this.notificacoes.getValue().filter(n => !n.lida).length;
  }
}