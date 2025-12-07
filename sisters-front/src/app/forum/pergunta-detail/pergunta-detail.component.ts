import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { PerguntaService } from '../pergunta.service';
import { FormsModule } from '@angular/forms'; 

import { HeaderComponent } from '../../shared/header/header.component';
import { FooterComponent } from '../../shared/footer/footer.component';

@Component({
  selector: 'app-pergunta-detail',
  standalone: true,
  imports: [
    CommonModule, 
    RouterLink, 
    FormsModule, // Para o formulário de resposta
    HeaderComponent, 
    FooterComponent
  ],
  templateUrl: './pergunta-detail.component.html',
  styleUrls: ['./pergunta-detail.component.css'] // Novo CSS específico
})
export class PerguntaDetailComponent implements OnInit {
  
  pergunta: any = null;
  respostas: any[] = [];
  idPergunta: number | null = null;
  
  // CSU05: Interagir no fórum - Resposta
  respostaCorpo: string = '';
  
  // Mock do ID da usuária logada (para interações/permissões)
  idUsuariaLogada: number = 1; 

  constructor(
    private route: ActivatedRoute,
    private perguntaService: PerguntaService
  ) { }

  ngOnInit(): void {
    // 1. Pega o ID da URL
    this.route.paramMap.subscribe(params => {
      this.idPergunta = Number(params.get('id'));
      if (this.idPergunta) {
        this.carregarPergunta(this.idPergunta);
        this.carregarRespostas(this.idPergunta);
      }
    });
  }

  carregarPergunta(id: number) {
    this.perguntaService.buscarPergunta(id).subscribe(data => {
      this.pergunta = data;
    });
  }

  carregarRespostas(id: number) {
    this.perguntaService.buscarRespostasDaPergunta(id).subscribe(data => {
      this.respostas = data;
    });
  }

  // CSU05: Interagir no fórum - Responder
  enviarResposta() {
    if (this.idPergunta && this.respostaCorpo.trim()) {
      // 3. Se responder, a usuária escreve o conteúdo (Passo 3)
      this.perguntaService.criarResposta(this.idPergunta, this.idUsuariaLogada, this.respostaCorpo).subscribe({
        next: (novaResposta) => {
          // 4. A usuária confirma o envio (Passo 4)
          this.respostas.push(novaResposta); // Adiciona visualmente
          this.respostaCorpo = ''; // Limpa o campo
        },
        error: (err) => {
          console.error('Erro ao enviar resposta', err);
          alert('Erro ao enviar resposta. Verifique suas permissões.');
        }
      });
    }
  }

  // CSU05: Marcar Resposta como Aceita (Melhor Resposta)
  marcarAceita(idResposta: number) {
    if (confirm('Marcar esta resposta como a solução aceita?')) {
      this.perguntaService.marcarRespostaAceita(idResposta, this.idUsuariaLogada).subscribe({
        next: (respostaAceita) => {
          // Atualiza a lista visualmente
          this.respostas = this.respostas.map(r => 
            r.idResposta === idResposta ? respostaAceita : r
          );
        },
        error: (err) => {
          console.error('Erro ao marcar como aceita:', err);
          alert('Você não tem permissão para marcar esta resposta (Somente a autora da pergunta pode fazer isso).');
        }
      });
    }
  }
}