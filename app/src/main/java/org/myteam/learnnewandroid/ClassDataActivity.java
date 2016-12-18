package org.myteam.learnnewandroid;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public class ClassDataActivity extends AppCompatActivity {
    TextView tvContent, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_data);
        tvTitle = (TextView) findViewById(R.id.textview);
        tvContent = (TextView) findViewById(R.id.content);
        try {
//            printMethod(StatusBarNotification.class, 1);
            printMethod(PendingIntent.class, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void printMethod(Class cz, int ceng) throws Exception {

        if (ceng >= 4) {
            return;
        }
        String idle = "" + ceng + "    ";
        for (int i = 0; i < ceng; i++) {
            idle += "     ";
        }

        //获取类的变量成员列表，注意，这个地方还有一个getDeclaredField方法，具体区别参见javadoc
        for (java.lang.reflect.Field f : cz.getDeclaredFields()) {
            int mceng = ceng;
            f.setAccessible(true);
            //获取变量的值，当然你也可以获取变量的名字
//            Object value = f.get(obj);
            log(idle + "====" + f.getType() + "---" + f.getName());
            if (!isBaseDataType(f.getType())) {
                printMethod(f.getType(), mceng + 1);
            }
        }
    }

    private void log(String s) {
        Log.d("ClassDataActivity", s);
    }

    private boolean isBaseDataType(Class clazz) throws Exception {
        return
                (
                        clazz.equals(String.class) ||
                                clazz.equals(Integer.class) ||
                                clazz.equals(Byte.class) ||
                                clazz.equals(Long.class) ||
                                clazz.equals(Double.class) ||
                                clazz.equals(Float.class) ||
                                clazz.equals(Character.class) ||
                                clazz.equals(Short.class) ||
                                clazz.equals(BigDecimal.class) ||
                                clazz.equals(BigInteger.class) ||
                                clazz.equals(Boolean.class) ||
                                clazz.equals(Date.class) ||
                                clazz.isPrimitive()
                );
    }
}
