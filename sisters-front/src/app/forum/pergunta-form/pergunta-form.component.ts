// src/app/forum/pergunta-form/pergunta-form.component.ts

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { PerguntaService } from '../pergunta.service';

// IMPORT DOS COMPONENTES COMPARTILHADOS
import { HeaderComponent } from '../../shared/header/header.component';
import { FooterComponent } from '../../shared/footer/footer.component';

@Component({
  selector: 'app-pergunta-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, HeaderComponent, FooterComponent],
  templateUrl: './pergunta-form.component.html',
  styleUrls: ['./pergunta-form.component.css'] // Reutiliza o CSS de formulário
})
export class PerguntaFormComponent {

  perguntaData = { 
    titulo: '', 
    corpo: '', 
    linkAnexo: '' // FA1: Suporte para anexo (link)
  };
  
  // Mock do ID da usuária logada
  autoraId: number = 1; 

  mensagemSucesso: string = '';
  mensagemErro: string = '';

  constructor(private perguntaService: PerguntaService, private router: Router) {}

  onSubmit(form: any) {
    if (form.invalid) {
      this.mensagemErro = 'Preencha todos os campos obrigatórios.';
      return;
    }
    
    // Limpa mensagens anteriores
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.perguntaService.criarPergunta(this.perguntaData, this.autoraId).subscribe({
      next: (response) => {
        // CSU04: Nova pergunta é registrada no fórum (após aprovação, se houver)
        this.mensagemSucesso = 'Pergunta enviada com sucesso! Aguardando moderação para publicação.'; 

        // Limpa o formulário
        this.perguntaData = { titulo: '', corpo: '', linkAnexo: '' };

        // Espera 2 segundos para a usuária ler, depois redireciona para a lista
        setTimeout(() => {
          this.router.navigate(['/forum']);
        }, 2000);
      },
      error: (err) => {
        console.error('Erro ao criar pergunta:', err);
        this.mensagemErro = 'Erro ao tentar publicar a pergunta. Tente novamente.';
      }
    });
  }
}