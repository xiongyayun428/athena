import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';

const routes: Routes = [
    {
        path: 'design',
        loadChildren: './example/interface-design/interface-design.module#InterfaceDesignModule',
    },
    { path: '', redirectTo: 'design', pathMatch: 'full' },
    { path: '**', redirectTo: 'design', pathMatch: 'full' }
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes);
