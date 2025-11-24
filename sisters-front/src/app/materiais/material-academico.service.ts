// /src/app/materiais/material-academico.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MaterialAcademicoService {

  private apiUrl = 'http://localhost:8080/api/v1/materiais';

  constructor(private http: HttpClient) { }

  // Método GET: Listar
  listarMateriaisAprovados(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Método POST: Compartilhar
  compartilharMaterial(material: any, autoraId: number): Observable<any> {
    const url = `${this.apiUrl}?autoraId=${autoraId}`;
    return this.http.post(url, material);
  }
listarPendentes(): Observable<any[]> {
  // Atenção: Verifique se no seu Controller é '/status' ou '/por-status'
  return this.http.get<any[]>(`${this.apiUrl}/por-status?status=Pendente`);
}

aprovarMaterial(id: number, adminId: number): Observable<any> {
  return this.http.put(`${this.apiUrl}/${id}/aprovar?usuarioLogadoId=${adminId}`, {});
}

rejeitarMaterial(id: number, adminId: number): Observable<any> {
  return this.http.put(`${this.apiUrl}/${id}/rejeitar?usuarioLogadoId=${adminId}`, {});
}
}
