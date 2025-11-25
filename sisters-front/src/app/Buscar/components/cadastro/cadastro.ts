import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { inputCadastro as Input } from '../cadastro/input/input';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [RouterOutlet, Input, ReactiveFormsModule],
  templateUrl: './cadastro.html',
  styleUrl: './cadastro.css'
})
export class Cadastro {
  protected form!: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      nomeCompleto: [null, [Validators.required]],
      email: [null, [Validators.required, Validators.email]],
      senha: [null, [Validators.required, Validators.minLength(8)]],
      confirmarSenha: [null, [Validators.required, Validators.minLength(8)]],
    });
  }

  protected cadastro() {
    if (this.form.invalid) return;

    console.log(this.form.value);
  }
}