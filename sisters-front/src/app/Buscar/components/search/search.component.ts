import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {

  nome = '';
  erro = '';

  constructor(private userService: UserService, private router: Router) {}

  buscar() {

    if (!this.nome.trim()) return;

    this.router.navigate(['/resultados'], {
  queryParams: { nome: this.nome }
  });
  }
}

