import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Necessário para [(ngModel)]
import { Router, RouterLink } from '@angular/router'; // Necessário para routerLink
import { MaterialAcademicoService } from '../material-academico.service';

// IMPORT DOS COMPONENTES COMPARTILHADOS
import { HeaderComponent } from '../../shared/header/header.component';
import { FooterComponent } from '../../shared/footer/footer.component';

@Component({
  selector: 'app-material-academico-form',
  standalone: true,
  // LISTA DE IMPORTS OBRIGATÓRIA
  imports: [
    CommonModule,
    FormsModule,
    RouterLink,
    HeaderComponent, // <--- Permite usar <app-header>
    FooterComponent  // <--- Permite usar <app-footer>
  ],
  templateUrl: './material-academico-form.component.html',
  styleUrls: ['./material-academico-form.component.css']
})
export class MaterialAcademicoFormComponent implements OnInit {

  materialData = {
    titulo: '',
    descricao: '',
    linkArquivo: '',
    tipo: 'Artigo'
  };

  autoraId: number = 1; // Fixo temporariamente (Simulando Maria Silva)
  mensagemSucesso: string = '';
  mensagemErro: string = '';

  constructor(
    private materialService: MaterialAcademicoService,
    private router: Router
  ) { }

  ngOnInit(): void {}

  onSubmit() {
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.materialService.compartilharMaterial(this.materialData, this.autoraId).subscribe({
      next: (response) => {
        console.log('Material Enviado:', response);
        this.mensagemSucesso = 'Material compartilhado com sucesso! Aguardando aprovação.';

        // Redireciona para a lista após 2 segundos
        setTimeout(() => {
          this.router.navigate(['/materiais']);
        }, 2000);
      },
      error: (err) => {
        console.error('Erro ao enviar material:', err);
        this.mensagemErro = 'Falha ao enviar material. Verifique a conexão.';
      }
    });
  }
}
