import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'a-sider-menu',
    templateUrl: './sider-menu.component.html',
    styleUrls: ['./sider-menu.component.scss']
})
export class SiderMenuComponent implements OnInit {
    @Input() values: any[];

    constructor() { }

    ngOnInit() {
    }

}
