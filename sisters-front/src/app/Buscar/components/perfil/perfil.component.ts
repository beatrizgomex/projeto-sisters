import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../../services/user.service';
import { HeaderComponent } from '../header/header.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-perfil',
  standalone: true,
  imports: [CommonModule, HeaderComponent],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.css']
})
export class PerfilComponent implements OnInit {

  usuaria: any;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id'); 
    if (idParam) {
      const id = Number(idParam); 

      this.userService.buscarPorId(id).subscribe({
        next: (resp) => this.usuaria = resp,
        error: (err) => console.error(err)
      });
    }
  }

  solicitarAcolhimento() {
  // Id fixo do programa
  const idPrograma = 1;

  this.userService.solicitarAcolhimento(idPrograma, this.usuaria.idUsuaria)
    .subscribe({
      next: (resp) => {
        console.log("Solicitação enviada com sucesso:", resp);
        alert("Solicitação de acolhimento enviada!");
      },
      error: (err) => {
        console.error("Erro ao solicitar acolhimento:", err);
        alert("Erro ao enviar solicitação.");
      }
    });
   }
}