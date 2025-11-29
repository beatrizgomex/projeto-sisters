import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HeaderComponent } from '../shared/header/header.component';
import { DatePipe } from '@angular/common';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { NotificacoesService } from '../core/services/notificacoes.service';
import { finalize } from 'rxjs/operators';
import { Observable } from 'rxjs';

export interface Notificacao {
  id: number;
  tipo: 'amizade'
    | 'solicitacaoAcolhimento'
    | 'respostaAcolhimento'
    | 'respostaForum';
  mensagem: string;
  dataCriacao: string | Date;
  lida: boolean;
  usuariaRelacionadoId?: number;
  referenciaId?: number;
}

@Component({
  selector: 'app-notificacoes',
  standalone: true,
  imports: [HeaderComponent, DatePipe],
  templateUrl: './notificacoes.component.html',
  styleUrls: ['./notificacoes.component.css']
})
export class NotificacoesComponent implements OnInit {

  notificacoes: Notificacao[] = [];
  carregando = true;
  erro: string | null = null;

  private apiUrl = 'http://localhost:8080/notificacoes';

  constructor(
    private router: Router,
    private http: HttpClient,
    private notService: NotificacoesService
  ) {}

  ngOnInit() {
    // Obtém id da usuária autenticada. Ajuste conforme seu fluxo de autenticação.
    const usuariaId = Number(localStorage.getItem('usuariaId')) || 1;

    this.carregando = true;
    this.erro = null;

    this.listarNotificacoes(usuariaId)
      .pipe(finalize(() => this.carregando = false))
      .subscribe({
        next: (lista) => {
          // normaliza data para Date no front (opcional)
          this.notificacoes = lista.map(n => ({ ...n, dataCriacao: new Date(n.dataCriacao) }));
          // atualiza BehaviorSubject do serviço (opcional, para outros componentes reagirem)
          this.notService.adicionarNotificacaoListaInicial(this.notificacoes);
        },
        error: (err: HttpErrorResponse) => {
          console.error('Erro ao buscar notificações', err);
          this.erro = 'Não foi possível carregar notificações';
        }
      });
  }

  // Faz a requisição GET ao backend
  private listarNotificacoes(usuariaId: number): Observable<Notificacao[]> {
    // monta URL com query param (ex: /notificacoes?usuariaId=5)
    const url = `${this.apiUrl}?usuariaId=${usuariaId}`;
    return this.http.get<Notificacao[]>(url);
  }

  abrirNotificacao(n: Notificacao) {
    if (!n.lida) {
      // marca localmente já para UI ficar responsiva
      n.lida = true;

      // chama backend para marcar como lida
      this.http.put(`${this.apiUrl}/${n.id}/marcar-lida`, null)
        .subscribe({
          next: () => {
            // opcional: atualizar o BehaviorSubject
            this.notService.marcarNotificacaoComoLidaLocal(n.id);
          },
          error: (err) => {
            console.error('Erro ao marcar notificação como lida', err);
            // reverte marcação local se quiser:
            n.lida = false;
          }
        });
    }

    // navegação baseada no tipo da notificação
    switch (n.tipo) {
      case 'amizade':
      case 'solicitacaoAcolhimento':
        if (n.usuariaRelacionadoId) {
          this.router.navigate(['/perfil', n.usuariaRelacionadoId]);
        }
        break;

      case 'respostaAcolhimento':
        this.router.navigate(['/acolhimento']);
        break;

      case 'respostaForum':
        this.router.navigate(['/forum/minhas-perguntas']);
        break;
    }
  }
}
