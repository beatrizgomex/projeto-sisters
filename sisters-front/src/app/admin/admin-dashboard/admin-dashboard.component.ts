import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoticiaService } from '../../noticias/noticia.service';
import { MaterialAcademicoService } from '../../materiais/material-academico.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  noticiasPendentes: any[] = [];
  materiaisPendentes: any[] = [];

  // ID da Administradora (Maria Silva) fixo para teste
  adminId: number = 1;

  constructor(
    private noticiaService: NoticiaService,
    private materialService: MaterialAcademicoService
  ) {}

  ngOnInit(): void {
    this.carregarTudo();
  }

  carregarTudo() {
    // 1. Busca Notícias Pendentes
    this.noticiaService.listarPendentes().subscribe(data => {
      this.noticiasPendentes = data;
    });

    // 2. Busca Materiais Pendentes
    this.materialService.listarPendentes().subscribe(data => {
      this.materiaisPendentes = data;
    });
  }

  // Ações de Notícia
  aprovarNoticia(id: number) {
    this.noticiaService.aprovarNoticia(id, this.adminId).subscribe(() => {
      alert('Notícia Aprovada!');
      this.carregarTudo(); // Recarrega a lista para o item sumir
    });
  }

  // Ações de Material
  aprovarMaterial(id: number) {
    this.materialService.aprovarMaterial(id, this.adminId).subscribe(() => {
      alert('Material Aprovado!');
      this.carregarTudo();
    });
  }
}
