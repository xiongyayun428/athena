import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'a-sider-submenu',
    templateUrl: './sider-submenu.component.html',
    styleUrls: ['./sider-submenu.component.scss']
})
export class SiderSubmenuComponent implements OnInit {
    @Input() values: any[];

    constructor() { }

    ngOnInit() {
    }

}
