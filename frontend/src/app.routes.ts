import { Routes } from '@angular/router';
import { AppLayout } from './app/layout/component/app.layout';


export const appRoutes: Routes = [
    {
        path: '',
        component: AppLayout,
    },
    { path: '**', redirectTo: '/notfound' }
];
