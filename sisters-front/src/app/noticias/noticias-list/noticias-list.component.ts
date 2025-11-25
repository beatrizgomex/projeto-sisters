import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { NoticiaService } from '../noticia.service';

// IMPORTAR OS SEUS COMPONENTES COMPARTILHADOS
import { HeaderComponent } from '../../shared/header/header.component';
import { FooterComponent } from '../../shared/footer/footer.component';

@Component({
  selector: 'app-noticias-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './noticias-list.component.html',
  styleUrls: ['./noticias-list.component.css']
})
export class NoticiasListComponent implements OnInit {

  noticias: any[] = [];

  // Variável para controlar se o botão de excluir aparece (Mock: true = Admin)
  isAdmin: boolean = true;

  constructor(private noticiaService: NoticiaService) { }

  ngOnInit(): void {
    this.carregarNoticias();
  }

  carregarNoticias() {
    this.noticiaService.listarNoticiasAprovadas().subscribe({
      next: (data) => {
        this.noticias = data;
      },
      error: (err) => console.error('Erro ao carregar notícias', err)
    });
  }

  // O método deve ficar DENTRO da classe
  excluirNoticia(id: number) {
    if (confirm('Tem certeza que deseja excluir esta notícia permanentemente?')) {

      // Precisamos passar o ID do admin (1) para o Backend aceitar
      const idAdmin = 1;

      this.noticiaService.excluirNoticia(id, idAdmin).subscribe({
        next: () => {
          // Remove da lista visualmente
          this.noticias = this.noticias.filter(n => n.idNoticia !== id);
          alert('Notícia excluída com sucesso.');
        },
        error: (err) => {
          console.error(err);
          alert('Erro ao excluir. Verifique se você tem permissão.');
        }
      });
    }
  }

}

