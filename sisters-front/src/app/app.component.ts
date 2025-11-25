import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './Buscar/components/header/header.component';
import { HeroComponent } from './Buscar/components/hero/hero.component';
import { MdunirioComponent } from './Buscar/components/mdunirio/mdunirio.component';
import { FuncionaComponent } from './Buscar/components/funciona/funciona.component';
import { AcolhimentoComponent } from './Buscar/components/acolhimento/acolhimento.component';
import { FooterComponent } from './Buscar/components/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    CommonModule,
    HeaderComponent,
    HeroComponent,
    MdunirioComponent,
    FuncionaComponent,
    AcolhimentoComponent,
    FooterComponent
  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'sisters-front';
}
