import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MaterialAcademicoService } from '../material-academico.service';

// 1. IMPORTAR OS COMPONENTES VISUAIS
import { HeaderComponent } from '../../shared/header/header.component';
import { FooterComponent } from '../../shared/footer/footer.component';

@Component({
  selector: 'app-material-academico-list',
  standalone: true,

  imports: [
    CommonModule,
    RouterLink,
    HeaderComponent,
    FooterComponent
  ],
  templateUrl: './material-academico-list.component.html',
  styleUrls: ['./material-academico-list.component.css']
})
export class MaterialAcademicoListComponent implements OnInit {

  materiais: any[] = [];

  constructor(private materialService: MaterialAcademicoService) { }

  ngOnInit(): void {
    this.materialService.listarMateriaisAprovados().subscribe(data => {
      this.materiais = data;
    });
  }
}
