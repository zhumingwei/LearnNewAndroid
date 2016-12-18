package org.myteam.learnnewandroid.customscoller;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.myteam.learnnewandroid.R;

import java.util.Arrays;
import java.util.List;

public class GetApplicationData extends AppCompatActivity {
    TextView textview;
    String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_application_data);
        textview = (TextView) findViewById(R.id.textview);
        new Thread(){
            @Override
            public void run() {
                super.run();
                check();
            }
        }.start();
    }

    String TAG = "check";

    private void check() {
        List<ApplicationInfo> list1 = getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES);
        if (list1.size() != 0) {
            for (ApplicationInfo info : list1) {
                // 配置文件中的android:backupAgent属性值,用于备份
                String backupAgentName = info.backupAgentName;
                log("backupAgentName： " + backupAgentName);
                // 获取继承Application类的对象,维护全局的Application状态
                //但一般都不用继承的方式，可以通过Context.getApplicationContext()方法得到
                String className = info.className;
                log("className: " + className);
                // 包名
                String packageName = info.packageName;
                log("packageName： " + packageName);
                // <android:manageSpaceActivity>标签的值
                //该标签代表了指定一个activity来处理用户的空间数据。而不是通过设置里的一个按钮来操作
                String activityName = info.manageSpaceActivityName;
                log("activityName： " + activityName);
                // 应用所在的进程
                String processName = info.processName;
                log("processName： " + processName);
                // 权限
                String permisson = info.permission;
                log("permisson: " + permisson);
                // 创建对象时，传入的是GET_SHARED_LIBRARY_FILES该属性才有值
                String[] files = info.sharedLibraryFiles;
                log("files: " + Arrays.toString(files));
                // 存放数据的路径
                String dataPath = info.dataDir;
                log("dataPath: " + dataPath);
                // 本地路径
                String nativePath = info.nativeLibraryDir;
                log("nativePath：" + nativePath);
                // 公共资源路径
                String punlicSourcePath = info.publicSourceDir;
                log("punlicSourcePath： " + punlicSourcePath);
                // 资源路径
                String sourcePath = info.sourceDir;
                log("sourcePath: " + sourcePath);
                // 内同的activity的task名称
                String taskAffinity = info.taskAffinity;
                log("taskAffinity： " + taskAffinity);
                // 如果是false,代表application里的所有组件都禁用
                boolean enable = info.enabled;
                log("enable: " + enable);
                // 表述资源文件的标识
                int descriRes = info.descriptionRes;
                log("descriRes: " + descriRes);
                int flag = info.flags;
                log("flag： " + flag);
                // 指定smallest screen width的值，超过这个值，就要开启屏幕兼容
                int compatibleWidth = info.compatibleWidthLimitDp;//android:compatibleWidthLimitDp属性
                log("compatibleWidth: " + compatibleWidth);
                // 同上，只是这时候用户无法禁止屏幕兼容模式，说明是强制启动屏幕兼容
                int largestWidth = info.largestWidthLimitDp;//android:largestWidthLimitDp属性
                log("largestWidth: " + largestWidth);
                // 所需屏幕空间的最短尺寸,
                int samllestWidth = info.requiresSmallestWidthDp;//android:requiresSmallestWidthDp属性
                log("samllestWidth: " + samllestWidth);
                // 应用所需的最小sdk版本
                int sdkVersion = info.targetSdkVersion;
                log("sdkVersion： " + sdkVersion);
                int theme = info.theme;
                log("theme: " + theme);//android:theme=
                int uid = info.uid;
                log("uid: " + uid);
                // 配置文件中的uiOptions属性的值
                int uiOptions = info.uiOptions;
                log("uiOptions： " + uiOptions);
                log("=======================================");
            }
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                textview.setText(result);
            }
        });

    }

    private void add(String s) {
        result = result + s + "\n";
    }
    Handler handler = new Handler();
    private void log(String s) {
        Log.d(TAG, s);
        final String finalS = s;
        add(finalS);
    }

}
