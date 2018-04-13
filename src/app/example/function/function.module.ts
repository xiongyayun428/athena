import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FunctionComponent } from './function.component';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { FunctionRoutes } from './function.routing';
import { InterfaceDesignComponent } from './interface-design/interface-design.component';
import { ControlsToolboxModule } from '../controls-toolbox/controls-toolbox.module';
import { HttpClientModule } from '@angular/common/http';
import { SiderMenuComponent } from './sider-menu/sider-menu.component';
import { SiderSubmenuComponent } from './sider-submenu/sider-submenu.component';

@NgModule({
    imports: [
        CommonModule,
        FunctionRoutes,
        HttpClientModule,
        NgZorroAntdModule,
        ControlsToolboxModule
    ],
    declarations: [
        FunctionComponent,
        SiderMenuComponent,
        SiderSubmenuComponent,
        InterfaceDesignComponent,
]
})
export class FunctionModule { }
