import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { NgZorroAntdModule } from 'ng-zorro-antd';

import { AppComponent } from './app.component';
import { AppRoutes } from './app.routing';
import { EeveeModule } from './components/eevee/eevee.module';


@NgModule({
    declarations: [
        AppComponent,
    ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutes,
        NgZorroAntdModule.forRoot(),
        EeveeModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule { }
