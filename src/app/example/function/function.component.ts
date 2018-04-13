import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

/**
 * 功能点
 *
 * @export
 * @class FunctionComponent
 * @implements {OnInit}
 */
@Component({
    selector: 'a-function',
    templateUrl: './function.component.html',
    styleUrls: ['./function.component.scss']
})
export class FunctionComponent implements OnInit {
    isCollapsed = false;
    tabSelectedIndex = 0;
    siderData: any = {};

    tabs = [];

    constructor(private title: Title,
        private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient
    ) {
        title.setTitle('功能点');
        http.get('assets/data/function.model.json').subscribe((data: any) => {
            console.log(data);
            this.siderData = data;
        });
    }

    ngOnInit() {
    }

    closeTab(tab) {
        this.tabs.splice(this.tabs.indexOf(tab), 1);
    }
    openWin(tab) {
        console.log('新开窗口', tab);
        // this.router.navigate(['design', tab.name], {relativeTo: this.route});
        // this.tabs.splice(this.tabs.indexOf(tab), 1);
    }

    dblclick(item: any) {
        const index = this.tabs.findIndex(tab => {
            return tab.name == item.name;
        });
        if (index >= 0) {
            this.tabSelectedIndex = index;
            return;
        }
        this.tabs.push(item);
        this.tabSelectedIndex = this.tabs.length;
    }

}
