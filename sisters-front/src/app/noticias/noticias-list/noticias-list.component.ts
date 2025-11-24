
import { Component, OnInit } from '@angular/core';
import { NoticiaService } from '../noticia.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-noticias-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './noticias-list.component.html',
  styleUrls: ['./noticias-list.component.css']
})
export class NoticiasListComponent implements OnInit {


  noticias: any[] = [];

  constructor(private noticiaService: NoticiaService) { }


  ngOnInit(): void {
      this.noticiaService.listarNoticiasAprovadas().subscribe((data: any[]) => {
        this.noticias = data;
      console.log('Dados recebidos do Backend:', this.noticias);
    });
  }

  excluirNoticia(id: number) {
  if (confirm('Tem certeza que deseja excluir esta notícia permanentemente?')) {

    this.noticiaService.excluirNoticia(id).subscribe({
      next: () => {
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
