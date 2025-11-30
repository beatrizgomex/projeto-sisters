import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MensagemService } from '../../../core/services/mensagem.service';
import { Mensagem } from '../../../core/models/mensagem.model';
import { MensagemRequest } from '../../../core/models/request/mensagemRequest.model';
import { UsuariaService } from '../../../core/services/usuaria.service';
import { CommonModule } from '@angular/common'; // <-- Adicionado
import { HeaderComponent } from '../../../shared/header/header.component';
import { FooterComponent } from '../../../shared/footer/footer.component';

@Component({
    selector: 'app-mensagem-chat',
    standalone: true, // <-- Adicionado: Tornando standalone
    imports: [CommonModule, ReactiveFormsModule, RouterLink, HeaderComponent, FooterComponent], // <-- CORREÇÃO PRINCIPAL
    templateUrl: './mensagem-chat.component.html',
    styleUrls: ['./mensagem-chat.component.css']
})

export class MensagemChatComponent implements OnInit {

    usuariaId!: number;            // ID da usuária com quem vamos conversar
    usuariaNome: string = '';      // Nome da usuária (para título do chat)
    mensagens: Mensagem[] = [];    // Array de mensagens
    usuariaLogadaId!: number;      // ID do usuário logado

    novaMensagemForm = new FormGroup({
        conteudo: new FormControl('', [Validators.required])
    });

    constructor(
        private route: ActivatedRoute,
        private mensagemService: MensagemService,
        private usuariaService: UsuariaService
    ) {}

    ngOnInit(): void {
        // Pega o ID do usuário logado do localStorage
        // NOTE: O uso de localStorage deve ser substituído por Firestore.
        const usuariaLogada = localStorage.getItem('usuariaLogada');
        if (usuariaLogada) {
            this.usuariaLogadaId = JSON.parse(usuariaLogada).idUsuaria;
            console.log('Usuária Logada ID (localStorage):', this.usuariaLogadaId); // NOVO LOG
        } else {
            console.error('Usuário não logado no localStorage!');
            return;
        }

        // Pega a usuária com quem vamos conversar via rota
        this.usuariaId = Number(this.route.snapshot.paramMap.get('id'));
        console.log('Usuária Destinatária ID (rota):', this.usuariaId); // NOVO LOG

        // Pega o nome da usuária (para o título do chat)
        this.carregarNomeUsuaria();

        // Carrega o histórico de mensagens
        this.carregarHistorico();
    }

    // Busca informações da usuária pelo ID
    carregarNomeUsuaria() {
        this.usuariaService.buscarPorId(this.usuariaId).subscribe({
            next: (usuaria) => this.usuariaNome = usuaria.nome,
            error: (err) => console.error('Erro ao buscar usuária', err)
        });
    }

    // Busca histórico de mensagens entre usuário logado e usuária
    carregarHistorico() {
        this.mensagemService.buscarHistorico(this.usuariaLogadaId, this.usuariaId).subscribe({
            next: (msgs) => {
                this.mensagens = msgs.sort((a, b) => new Date(a.dataEnvio).getTime() - new Date(b.dataEnvio).getTime());
            },
            error: (err) => console.error('Erro ao carregar histórico', err)
        });
    }
    enviarMensagem() {
        const conteudo = this.novaMensagemForm.value.conteudo?.trim();
        if (!conteudo) return;

        console.log('Verificando amizade entre Logada:', this.usuariaLogadaId, 'e Destinatária:', this.usuariaId); // LOG CRÍTICO


        this.mensagemService.verificarAmizade(this.usuariaLogadaId, this.usuariaId)
            .subscribe({
                next: (saoAmigas) => {
                    if (!saoAmigas) {
                        console.error('Você só pode enviar mensagens para suas amigas.'); // Corrigido alert()
                        // Em ambiente real, use uma modal ou notificação
                        return;
                    }
                    const request: MensagemRequest = {
                        conteudo,
                        idRemetente: this.usuariaLogadaId,
                        idDestinataria: this.usuariaId
                    };

                    this.mensagemService.enviarMensagem(request).subscribe({
                        next: (msg) => {
                            this.mensagens.push(msg);
                            this.novaMensagemForm.reset();
                            this.carregarHistorico();
                        },
                        error: (err) => console.error('Erro ao enviar mensagem', err)
                    });
                },
                error: (err) => console.error('Erro ao verificar amizade', err)
            });
    }

    formatarHorario(data: any): string {
        const d = new Date(data);
        const hoje = new Date();

        if (
            d.getDate() === hoje.getDate() &&
            d.getMonth() === hoje.getMonth() &&
            d.getFullYear() === hoje.getFullYear()
        ) {
            return d.toLocaleTimeString('pt-BR', { hour: '2-digit', minute: '2-digit' });
        } else {
            return d.toLocaleDateString('pt-BR', { day: '2-digit', month: '2-digit' });
        }
    }

}