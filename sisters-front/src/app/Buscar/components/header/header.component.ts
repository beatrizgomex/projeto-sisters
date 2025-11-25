import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';   
import { SearchComponent } from '../search/search.component';

@Component({
  selector: 'app-header',
  standalone: true,
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
  imports: [
    RouterModule,   
    SearchComponent
  ]
})
export class HeaderComponent {}