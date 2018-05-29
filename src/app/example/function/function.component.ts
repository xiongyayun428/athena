import { Component, OnInit, VERSION } from '@angular/core';
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

    version = VERSION.full;

    constructor(private title: Title,
        private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient
    ) {
        title.setTitle('功能点');
        http.get('assets/data/function.model.json').subscribe((data: any) => {
            // console.log(data);
            this.siderData = data;
        });
    }

    ngOnInit() {
    }

    closeTab(tab) {
        this.tabs.splice(this.tabs.indexOf(tab), 1);
    }
    openWin(tab) {
        const encodeData = encodeURIComponent(JSON.stringify(tab));
        const url = this.router.url + '/design/' + encodeData;
        window.open(url);
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
