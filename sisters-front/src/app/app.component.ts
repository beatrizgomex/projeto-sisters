import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';

// Componentes
import { MensagensListComponent } from './features/mensagem/mensagem-lista/mensagem-lista.component';
import { MensagemChatComponent } from './features/mensagem/mensagem-chat/mensagem-chat.component';

// Serviços (já têm providedIn: 'root')
import { MensagemService } from './core/services/mensagem.service';
import { UsuariaService } from './core/services/usuaria.service';
import { EditarPerfilComponent } from './features/editarPerfil/editar-perfil.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    HeaderComponent,
    FooterComponent,
    MensagensListComponent,
    MensagemChatComponent,
    EditarPerfilComponent
  ],
   providers: [
    MensagemService,
    UsuariaService
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'sisters-front';
}
