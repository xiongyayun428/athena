import { Component, OnInit, ContentChildren, QueryList } from '@angular/core';
import { ContentComponent } from './content/content.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { SiderComponent } from './sider/sider.component';

@Component({
    selector: 'ks-layout',
    templateUrl: './layout.component.html',
    styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {

    @ContentChildren(LayoutComponent) layouts: QueryList<LayoutComponent>;
    @ContentChildren(ContentComponent) contents: QueryList<ContentComponent>;
    @ContentChildren(FooterComponent) footers: QueryList<FooterComponent>;
    @ContentChildren(HeaderComponent) headers: QueryList<HeaderComponent>;
    @ContentChildren(SiderComponent) siders: QueryList<SiderComponent>;

    constructor() { }

    ngOnInit() {
    }

}
