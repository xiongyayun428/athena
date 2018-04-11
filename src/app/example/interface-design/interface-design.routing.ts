import { Routes, RouterModule } from '@angular/router';
import { InterfaceDesignComponent } from './interface-design.component';
import { ModuleWithProviders } from '@angular/core';

const routes: Routes = [
    {
        path: '',
        component: InterfaceDesignComponent
    }
];

export const InterfaceDesignRoutes: ModuleWithProviders = RouterModule.forChild(routes);
