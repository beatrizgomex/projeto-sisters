import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { PerguntaService } from '../pergunta.service';

// IMPORTAR OS COMPONENTES COMPARTILHADOS (Ajuste o caminho se necessário)
import { HeaderComponent } from '../../shared/header/header.component';
import { FooterComponent } from '../../shared/footer/footer.component';

@Component({
  selector: 'app-forum-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './forum-list.component.html',
  styleUrls: ['./forum-list.component.css'] // Reutiliza o CSS de lista
})
export class ForumListComponent implements OnInit {

  perguntas: any[] = [];
  // Variáveis para simular permissões de CRUD (RF04)
  isAdmin: boolean = true;
  idUsuariaLogada: number = 1; // ID mock para testes de permissão

  constructor(private perguntaService: PerguntaService) { }

  ngOnInit(): void {
    this.carregarPerguntas();
  }

  carregarPerguntas() {
    this.perguntaService.listarTodasPerguntas().subscribe({
      next: (data) => {
        this.perguntas = data;
      },
      error: (err) => console.error('Erro ao carregar perguntas do fórum', err)
    });
  }

  excluirPergunta(idPergunta: number) {
    if (confirm('Tem certeza que deseja excluir esta pergunta?')) {
      // Nota: A lógica de permissão de exclusão deve estar principalmente no backend (PerguntaController/Service)
      this.perguntaService.excluirPergunta(idPergunta).subscribe({
        next: () => {
          this.perguntas = this.perguntas.filter(p => p.idPergunta !== idPergunta);
        },
        error: (err) => console.error('Erro ao excluir pergunta', err)
      });
    }
  }

  // Método para verificar se o usuário é a autora (para edição/exclusão)
  isAutora(autoraId: number): boolean {
    return autoraId === this.idUsuariaLogada;
  }
}