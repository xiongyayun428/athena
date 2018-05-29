import { Component } from '@angular/core';

@Component({
    selector: 'a-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {
    constructor() {
        console.log("%c熊大大，欢迎您", " text-shadow: 0 0 5px #ccc,0 2px 0 #c9c9c9,0 3px 0 #bbb,0 4px 0 #b9b9b9,0 5px 0 #aaa,0 6px 1px rgba(0,0,0,.1),0 0 5px rgba(0,0,0,.1),0 1px 3px rgba(0,0,0,.3),0 3px 5px rgba(0,0,0,.2),0 5px 10px rgba(0,0,0,.25),0 10px 10px rgba(0,0,0,.2),0 20px 20px rgba(0,0,0,.15);font-size:5em");
        console.log("\n %c 有朋自远方来 不亦说乎？ email: xiongyayun428@163.com %c http://www.xiongyayun.com/ \n", "color: #FF0000; background: #4bffba; padding:5px 0; border-radius: 5px 5px 5px 5px;", "background: #fadfa3; padding:5px 0; border-radius: 5px 5px 5px 5px;");
        console.log('%c老婆给当程序员的老公打电话：下班顺路买十个包子，如果看到卖西瓜的，买一个。', 'text-shadow: 3px 1px 1px grey');
        console.info('%c当晚老公手捧一个包子进了家门。', 'text-shadow: 3px 1px 1px grey');
        console.warn('%c婆怒道：你怎么只买一个包子？！', 'text-shadow: 3px 1px 1px grey');
        console.info('%c老公甚恐，喃喃道：因为我真看到卖西瓜的了。', 'text-shadow: 3px 1px 1px grey');
        // var data = [{
        //     '技术': 'Angular',
        //     '起源': 'Google'
        // }, {
        //     '技术': 'React',
        //     '起源': 'Facebook'
        // }, {
        //     '技术': 'Vue.js',
        //     '起源': '个人'
        // }];
        // console.table(data);
        console.log('%c女朋友就是私有变量，只有我这个类才能调用！', 'background-image:-webkit-gradient( linear, left top, right top, color-stop(0, #f22), color-stop(0.15, #f2f), color-stop(0.3, #22f), color-stop(0.45, #2ff), color-stop(0.6, #2f2),color-stop(0.75, #2f2), color-stop(0.9, #ff2), color-stop(1, #f22) );color:transparent;-webkit-background-clip: text;font-size:2em;');
        console.log('程序员A：哎 太累了日子没法过了 怎么才能换行啊');
        console.log('程序员B：打回车呀！');
    }
}
