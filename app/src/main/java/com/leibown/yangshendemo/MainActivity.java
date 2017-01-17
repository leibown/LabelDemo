package com.leibown.yangshendemo;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private DemoView demoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demoView = (DemoView) findViewById(R.id.demoView);
        demoView.setColumnNum(3);
        String[] s = {"1", "1", "1", "1", "1", "1", "1","1","1"};
        demoView.setData(s);
    }
}
