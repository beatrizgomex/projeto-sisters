export interface Notificacao {
  id: number;
  tipo: 'amizade'
    | 'solicitacaoAcolhimento'
    | 'respostaAcolhimento'
    | 'respostaForum';
  mensagem: string;
  dataCriacao: string | Date;
  lida: boolean;
  usuariaRelacionadoId?: number;
  referenciaId?: number;
}