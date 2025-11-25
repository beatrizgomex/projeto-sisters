import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { InputLogin as Input } from '../login/input/input';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  imports: [RouterOutlet, Input, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  title = 'Login';

  protected form!: FormGroup; 
  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      email: [null, [Validators.required, Validators.email]],
      senha: [null, [Validators.required, Validators.minLength(8)]],
    });
  }

  protected login() {
    if(this.form.invalid) return; 
    
    const {email, senha}= this.form.getRawValue();
    console.log(email);
    console.log(senha);
    
  }
}

