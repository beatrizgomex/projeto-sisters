import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { UsuariaService } from '../../core/services/usuaria.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // <-- Adicionado
import { RouterLink } from '@angular/router'; // <-- Adicionado para RouterLink no template

@Component({
    selector: 'app-editar-perfil',
    standalone: true, // <-- Adicionado: Tornando standalone
    imports: [CommonModule, ReactiveFormsModule, RouterLink], // <-- CORREÇÃO PRINCIPAL
    templateUrl: './editar-perfil.component.html',
    styleUrls: ['./editar-perfil.component.css']
})
export class EditarPerfilComponent implements OnInit {

    form!: FormGroup;
    listaInteresses: string[] = ["Front-end", "Back-end", "UI/UX", "IA", "Dados", "Jogos"];
    
    listaHabilidades: string[] = ["Java", "Spring", "Angular", "SQL", "HTML/CSS", "Git"];

    listaPapeis: string[] = ["Aluna", "Professora", "Participante Externa", "Administradora"];

    usuariaId!: number; 
    usuariaEmail!: string;

    constructor(
        private fb: FormBuilder,
        private usuariaService: UsuariaService,
        private router: Router) {}

    ngOnInit(): void {
        this.iniciarForm();
        this.carregarDadosDaUsuaria();
    }

    iniciarForm() {
        this.form = this.fb.group({
            nome: ['', Validators.required],
            bioCurta: [''],
            semestre: [''],
            papel: [''],
            preferenciasPriv: [true],
            interesses: [[]],
            habilidades: [[]]
        });
    }

    // Corrigindo o uso de alert() para console.error, conforme as regras de UX do Iframe
    carregarDadosDaUsuaria() {
        // NOTE: O uso de localStorage deve ser substituído por Firestore em ambientes de produção.
        const dados = localStorage.getItem('usuariaLogada');

        if (dados) {
            const usuaria = JSON.parse(dados);
            this.usuariaId = usuaria.idUsuaria;
            this.usuariaEmail = usuaria.email; 

            if (!this.usuariaId) {
                console.error("ALERTA: O objeto salvo não tem ID! O backend precisa enviar o ID no login.");
            }

            this.form.patchValue({
                nome: usuaria.nome,
                bioCurta: usuaria.bioCurta,
                semestre: usuaria.semestre,
                papel: usuaria.papel,
                preferenciasPriv: usuaria.preferenciasPriv,
                interesses: usuaria.interesses || [],
                habilidades: usuaria.habilidades || []
            });
        }
    }

    toggleInteresse(interesse: string) {
        const atual = this.form.value.interesses as string[];

        if (atual.includes(interesse)) {
            this.form.patchValue({
            interesses: atual.filter(i => i !== interesse)
            });
        } else {
            this.form.patchValue({
            interesses: [...atual, interesse]
            });
        }
    }

    toggleHabilidade(habilidade: string) {
        const atual = this.form.value.habilidades as string[];
        
        if (atual.includes(habilidade)) {
            this.form.patchValue({
                habilidades: atual.filter(h => h !== habilidade)
            });
        } else {
            this.form.patchValue({
                habilidades: [...atual, habilidade]
            });
        }
    }
    togglePrivacidade(isPrivate: boolean) {
        this.form.get('preferenciasPriv')?.setValue(isPrivate);
    }

    salvar() {
        if (this.form.invalid) {
            console.error('Por favor, verifique os campos obrigatórios.');
            // Em ambiente real, você mostraria uma modal ou mensagem de erro no template.
            return;
        }
        const dadosParaAtualizar = {
            id: this.usuariaId,
            email: this.usuariaEmail,
            ...this.form.value
        };

        this.usuariaService.atualizarPerfil(this.usuariaId, dadosParaAtualizar).subscribe({
            next: (resposta) => {
                const dadosAntigos = JSON.parse(localStorage.getItem("usuariaLogada") || '{}');
                
                const novoLocalStorage = { 
                    ...dadosAntigos,
                    ...this.form.value
                }; 
                
                // NOTE: O uso de localStorage deve ser substituído por Firestore.
                localStorage.setItem("usuariaLogada", JSON.stringify(novoLocalStorage));
                
                console.log("Perfil atualizado com sucesso!");
                // Em ambiente real, você mostraria uma modal ou toast de sucesso.
            },
            error: (err) => {
                console.error("Erro ao atualizar perfil:", err);
                // Em ambiente real, você mostraria uma modal ou toast de erro.
            }
        });
    }

    excluir() {
        console.log("Exclusão de perfil iniciada. Usar modal customizado para confirmação.");

        this.usuariaService.excluir(this.usuariaId).subscribe({
            next: () => {
                console.log("Conta excluída.");
                // NOTE: O uso de localStorage deve ser substituído por Firestore.
                localStorage.clear();
                this.router.navigate(['/login']);
            },
            error: (err) => {
                console.error("Erro ao excluir perfil.", err);
            }
        });
    }

    sair() {
        this.usuariaService.logout();
        this.router.navigate(['/login']);
    }

}