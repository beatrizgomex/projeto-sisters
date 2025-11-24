export interface UsuariaResponse {
  idUsuaria: number;
  nome: string;
  email: string;
  papel: string;
  papelAcolhimento?: string | null;
  bioCurta: string;
  preferenciasPriv: boolean;
  interesses: string[];
  habilidades: string[];
  curso?: string | null;
  semestre?: string | null;
}
