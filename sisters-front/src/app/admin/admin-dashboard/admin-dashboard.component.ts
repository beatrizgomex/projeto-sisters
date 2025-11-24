import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoticiaService } from '../../noticias/noticia.service';
import { MaterialAcademicoService } from '../../materiais/material-academico.service';
import { HeaderComponent } from '../../shared/header/header.component';
import { FooterComponent } from '../../shared/footer/footer.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  noticiasPendentes: any[] = [];
  materiaisPendentes: any[] = [];

  // ID da Administradora (Maria Silva)
  adminId: number = 1;

  constructor(
    private noticiaService: NoticiaService,
    private materialService: MaterialAcademicoService
  ) {}

  ngOnInit(): void {
    // Chama o método ao iniciar a tela
    this.carregarPendencias();
  }

  // MÉTODO QUE ESTAVA FALTANDO OU COM NOME ERRADO
  carregarPendencias() {
    // 1. Busca Notícias Pendentes
    this.noticiaService.listarPendentes().subscribe(data => {
      this.noticiasPendentes = data;
    });

    // 2. Busca Materiais Pendentes
    this.materialService.listarPendentes().subscribe(data => {
      this.materiaisPendentes = data;
    });
  }

  // --- AÇÕES DE NOTÍCIA ---
  aprovarNoticia(id: number) {
    if(confirm('Confirmar aprovação desta notícia?')) {
      this.noticiaService.aprovarNoticia(id, this.adminId).subscribe(() => {
        this.carregarPendencias(); // Recarrega a lista
      });
    }
  }

  rejeitarNoticia(id: number) {
    if(confirm('Tem certeza que deseja rejeitar esta notícia?')) {
      this.noticiaService.rejeitarNoticia(id, this.adminId).subscribe({
        next: () => {
          alert('Notícia rejeitada.');
          this.carregarPendencias(); // Recarrega a lista
        },
        error: (err) => {
          console.error(err);
          alert('Erro ao rejeitar.');
        }
      });
    }
  }

  // --- AÇÕES DE MATERIAL ---
  aprovarMaterial(id: number) {
    if(confirm('Confirmar aprovação deste material?')) {
      this.materialService.aprovarMaterial(id, this.adminId).subscribe(() => {
        this.carregarPendencias();
      });
    }
  }

  rejeitarMaterial(id: number) {
     if(confirm('Tem certeza que deseja rejeitar este material?')) {
      this.materialService.rejeitarMaterial(id, this.adminId).subscribe({
        next: () => {
          alert('Material rejeitado.');
          this.carregarPendencias();
        },
        error: (err) => alert('Erro ao rejeitar.')
      });
    }
  }
}


