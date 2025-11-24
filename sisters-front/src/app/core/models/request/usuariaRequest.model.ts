export interface UsuariaRequest {
  nome: string;
  email: string;
  senha: string;
  papel: string;
  papelAcolhimento?: string | null;
  bioCurta: string;
  preferenciasPriv: boolean;
  interesses: string[];
  habilidades: string[];
  curso?: string | null;
  semestre?: string | null;
}
