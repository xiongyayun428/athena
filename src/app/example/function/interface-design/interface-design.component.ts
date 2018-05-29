import { Component, OnInit, ContentChild, ContentChildren, ViewChildren, ViewChild, ElementRef, Input } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Params } from '@angular/router';
import { Subscription, Observable } from 'rxjs/Rx';

@Component({
    selector: 'a-interface-design',
    templateUrl: './interface-design.component.html',
    styleUrls: ['./interface-design.component.scss']
})
export class InterfaceDesignComponent implements OnInit {
    @Input() set values(data: any) {
        this.title.setTitle('功能点界面设计 - ' + data.name);
    }
    isLeftCollapsed = false;
    isRightCollapsed = false;

    leftSiderWidth = 200;

    controlsData: any;
    attributesData: any;

    @ViewChild('leftSider') leftSider: any;
    @ViewChild('left') left: ElementRef;
    @ViewChild('right') right: ElementRef;

    constructor(private title: Title, private route: ActivatedRoute, private http: HttpClient) {
        title.setTitle('功能点界面设计');
        http.get('assets/data/controls.model.json').subscribe((data: any) => {
            this.controlsData = data;
        });
        http.get('assets/data/attributes.model.json').subscribe((data: any) => {
            this.attributesData = data;
        });
    }

    ngOnInit() {
        this.route.params.switchMap((params: Params) => {
            if (params['name']) {
                const data = JSON.parse(decodeURIComponent(params['name']));
                console.log(data);
                this.title.setTitle('功能点界面设计 - ' + data.name);
            }
            return Observable.of();
        }).subscribe();
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
