import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HeaderComponent } from '../shared/header/header.component'; 
import { DatePipe } from '@angular/common';

export interface Notificacao {
  id: number;
  tipo: 'amizade' 
        | 'solicitacaoAcolhimento' 
        | 'respostaAcolhimento' 
        | 'respostaForum';
  mensagem: string;
  data: Date;
  lida: boolean;
  usuariaRelacionadoId?: number;
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

  constructor(private router: Router) {}

  ngOnInit() {
    // Simulação – depois isso vem do backend
    setTimeout(() => {
      this.notificacoes = [
        {
          id: 1,
          tipo: 'amizade',
          mensagem: 'Maria te enviou uma solicitação de amizade',
          data: new Date(),
          lida: false,
          usuariaRelacionadoId: 34
        },
        {
          id: 2,
          tipo: 'solicitacaoAcolhimento',
          mensagem: 'Ana pediu acolhimento',
          data: new Date(),
          lida: false,
          usuariaRelacionadoId: 20
        },
        {
          id: 3,
          tipo: 'respostaForum',
          mensagem: 'Alguém respondeu sua pergunta no fórum',
          data: new Date(),
          lida: false
        }
      ];

      this.carregando = false;
    }, 800);
  }

  abrirNotificacao(n: Notificacao) {
    n.lida = true; // marca como lida

    switch (n.tipo) {
      case 'amizade':
      case 'solicitacaoAcolhimento':
        // abrir perfil da usuária
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
