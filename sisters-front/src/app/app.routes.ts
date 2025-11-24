
import { Routes } from '@angular/router';
import { NoticiasListComponent } from './noticias/noticias-list/noticias-list.component';
import { MaterialAcademicoListComponent } from './materiais/material-academico-list/material-academico-list.component';
import { MaterialAcademicoFormComponent } from './materiais/material-academico-form/material-academico-form.component';
import { NoticiaFormComponent } from './noticias/noticia-form/noticia-form.component';
import { AdminDashboardComponent } from './admin/admin-dashboard/admin-dashboard.component';


export const routes: Routes = [
  // Rota Padrão (Redireciona para noticias)
  { path: '', redirectTo: '/noticias', pathMatch: 'full' },

  // Rotas de Notícias
  { path: 'noticias', component: NoticiasListComponent },
    { path: 'noticias/publicar', component: NoticiaFormComponent },

  // Rotas de Materiais
  { path: 'materiais', component: MaterialAcademicoListComponent },
  { path: 'materiais/compartilhar', component: MaterialAcademicoFormComponent },

 { path: 'admin', component: AdminDashboardComponent },

];




