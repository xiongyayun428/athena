import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';

const routes: Routes = [
    {
        path: 'function',
        loadChildren: './example/function/function.module#FunctionModule',
    },
    // {
    //     path: 'design',
    //     loadChildren: './example/interface-design/interface-design.module#InterfaceDesignModule',
    // },
    { path: '', redirectTo: 'function', pathMatch: 'full' },
    { path: '**', redirectTo: 'function', pathMatch: 'full' }
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes);
