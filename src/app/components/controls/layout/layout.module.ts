import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LayoutComponent } from './layout.component';
import { HeaderComponent } from './header/header.component';
import { SiderComponent } from './sider/sider.component';
import { ContentComponent } from './content/content.component';
import { FooterComponent } from './footer/footer.component';
import { NgZorroAntdModule } from 'ng-zorro-antd';

@NgModule({
    imports: [
        CommonModule,
        NgZorroAntdModule
    ],
    exports: [
        LayoutComponent,
        HeaderComponent,
        SiderComponent,
        ContentComponent,
        FooterComponent
    ],
    declarations: [
        LayoutComponent,
        HeaderComponent,
        SiderComponent,
        ContentComponent,
        FooterComponent
    ],
    // entryComponents: [
    //     LayoutComponent,
    //     HeaderComponent,
    //     SiderComponent,
    //     ContentComponent,
    //     FooterComponent
    // ]
})
export class LayoutModule { }
