import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { DragulaService } from 'ng2-dragula';

@Component({
    selector: 'a-sider-menu',
    templateUrl: './sider-menu.component.html',
    styleUrls: ['./sider-menu.component.scss']
})
export class SiderMenuComponent implements OnInit {
    @Input() values: any;
    @Input() draggable: boolean;
    @Input() isCollapsed: boolean;

    @Output() onclick = new EventEmitter<any>();
    @Output() ondblclick = new EventEmitter<any>();

    constructor(private dragulaService: DragulaService) {
        dragulaService.drag.subscribe((value) => {
            console.log(`drag: ${value[0]}`, value);
            // console.log(`drag: ${value[0].name}`, value);
        });
        dragulaService.drop.subscribe((value) => {
            console.log(`drop: ${value[0]}`, value);
            // console.log(`drop: ${value[0].name}`, value);
        });
        dragulaService.over.subscribe((value) => {
            console.log(`over: ${value[0]}`, value);
            // console.log(`over: ${value[0].name}`, value);
        });
        dragulaService.out.subscribe((value) => {
            console.log(`out: ${value[0]}`, value);
            // console.log(`out: ${value[0].name}`, value);
        });
    }

    ngOnInit() {
    }


    click(event: any) {
        this.onclick.emit(event);
    }
    dblclick(event: any) {
        this.ondblclick.emit(event);
    }

}
