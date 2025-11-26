import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common'; // <--- 1. GARANTA ESTE IMPORT
import { NotificacoesService } from '../../core/services/notificacoes.service';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    CommonModule, // <--- 2. ADICIONE AQUI
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit{

  // MOCK: Mude para 'false' quando quiser testar a visão de uma aluna
  isAdmin: boolean = true;

  // No futuro, você vai substituir essa linha fixa por algo como:
  // this.isAdmin = authService.getUsuario().papel === 'administradora';
  contador = 0;

  constructor(private notificacoesService: NotificacoesService) {}

  ngOnInit() {
    this.notificacoesService.notificacoes$
      .subscribe(lista => {
        this.contador = lista.filter(n => !n.lida).length;
      });
  }

}


