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
    }
}