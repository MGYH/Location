package com.mgyh.npl;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class BaiduActivity extends AppCompatActivity {
    private static final String TAG = "BaiduActivity";
    private TextView log;
    private BDAbstractLocationListener myListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = bdLocation.getLatitude();    //获取纬度信息
            double longitude = bdLocation.getLongitude();    //获取经度信息
            double altitude = bdLocation.getAltitude();     //获取高度信息

            Log.d(TAG, "latitude: " + latitude + ", longitude: " + longitude + ", altitude: " + altitude);
            float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
            Log.d(TAG, "定位精度: " + radius);
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = bdLocation.getCoorType();

            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            int errorCode = bdLocation.getLocType();
            String s = log.getText().toString() + "//n" + bdLocation.getLocTypeDescription();
            log.setText(s);
            Log.d(TAG, "状态: " + bdLocation.getLocTypeDescription());
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu);
        log = findViewById(R.id.log_view);
        LocationClient client = new LocationClient(this);
        LocationClientOption options = new LocationClientOption();
        options.setIsNeedAltitude(true);
        requestPermissions(new String[] {Manifest.permission.ACCESS_BACKGROUND_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},10086);
        /**
         * Hight_Accuracy GPS和网络定位
         * Battery_Saving 网络定位
         * Device_Sensors GPS定位
         */
        options.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        //声明LocationClient类
        client.registerLocationListener(myListener);
        log.setText("定位开始");
        client.start();
    }
}