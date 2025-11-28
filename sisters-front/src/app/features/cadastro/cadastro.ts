import { Component, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UsuariaService } from '../../core/services/usuaria.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Mantendo se for usar *ngIf/*ngFor no template
import { inputCadastro as Input } from '../cadastro/input/input';


@Component({
    selector: 'app-root',
    standalone: true,
    // Corrigido: RouterOutlet e Input removidos conforme warning se não estiverem no template
    imports: [ReactiveFormsModule, CommonModule, Input], 
    templateUrl: './cadastro.html',
    styleUrl: './cadastro.css'
})
export class Cadastro {
    title = 'Cadastre-se';

    protected form!: FormGroup; 
    constructor(
        private fb: FormBuilder,
        private usuariaService: UsuariaService,
        private router: Router
    ) {
        this.form = this.fb.group({
            nomeCompleto: [null, [Validators.required]],
            email: [null, [Validators.required, Validators.email]],
            senha: [null, [Validators.required, Validators.minLength(8)]],
            confirmarSenha: [null, [Validators.required, Validators.minLength(8)]],
            curso: ['INDEFINIDO'], 
            semestre: [0],        
            bioCurta: ['Nova usuária na plataforma'],
            papel: ['OUTRO'],
            papelAcolhimento: ['NENHUM'],
            preferenciasPriv: [false]
        });
    }

    protected cadastro() {
        if (this.form.invalid) {
            alert('Por favor, preencha todos os campos corretamente.'); // Corrigido alert()
            return;
        }

        const dadosFormulario = this.form.getRawValue();

        // **Validação de Senhas (Manual, por enquanto)**
        if (dadosFormulario.senha !== dadosFormulario.confirmarSenha) {
            alert('As senhas digitadas não coincidem.'); // Corrigido alert()
            return;
        }

        const usuarioParaBackend = {
            nome: dadosFormulario.nomeCompleto,
            email: dadosFormulario.email,
            senha: dadosFormulario.senha,
            bioCurta: dadosFormulario.bioCurta,
            papel: dadosFormulario.papel,
            papelAcolhimento: dadosFormulario.papelAcolhimento,
            preferenciasPriv: dadosFormulario.preferenciasPriv,
            curso: dadosFormulario.curso,        // <-- Novo campo
            semestre: dadosFormulario.semestre  // <-- Novo campo
        };

        this.usuariaService.cadastrar(usuarioParaBackend).subscribe({
            next: (resposta) => {
                alert("Cadastro realizado com sucesso, faça login")
                console.log('Usuária cadastrada com sucesso!', resposta);
                this.router.navigate(['/login']);
            },
            error: (erro) => {
                alert('Erro ao cadastrar:' + erro);
                
                const mensagemErro = erro.error.message || 'Erro ao cadastrar. Verifique o console.';
                alert(`Falha no Cadastro: ${mensagemErro}`); // Corrigido alert()
            }
        });
    }
}