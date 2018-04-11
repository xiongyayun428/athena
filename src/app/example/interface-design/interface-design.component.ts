import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'ks-interface-design',
    templateUrl: './interface-design.component.html',
    styleUrls: ['./interface-design.component.scss']
})
export class InterfaceDesignComponent implements OnInit {
    isCollapsed = true;

    constructor() { }

    ngOnInit() {
    }

}
