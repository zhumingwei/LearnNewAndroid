package org.myteam.learnnewandroid;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnalysisContentResolverActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_content_resolver);
        new MyThread().start();
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            List<List<Object>> plist = new ArrayList<>();
            ContentResolver contentResolver = AnalysisContentResolverActivity.this.getContentResolver();
            String queryUris = "content://com.android.launcher3.settings/favorites?notify=true";
            Uri queryUri = Uri.parse(queryUris);
            Cursor cursor = contentResolver.query(queryUri, null, null, null, null);


            log(Arrays.toString(cursor.getColumnNames()));


            while (cursor.moveToNext()) {
                List<Object> list = new ArrayList<>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    list.add(getValue(cursor, i));
                }
                plist.add(list);
            }

            for (List<Object> list : plist) {
                log(Arrays.toString(list.toArray()));
            }

            cursor.close();
        }
    }

    private Object getValue(Cursor cursor, int i) {
        switch (cursor.getType(i)) {
            case Cursor.FIELD_TYPE_NULL:
                return null;
            case Cursor.FIELD_TYPE_INTEGER:
                return cursor.getInt(i);
            case Cursor.FIELD_TYPE_FLOAT:
                return cursor.getFloat(i);
            case Cursor.FIELD_TYPE_STRING:
                return cursor.getString(i);
            case Cursor.FIELD_TYPE_BLOB:
                return cursor.getBlob(i);
        }
        return null;
    }


    private void log(String s) {
        Log.d("ContentResolver", s);
    }
}
