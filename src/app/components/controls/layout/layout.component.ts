import { Component, OnInit, ContentChildren, QueryList } from '@angular/core';
import { ContentComponent } from './content/content.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { SiderComponent } from './sider/sider.component';
import { ControlsComponent } from '../controls.component';

@Component({
    selector: 'a-layout',
    templateUrl: './layout.component.html',
    styleUrls: ['./layout.component.scss']
})
export class LayoutComponent extends ControlsComponent implements OnInit {

    @ContentChildren(LayoutComponent) layouts: QueryList<LayoutComponent>;
    @ContentChildren(ContentComponent) contents: QueryList<ContentComponent>;
    @ContentChildren(FooterComponent) footers: QueryList<FooterComponent>;
    @ContentChildren(HeaderComponent) headers: QueryList<HeaderComponent>;
    @ContentChildren(SiderComponent) siders: QueryList<SiderComponent>;

    constructor() {
        super();
    }

    ngOnInit() {
    }

    click(event: any) {
        console.log(event);
        this.attributes.emit(event);
    }

}
