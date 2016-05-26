package com.yao.feicui.asynctaskdemo;

import android.app.Activity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
    private static final String TAG = "ASYNC_TASK";
    private Button execute;
    private Button cancel;
    private ProgressBar progressbar;
    private TextView textview;
    private MyTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        execute = (Button) findViewById(R.id.execute);
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注意每次需new一个实例,新建的任务只能执行一次,否则会出现异常
                mTask = new MyTask();
                mTask.execute();
                execute.setEnabled(false);
                cancel.setEnabled(true);

            }
        });
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消一个正在执行的任务,onCancelled方法将会被调用
                mTask.cancel(true);
            }
        });
        progressbar = (ProgressBar) findViewById(R.id.progress_bar);
        textview = (TextView) findViewById(R.id.text_view);
    }

     class MyTask extends AsyncTask<String, Integer, String> {
        String str = "";

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute() called");
            textview.setText("loading...");
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                    for (int i = 0; i < 101; i++) {
                        this.publishProgress(i);
                        Thread.sleep(200);
                    }

                return str.toString();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "onProgressUpdate(Progress progress) called");
            progressbar.setProgress(values[0]);
            textview.setText("loading..." + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "onPostExecute(Result result) called");
            textview.setText(s);
            execute.setEnabled(true);
            cancel.setEnabled(false);
        }

        @Override
        protected void onCancelled() {
            Log.i(TAG, "onCancelled() called");
            textview.setText("called");
            progressbar.setProgress(0);

            execute.setEnabled(true);
            cancel.setEnabled(false);
        }
    }
}
