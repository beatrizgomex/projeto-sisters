import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PerguntaService {

  // A URL base para as operações de Perguntas e Respostas
  private apiUrlPergunta = 'http://localhost:8080/pergunta';
  private apiUrlResposta = 'http://localhost:8080/resposta';

  constructor(private http: HttpClient) { }

  /**
   * CSU04: Listar Perguntas
   * Assume que há um endpoint para listar todas as perguntas (tópicos do fórum).
   */
  listarTodasPerguntas(): Observable<any[]> {
    // Ex: GET /pergunta/todas
    // Se o backend tiver um endpoint dedicado para listar
    return this.http.get<any[]>(`${this.apiUrlPergunta}/todas`);
  }

  /**
   * CSU04: Criar nova pergunta (Publicar no fórum)
   */
  criarPergunta(perguntaData: any, idUsuaria: number): Observable<any> {
    // O backend espera { idUsuaria, titulo, corpo }
    const payload = {
      idUsuaria: idUsuaria,
      titulo: perguntaData.titulo,
      corpo: perguntaData.corpo,
      // Se houver anexo, o backend deve processar o arquivo/link
      linkAnexo: perguntaData.linkAnexo // FA1: Anexar arquivos
    };
    // Ex: POST /pergunta
    return this.http.post(this.apiUrlPergunta, payload);
  }

  /**
   * Buscar uma pergunta específica (Detalhe do tópico)
   */
  buscarPergunta(idPergunta: number): Observable<any> {
    // Ex: GET /pergunta?idPergunta=X
    return this.http.get<any>(`${this.apiUrlPergunta}?idPergunta=${idPergunta}`);
  }

  // 👇 MÉTODO FALTANTE ADICIONADO AQUI
  /**
   * CSU04: Excluir Pergunta
   */
  excluirPergunta(idPergunta: number): Observable<void> {
    // Ex: DELETE /pergunta?idPergunta=X
    return this.http.delete<void>(`${this.apiUrlPergunta}?idPergunta=${idPergunta}`);
  }
  // 👆 FIM DO MÉTODO FALTANTE

  /**
   * Buscar Respostas de uma Pergunta
   * Assumindo que o PerguntaController ou um endpoint de Resposta retorna a lista
   */
  buscarRespostasDaPergunta(idPergunta: number): Observable<any[]> {
    // Ex: GET /pergunta/respostas?idPergunta=X
    return this.http.get<any[]>(`${this.apiUrlPergunta}/${idPergunta}/respostas`);
  }

  /**
   * CSU05: Criar Resposta (Comentar)
   */
  criarResposta(idPergunta: number, idUsuaria: number, corpo: string): Observable<any> {
    // Ex: POST /resposta?idUsuaria=X&idPergunta=Y (seguindo RespostaController)
    // O corpo da resposta pode ser enviado no body como objeto { corpo: corpo }
    return this.http.post(`${this.apiUrlResposta}?idUsuaria=${idUsuaria}&idPergunta=${idPergunta}`, { corpo: corpo });
  }

  /**
   * CSU05: Marcar Resposta como Aceita (Melhor Resposta)
   * Assume que apenas a autora da pergunta pode fazer isso
   */
  marcarRespostaAceita(idResposta: number, idUsuaria: number): Observable<any> {
    // Ex: PATCH /resposta/aceitar?idResposta=X&idUsuaria=Y
    return this.http.patch(`${this.apiUrlResposta}/aceitar?idResposta=${idResposta}&idUsuaria=${idUsuaria}`, {});
  }
}