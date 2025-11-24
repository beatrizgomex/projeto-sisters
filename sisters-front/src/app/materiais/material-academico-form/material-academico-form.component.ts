
import { Component, OnInit } from '@angular/core';
import { MaterialAcademicoService } from '../material-academico.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterLink} from '@angular/router';

@Component({
  selector: 'app-material-academico-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './material-academico-form.component.html',
  styleUrls: ['./material-academico-form.component.css']
})
export class MaterialAcademicoFormComponent implements OnInit {

  // Objeto que será enviado ao Backend (só com os campos necessários)
  materialData = {
    titulo: '',
    descricao: '',
    linkArquivo: '',
    tipo: 'Artigo' // Valor padrão, baseado no ENUM do Backend
  };

  // Usaremos o ID 1, a administradora, como autora para fins de teste
  autoraId: number = 1;
  mensagemSucesso: string = '';
  mensagemErro: string = '';

  constructor(private materialService: MaterialAcademicoService) { }

  ngOnInit(): void {}

  // Método chamado quando o usuário clica em Enviar
  onSubmit() {
    this.mensagemSucesso = '';
    this.mensagemErro = '';

    // Chama o método POST no Service
    this.materialService.compartilharMaterial(this.materialData, this.autoraId).subscribe({
      next: (response) => {
        console.log('Material Enviado:', response);
        this.mensagemSucesso = 'Material compartilhado com sucesso! Aguardando aprovação.';
        // Limpar o formulário
        this.materialData = { titulo: '', descricao: '', linkArquivo: '', tipo: 'Artigo' };
      },
      error: (err) => {
        console.error('Erro ao enviar material:', err);
        this.mensagemErro = 'Falha ao enviar material. Verifique a conexão e o status do Backend.';
      }
    });
  }
}
