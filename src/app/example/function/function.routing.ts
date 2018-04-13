import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { FunctionComponent } from './function.component';
import { InterfaceDesignComponent } from './interface-design/interface-design.component';

const routes: Routes = [
    {
        path: '',
        component: FunctionComponent
    },
    { path: 'design/:name', component: InterfaceDesignComponent }
];

export const FunctionRoutes: ModuleWithProviders = RouterModule.forChild(routes);
