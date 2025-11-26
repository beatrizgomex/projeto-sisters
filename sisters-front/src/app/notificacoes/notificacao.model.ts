export interface Notificacao {
  id: number;
  tipo: 'amizade' | 'acolhimento' | 'respostaAcolhimento' | 'forum';
  mensagem: string;
  data: Date;
  lida: boolean;
  remetenteId?: number;
}