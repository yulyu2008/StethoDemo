package debugapp;


import com.facebook.stetho.Stetho;
import com.xiayu.stethodemo.XiayuApplication;

/**
 * 创建者     罗夏雨
 * 创建时间   2016/12/22 19:57
 * 描述	      用于debug版本调用
 */
public class MyDebugApplication extends XiayuApplication{
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
}