// /sisters-front/src/app/app.config.ts

import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideHttpClient } from '@angular/common/http'; // <-- 1. IMPORT NOVO

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(), // <-- 2. ADICIONE O PROVEDOR HTTP AQUI
  ]
};
