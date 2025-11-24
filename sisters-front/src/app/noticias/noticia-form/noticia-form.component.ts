import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { NoticiaService } from '../noticia.service';
import { HeaderComponent } from '../../shared/header/header.component';
import { FooterComponent } from '../../shared/footer/footer.component';

@Component({
  selector: 'app-noticia-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, HeaderComponent, FooterComponent],
  templateUrl: './noticia-form.component.html',
  styleUrls: ['./noticia-form.component.css']
})
export class NoticiaFormComponent {

  noticiaData = { titulo: '', descricao: '' };
  autoraId = 1;

  // NOVAS VARIÁVEIS
  mensagemSucesso: string = '';
  mensagemErro: string = '';

  constructor(private noticiaService: NoticiaService, private router: Router) {}

  onSubmit() {
    // Limpa mensagens anteriores
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.noticiaService.criarNoticia(this.noticiaData, this.autoraId).subscribe({
      next: () => {
        //Exibe a mensagem na tela
        this.mensagemSucesso = 'Notícia enviada para moderação com sucesso!';

        //Limpa o formulário
        this.noticiaData = { titulo: '', descricao: '' };

        // Espera 2 segundos para a usuária ler, depois redireciona
        setTimeout(() => {
          this.router.navigate(['/noticias']);
        }, 2000);
      },
      error: (err) => {
        console.error(err);
        this.mensagemErro = 'Erro ao enviar a notícia. Tente novamente.';
      }
    });
  }
}
