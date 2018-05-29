import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlsComponent } from './controls.component';
import { LayoutModule } from './layout/layout.module';

@NgModule({
    imports: [
        CommonModule,
        LayoutModule
    ],
    exports: [
        LayoutModule
    ],
    declarations: [
        ControlsComponent
    ]
})
export class ControlsModule { }
