import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FunctionComponent } from './function.component';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { FunctionRoutes } from './function.routing';
import { InterfaceDesignComponent } from './interface-design/interface-design.component';
import { ControlsToolboxModule } from '../controls-toolbox/controls-toolbox.module';
import { HttpClientModule } from '@angular/common/http';
import { SiderMenuComponent } from './sider-menu/sider-menu.component';
import { DesignContentComponent } from './design-content/design-content.component';
import { ControlsModule } from '../../components/controls/controls.module';
import { DragulaModule } from 'ng2-dragula';

@NgModule({
    imports: [
        CommonModule,
        FunctionRoutes,
        HttpClientModule,
        NgZorroAntdModule,
        ControlsToolboxModule,
        ControlsModule,
        DragulaModule
    ],
    declarations: [
        FunctionComponent,
        SiderMenuComponent,
        InterfaceDesignComponent,
        DesignContentComponent
    ]
})
export class FunctionModule { }
