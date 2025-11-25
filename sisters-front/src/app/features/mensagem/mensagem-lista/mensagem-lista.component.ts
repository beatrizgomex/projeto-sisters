import { Component, OnInit } from '@angular/core';
import { MensagemService } from '../../../core/services/mensagem.service';
import { Mensagem } from '../../../core/models/mensagem.model';
import { Usuaria } from '../../../core/models/usuaria.model';
import { UsuariaService } from '../../../core/services/usuaria.service';
import { Router, RouterLink } from '@angular/router'; // <-- Adicionado RouterLink
import { forkJoin, Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { CommonModule } from '@angular/common'; // <-- Adicionado


@Component({
    selector: 'app-mensagem-lista',
    standalone: true, // <-- Adicionado: Tornando standalone
    imports: [CommonModule, RouterLink], // <-- CORREÇÃO PRINCIPAL
    templateUrl: './mensagem-lista.component.html',
    styleUrls: ['./mensagem-lista.component.css'],
})

export class MensagensListComponent implements OnInit {

    usuarias: Usuaria[] = [];
    conversas: { usuaria: Usuaria, ultimaMensagem: Mensagem | null }[] = [];
    usuariaLogadaId!: number;

    constructor(
        private mensagemService: MensagemService,
        private usuariaService: UsuariaService,
        private router: Router
    ) {}

    ngOnInit(): void {
        // NOTE: O uso de localStorage deve ser substituído por Firestore.
        const usuariaLogada = JSON.parse(localStorage.getItem('usuariaLogada')!);
        if (usuariaLogada && usuariaLogada.idUsuaria) {
            this.usuariaLogadaId = usuariaLogada.idUsuaria;
        } else {
            console.error('ID da usuária logada não encontrado.');
            return;
        }
        this.carregarListaDeConversas();
    }

    carregarListaDeConversas(): void {
        this.mensagemService.buscarAmigas(this.usuariaLogadaId).pipe(
            switchMap(amigas => {
                if (amigas.length === 0) {
                    return new Observable<any>();
                }

                const ultimaMensagemObservables = amigas.map(amiga =>
                    this.mensagemService.buscarUltimaMensagem(this.usuariaLogadaId, amiga.idUsuaria).pipe(
                        map(msg => ({
                            usuaria: amiga,
                            ultimaMensagem: msg
                        }))
                    )
                );

                return forkJoin(ultimaMensagemObservables);
            })
        ).subscribe({
            next: (conversas) => {
                this.conversas = conversas;
            },
            error: (err) => console.error('Erro ao carregar conversas:', err)
        });
    }

    abrirConversa(id: number) {
        this.router.navigate(['/chat', id]);
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
