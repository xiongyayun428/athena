import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InterfaceDesignComponent } from './interface-design.component';
import { InterfaceDesignRoutes } from './interface-design.routing';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { ControlsToolboxModule } from '../controls-toolbox/controls-toolbox.module';

/**
 * 界面设计
 *
 * @export
 * @class InterfaceDesignModule
 */
@NgModule({
    imports: [
        CommonModule,
        InterfaceDesignRoutes,
        NgZorroAntdModule,
        ControlsToolboxModule
    ],
    declarations: [
        InterfaceDesignComponent
    ]
})
export class InterfaceDesignModule { }
