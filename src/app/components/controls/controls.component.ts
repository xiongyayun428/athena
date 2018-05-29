import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
    selector: 'a-controls',
    templateUrl: './controls.component.html',
    styleUrls: ['./controls.component.scss']
})
export class ControlsComponent implements OnInit {

    @Output() attributes = new EventEmitter<ControlsComponent>();

    constructor() { }

    ngOnInit() {
    }

}
