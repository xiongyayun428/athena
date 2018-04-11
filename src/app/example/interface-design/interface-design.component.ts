import { Component, OnInit, ContentChild, ContentChildren, ViewChildren, ViewChild, ElementRef } from '@angular/core';

@Component({
    selector: 'a-interface-design',
    templateUrl: './interface-design.component.html',
    styleUrls: ['./interface-design.component.scss']
})
export class InterfaceDesignComponent implements OnInit {
    isLeftCollapsed = false;
    isRightCollapsed = false;

    leftSiderWidth = 200;

    @ViewChild('leftSider') leftSider: any;
    @ViewChild('left') left: ElementRef;
    @ViewChild('right') right: ElementRef;

    constructor() { }

    ngOnInit() {
    }

    mousedown(event: any) {
        const min = 100;
        document.onmousemove = (event: any) => {
            const dx = event.clientX;// 存储x轴的坐标。
            const leftX = this.left.nativeElement.offsetLeft;
            const rightX = this.right.nativeElement.offsetLeft;
            if (min < dx) {
                this.leftSiderWidth = dx - leftX;
            } else {
                this.leftSiderWidth = leftX + min;
            }
            // console.log(dx, this.leftSiderWidth);
            if (dx > rightX - 100) {
                this.leftSiderWidth = rightX - 100;
            }
        };
        document.onmouseup = function () {
            document.onmousedown = null;
            document.onmousemove = null;
        };
    }

}
