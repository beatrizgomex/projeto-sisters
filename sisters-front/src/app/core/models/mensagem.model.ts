import { Usuaria } from './usuaria.model';


export interface Mensagem {
  idMensagem: number;
  conteudo: string;
  dataEnvio: string;
  statusLeitura: boolean;
  remetente: Usuaria;
  destinataria: Usuaria;
}
