import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user.service'; 
import { HeaderComponent } from '../../../shared/header/header.component';
import { CommonModule } from '@angular/common';
import { FooterComponent } from '../../../shared/footer/footer.component';

@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [CommonModule, HeaderComponent, FooterComponent],
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {

  termoBuscado = '';
  resultados: any[] = [];
  carregando = false;
  semResultados = false;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.termoBuscado = params['nome'] || '';

      if (this.termoBuscado) {
        this.buscar();
      }
    });
  }

  buscar() {
    this.carregando = true;

    this.userService.buscarPorNome(this.termoBuscado).subscribe({
      next: (res: any[]) => {
      console.log("RESPOSTA DA API:", res);
      this.carregando = false;
      this.resultados = res;
      this.semResultados = res.length === 0;
    },
      error: () => {
        this.carregando = false;
        this.semResultados = true;
      }
    });
  }

  abrirPerfil(id: number) {
    this.router.navigate(['/perfil', id]);
  }
}