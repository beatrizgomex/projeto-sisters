// /src/app/materiais/material-academico-list/material-academico-list.component.ts

import { Component, OnInit } from '@angular/core';
import { MaterialAcademicoService } from '../material-academico.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-material-academico-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink
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
      console.log('Materiais recebidos:', this.materiais);
    });
  }
}
