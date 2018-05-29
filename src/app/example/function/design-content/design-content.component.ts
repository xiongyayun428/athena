import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'a-design-content',
    templateUrl: './design-content.component.html',
    styleUrls: ['./design-content.component.scss']
})
export class DesignContentComponent implements OnInit {

    constructor() { }

    ngOnInit() {
    }

    attributes(event: any) {
        console.log(event);
    }

}
