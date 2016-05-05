package com.hnpolice.xiaoke.downloadfile.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.hnpolice.xiaoke.downloadfile.R;
import com.hnpolice.xiaoke.downloadfile.adapter.FileListAdapter;
import com.hnpolice.xiaoke.downloadfile.bean.FileInfo;
import com.hnpolice.xiaoke.downloadfile.service.DownloadService2;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "MainActivity2";

    @InjectView(R.id.list)
    ListView list;

    private List<FileInfo> fileList;
    private FileListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.inject(this);

        initData();
        initSetup();
        initRegister();


    }

    private void initRegister() {
        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService2.ACTION_UPDATE);
        filter.addAction(DownloadService2.ACTION_FINISHED);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 添加数据
     */
    private void initData() {
        fileList = new ArrayList<>();
        FileInfo fileInfo1 = new FileInfo(0, "http://dldir1.qq.com/weixin/android/weixin6316android780.apk",
                "weixin.apk", 0, 0);
        FileInfo fileInfo2 = new FileInfo(1, "http://111.202.99.12/sqdd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk",
                "qq.apk", 0, 0);
        FileInfo fileInfo3 = new FileInfo(2, "http://www.imooc.com/mobile/imooc.apk",
                "imooc.apk", 0, 0);
        FileInfo fileInfo4 = new FileInfo(3, "http://www.imooc.com/download/Activator.exe",
                "Activator.exe", 0, 0);
        fileList.add(fileInfo1);
        fileList.add(fileInfo2);
        fileList.add(fileInfo3);
        fileList.add(fileInfo4);
    }


    private void initSetup() {
        //创建适配器
        listAdapter = new FileListAdapter(this, fileList);
        //给listview设置适配器
        list.setAdapter(listAdapter);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService2.ACTION_UPDATE.equals(intent.getAction())) {
                long finished = intent.getLongExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                Log.e(TAG, "finished==" + finished);
                Log.e(TAG, "id==" + id);
                listAdapter.updateProgress(id, finished);
                //progressBar.setProgress(finished);
            } else if (DownloadService2.ACTION_FINISHED.equals(intent.getAction())) {
                FileInfo fileinfo = (FileInfo) intent.getSerializableExtra("fileinfo");
                //更新进度为100
                listAdapter.updateProgress(fileinfo.getId(), 100);
                Toast.makeText(
                        MainActivity2.this,
                        fileinfo.getFileName() + "下载完成",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }


}
