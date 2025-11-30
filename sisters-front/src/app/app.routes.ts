
import { Routes } from '@angular/router';
import { NoticiasListComponent } from './noticias/noticias-list/noticias-list.component';
import { MaterialAcademicoListComponent } from './materiais/material-academico-list/material-academico-list.component';
import { MaterialAcademicoFormComponent } from './materiais/material-academico-form/material-academico-form.component';
import { NoticiaFormComponent } from './noticias/noticia-form/noticia-form.component';
import { AdminDashboardComponent } from './admin/admin-dashboard/admin-dashboard.component';
import { MensagensListComponent } from './features/mensagem/mensagem-lista/mensagem-lista.component';
import { MensagemChatComponent } from './features/mensagem/mensagem-chat/mensagem-chat.component';
import { Login } from './features/login/login';
import { Cadastro } from './features/cadastro/cadastro';
import { EditarPerfilComponent } from './features/editarPerfil/editar-perfil.component';
import { HomeComponent } from './Buscar/home/home.component';
import { SearchComponent } from './Buscar/components/search/search.component';
import { PerfilComponent } from './Buscar/components/perfil/perfil.component';
import { NotificacoesComponent } from './notificacoes/notificacoes.component';


export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  //Rota da página institucional
  { path: 'institucional', component: HomeComponent },

  //Rota de notificações
  { path: 'notificacoes', component: NotificacoesComponent },

  // Rotas de Notícias
  { path: 'noticias', component: NoticiasListComponent },
    { path: 'noticias/publicar', component: NoticiaFormComponent },

  // Rotas de Materiais
  { path: 'materiais', component: MaterialAcademicoListComponent },
  { path: 'materiais/compartilhar', component: MaterialAcademicoFormComponent },
  { path: 'admin', component: AdminDashboardComponent },

  { path: 'login', component: Login },
  { path: 'cadastro', component: Cadastro },
  { path: 'mensagens', component: MensagensListComponent },
  { path: 'chat/:id', component: MensagemChatComponent },
  { path: 'atualizarperfil', component: EditarPerfilComponent },
  { path: '**', redirectTo: '/login' },

  //Rotas das páginas de busca 
  {
  path: 'buscar',
  loadComponent: () =>
    import('./Buscar/components/search/search.component')
      .then(c => c.SearchComponent)
  },

  {
  path: 'resultados',
  loadComponent: () =>
    import('./Buscar/components/search-results/search-results.component')
      .then(c => c.SearchResultsComponent)
  },

];




