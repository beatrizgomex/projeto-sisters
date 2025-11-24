import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuariaService } from '../../core/services/usuaria.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Mantendo se for usar *ngIf/*ngFor no template

@Component({
    selector: 'app-root',
    standalone: true,
    // Corrigido: RouterOutlet e Input removidos conforme warning se não estiverem no template
    imports: [ReactiveFormsModule, CommonModule], 
    templateUrl: './login.html',
    styleUrl: './login.css'
})
export class Login {
    title = 'Login';

    protected form!: FormGroup; 

    constructor(
        private fb: FormBuilder,
        private usuariaService: UsuariaService, 
        private router: Router                  
    ) {
        this.form = this.fb.group({
            email: [null, [Validators.required, Validators.email]],
            senha: [null, [Validators.required, Validators.minLength(8)]],
        });
    }

    protected login() {
        if(this.form.invalid) {
            console.error('Por favor, preencha o e-mail e a senha corretamente.'); // Corrigido alert()
            return; 
        }
    
        const credenciais = this.form.getRawValue();

        this.usuariaService.login(credenciais).subscribe({
            next: (resposta) => {
                console.log('Login bem-sucedido!', resposta);

                // NOTE: O uso de localStorage deve ser substituído por Firestore.
                localStorage.setItem('usuariaLogada', JSON.stringify(resposta)); 
                
                console.log(`Bem-vinda, ${resposta.nome}!`); // Corrigido alert()

                this.router.navigate(['/perfil']); 
            },
            error: (erro) => {
                console.error('Erro no login:', erro);
                
                const mensagemErro = erro.error.message || 'Erro ao tentar fazer login. Verifique as credenciais.';
                console.error(`Falha no Login: ${mensagemErro}`); // Corrigido alert()
            }
        });
    }
}