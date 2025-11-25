import { Component } from '@angular/core';

import { HeaderComponent } from '../components/header/header.component';
import { HeroComponent } from '../components/hero/hero.component';
import { MdunirioComponent } from '../components/mdunirio/mdunirio.component';
import { FuncionaComponent } from '../components/funciona/funciona.component';
import { AcolhimentoComponent } from '../components/acolhimento/acolhimento.component';
import { FooterComponent } from '../components/footer/footer.component';

@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [
    HeaderComponent,
    HeroComponent,
    MdunirioComponent,
    FuncionaComponent,
    AcolhimentoComponent,
    FooterComponent
  ]
})
export class HomeComponent {}
