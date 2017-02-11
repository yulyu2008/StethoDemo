#前言:
**在我们平时开发中,经常会用到网络请求,sharepreference或者是数据库,但是要做调试的时候却很麻烦.网络请求需要用抓包,数据库调试起来就更麻烦了,需要root手机,并且频繁的打开DDMS导出数据库,然后才能打开.所以facebook开源了一款工具,可以通过Chrome对安卓程序进行一系列的调试**

###[项目DEMO源码](https://github.com/yulyu2008/StethoDemo)
###https://github.com/yulyu2008/StethoDemo
#1.简单使用
##1.1添加grade

**compile 'com.facebook.stetho:stetho:1.1.1'**

只有stetho库是必须的，想查看网络请求的话，需要使用下面的两个库之一（看你的网络库用的是okhttp还是urlconnection）

compile 'com.facebook.stetho:stetho-okhttp3:1.3.1'
或者
compile 'com.facebook.stetho:stetho-urlconnection:1.3.1'

##1.2初始化

	public class XiayuApplication extends Application {
	    @Override
	    public void onCreate() {
	        super.onCreate();
	        Stetho.initialize(
	                Stetho.newInitializerBuilder(this)
	                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
	                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
	                        .build());
	        
		        //注意:下面这段话是为okhttpclient配置拦截器,只有用这个okhttpclient请求数据才能被拦截
		        //目前这个demo没有使用网络请求,所以我这里就注释掉了                
	/*OkHttpClient client = new OkHttpClient.Builder()
	             .addNetworkInterceptor(new StethoInterceptor())
	             .build();*/
		    }

##1.3模拟网络请求代码,数据库使用等代码
**这里指贴出SharedPreferences模拟代码**
 
	public class MainActivity extends Activity {
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        SharedPreferences sp = getSharedPreferences("config", 0);
	        SharedPreferences.Editor edit = sp.edit();
	        edit.putString("name", "xiayu");
	        edit.putBoolean("handsome?", true);
	        edit.commit();
	    }
	}
 
##1.4使用Stetho进入调试
**在Chrome浏览器中输入 chrome://inspect/#devices**

**在这里能够看到你的项目,点击inspect进入调试**

![这里写图片描述](http://img.blog.csdn.net/20170211104057830)

**在Resources这里能够看到SharedPreferences以及数据库中存储的数据(数据库在Web SQL里面)**

![这里写图片描述](http://img.blog.csdn.net/20170211104312925)

**在Network这里能够看到http请求(这个demo里面没有调用http请求,所以看不到拦截)**

![这里写图片描述](http://img.blog.csdn.net/20170211105111437)


#2.高级使用(debug包中能够调试,release包中不允许调试)

**如果只是按照上面的配置,那么无论是在debug包中,还是在正式发布的release包中都能够调试,那么会把调试功能都暴露在正式版本中,所以接下来是介绍如何只在debug版本中开启调试**

##2.1跟换gradle配置
**将compile换成debugCompile ,这样在debug版本中才会依赖这些框架 **

	debugCompile 'com.facebook.stetho:stetho:1.1.1'
    debugCompile 'com.facebook.stetho:stetho-okhttp3:1.3.1'

##2.2更换初始化的配置
**在src目录下新建一个debug目录,并创建一个debug的Application和AndroidManifest**

**debug的Application继承项目中的Application,并对stetho做初始化**

![这里写图片描述](http://img.blog.csdn.net/20170211135954219)

	public class MyDebugApplication extends XiayuApplication{
		    @Override
		    public void onCreate() {
		        super.onCreate();
		        Stetho.initialize(
		                Stetho.newInitializerBuilder(this)
		                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
		                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
		                        .build());
		    }
		}

**debug的AndroidManifest配置如下**

![这里写图片描述](http://img.blog.csdn.net/20170211140242139)

	<?xml version="1.0" encoding="utf-8"?>
	<manifest package="com.xiayu.stethodemo"
	          xmlns:android="http://schemas.android.com/apk/res/android"
	          xmlns:tools="http://schemas.android.com/tools">
	
	
	    <application
	        android:name="debugapp.MyDebugApplication"
	        tools:replace="android:name"/>
	
	</manifest>


**这样就大功告成了, 以后调试就变得 so easy了**


#3.[项目DEMO源码](https://github.com/yulyu2008/StethoDemo)
https://github.com/yulyu2008/StethoDemo