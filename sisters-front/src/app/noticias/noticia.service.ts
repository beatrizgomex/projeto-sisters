// /src/app/noticias/noticia.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NoticiaService {

  // URL do seu Backend Spring Boot (servidor Tomcat)
  private apiUrl = 'http://localhost:8080/api/v1/noticias';


  // O Angular injeta o cliente HTTP para fazer as requisições
  constructor(private http: HttpClient) { }


// Busca notícias com status 'Pendente' usando o controller
listarPendentes(): Observable<any[]> {
  // Chama: GET /api/v1/noticias/status?status=Pendente
  return this.http.get<any[]>(`${this.apiUrl}/status?status=Pendente`);
}

// Aprova a notícia (Muda status para 'Aceito')
aprovarNoticia(id: number, adminId: number): Observable<any> {
  return this.http.put(`${this.apiUrl}/${id}/aprovar?usuarioLogadoId=${adminId}`, {});
}

// Rejeita a notícia (Muda status para 'Recusado')
rejeitarNoticia(id: number, adminId: number): Observable<any> {
  return this.http.put(`${this.apiUrl}/${id}/rejeitar?usuarioLogadoId=${adminId}`, {});
}

  // 1. Método para buscar a lista de notícias aprovadas (GET /api/v1/noticias)
  listarNoticiasAprovadas(): Observable<any[]> {
    // Retorna uma requisição GET. O 'any[]' indica que ele espera uma lista de objetos JSON.
    return this.http.get<any[]>(this.apiUrl);
  }

  // 2. Método para criar uma nova notícia (POST /api/v1/noticias)
  // Recebe o objeto Noticia e o ID da autora para enviar ao Controller do backend.
  criarNoticia(noticia: any, autoraId: number): Observable<any> {
    // Monta a URL com o parâmetro de query (?autoraId=...) e envia o objeto JSON (noticia) no corpo.
    return this.http.post(`${this.apiUrl}?autoraId=${autoraId}`, noticia);
  }

// Adicione este método na classe NoticiaService

// Método para Excluir (DELETE)
excluirNoticia(id: number): Observable<void> {
  // Passamos o ID da notícia e o ID 1 (Admin) para ter permissão
  return this.http.delete<void>(`${this.apiUrl}/${id}?usuarioLogadoId=1`);
}}
