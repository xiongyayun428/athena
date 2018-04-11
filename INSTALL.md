
# Angular使用指南


## 软件安装
1. [JDK 1.8+](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

2. [Node.js](https://nodejs.org/en/download/current/ "请下载自己相对应的位数的版本, 使用版本6以上")
    ### 安装与配置
    1. 默认安装(可使用免安装版本)
    2. 新建全局安装文件夹与全局缓存文件夹（路径自定义）
        ```
        mkdir C:\nodejs\node_global
        mkdir C:\nodejs\node_cache
        ```
    3. 设置全局安装路径与缓存路径(注意路径)
        ```
        npm config set prefix "C:\nodejs\node_global"
        npm config set cache "C:\nodejs\node_cache"
        ```
    4. 检查是否设置成功
        npm config get prefix
        npm config get cache
    5. 新增系统变量
        ```
        变量：NODE_PATH
          值：安装路径(例如：C:\node-v9.9.0-win-x64);全局安装文件夹(例如：C:\nodejs\node_global)
        ```
    6. 编辑Path环境变量(新增)
        ```
        ;%NODE_PATH%;
        ```

3. [Git](https://git-scm.com/downloads "请下载自己相对应的位数的版本")
    ### 安装与配置
    1. 默认安装
    2. 新增环境变量(注意路径)
        ```
        变量：Git_PATH
          值: C:\Program Files\Git\cmd
        ```
    3. 编辑Path环境变量(新增)
        ```
        ;%Git_PATH%;
        ```

4. [TortoiseGit](https://tortoisegit.org/download/ "请下载自己相对应的位数的版本")

5. [Visual Studio Code](http://code.visualstudio.com/Download "请下载自己相对应的位数的版本")



## Visual Studio Code插件安装
1. Eclipse Keymap
2. TypeScript Importer
3. vscode-icons
4. TSLint
5. ESLint
6. EditorConfig for Visual Studio Code
7. Debugger for Chrome
8. Angular Files
9. Auto Close Tag
10. Auto Rename Tag
11. Npm Dependency
12. psioniq File Header


## Angular环境准备

### 全局安装typeScript、tslint、eslint、angular-cli-ghpages、yarn、rimraf、nrm、cnpm
```
npm install -g typescript tslint eslint angular-cli-ghpages yarn rimraf nrm cnpm
```

### 全局安装@Angular/cli

#### 安装
```
npm install -g @angular/cli@latest
```

#### 更新
##### Global package:
```
npm uninstall -g @angular/cli
npm cache verify
# if npm version is < 5 then use `npm cache clean`
npm install -g @angular/cli@latest
```

##### Local project package:
```
rm -rf node_modules dist # use rmdir /S/Q node_modules dist in Windows Command Prompt; use rm -r -fo node_modules,dist in Windows PowerShell
npm install --save-dev @angular/cli@latest
npm install
```

## 下载依赖包
使用cmd命令进入克隆的目录
```
npm install
```

## 运行
```
npm run serve
# or
ng serve
```
