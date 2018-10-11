# SimpleProject
SimpleProject让开发者快速搭建一个Android项目，里面使用的技术有
 1.mvp设计模式 
2.Tinker热修复 
3.今日头条适配方案 
4.Glide图片加载 
5.Okhttp网络请求 
6.下拉刷新，上拉加载更多
如果你也打算在你的项目中使用这些技术，可以直接使用此项目作为基础框架


----------

**baselib**
-----------

baselib是一个代码库，里面的代码是与业务完全无关的，可以把他作为一个通用库，作为自己的代码基础库

----------

**gradle.properties**
---------------------

gradle.properties文件配置一些不同环境下的url或者其他字符串常量：
DEBUG环境
DEBUG_BASE_SERVER_URL=""
Release环境
RELEASE_BASE_SERVER_URL=""
然后在productFlavors下面引用即可


----------


**LaunchApplication**
LaunchApplication是Application的代理类，因为使用了Tinker热修复，具体参考：[Tinker热修复集成指南](https://github.com/Tencent/tinker/wiki/Tinker-%E6%8E%A5%E5%85%A5%E6%8C%87%E5%8D%97)
里面有一些工具的初始化，具体请自行阅读代码


----------


**适配**

屏幕适配采用的是今日头条的适配方案。
在AndroidManifest.xml文件中配置UI设计图的宽度dp值即可完成自动适配
参考：[今日头条适配方案的封装](https://github.com/JessYanCoding/AndroidAutoSize)


----------
**图片加载**

使用ImageLoaderUtils加载图片即可


----------
**多渠道打包**

多渠道打包采用的walle，渠道信息的读取请使用ChannelUtils
walle打包参考：[Walle多渠道打包Python工具](https://github.com/Jay-Goo/ProtectedApkResignerForWalle)

----------

**H5**

采用腾讯X5WebView内核，所有的交互方法写在H5Interactive类中,为了使X5Webview不那么臃肿，具体实现放在H5InvokeAndroidProxy代理类。
交互方式参考：[Android与H5安全交互方式](https://github.com/pedant/safe-java-js-webview-bridge)

----------
**Mvp设计模式**

Activity继承BaseActivity即可
Fragment继承BaseFragment即可
BaseActivity，BaseFragment放置一些公共业务，如果想封装一些UI相关的Activity，Fragment，都继承这两个
项目里面已经封装有loading相关，标题栏相关，开发者根据需要选择继承即可。
继承关系如下图：
![enter image description here](https://github.com/zhongxiong00/SimpleProject/blob/master/pics/activity.jpg)
![enter image description here](https://github.com/zhongxiong00/SimpleProject/blob/master/pics/fragment.jpg)

