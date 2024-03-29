package com.example.map;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
 
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
 
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
 
public class MapSample extends MapActivity {
 
    /** 緯度、経度を表示するボタン。 */
    private Button mButton01;
    /** 移動ボタン。 */
    private Button mButton02;
    /** MapViewを格納する。 */
    private MapView mMap;
    /** MapControllerを格納する。 */
    private MapController mMapController;
    /** BindするContext。 */
    private Context mContext;
 
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_map_sample );
 
        // Contextを格納(Toastで使用)
        mContext = this.getApplicationContext();
        // res/layout/activity_map_sample.xmlのmapViewのを呼び出す
        mMap = (MapView) findViewById( R.id.mapView );
 
        // Mapの操作で使用
        mMapController = mMap.getController();
 
        // Mapの縮尺を設定
        mMapController.setZoom( 15 );
 
        // ズームコントローラーの表示
        mMap.setBuiltInZoomControls( true );
 
        // 緯度・経度を表示するボタン。押すと緯度経度のToastを表示する
        mButton01 = (Button) findViewById( R.id.Button01 );
 
        // Button01のイベント処理
        mButton01.setOnClickListener( new OnClickListener() {
            // Button01が押された時の処理
            @Override
            public void onClick( View v ) {
                // 地図の中心点の緯度・経度を取得
                double lat = mMap.getMapCenter().getLatitudeE6() / 1E6;
                double lon = mMap.getMapCenter().getLongitudeE6() / 1E6;
 
                // Geocoderで住所を取得
                Geocoder mGeocoder = new Geocoder( mContext, Locale.getDefault() );
 
                // 住所を格納する文字列バッファ
                StringBuffer sb = new StringBuffer();
                // 緯度、経度、取得可能な最大の住所数を指定
                List< Address > addresses;
                try {
                    addresses = mGeocoder.getFromLocation( lat, lon, 1 );
                    for ( Address address : addresses ) {
                        // 取得された住所情報のサイズでforループ
                        int maxSize = address.getMaxAddressLineIndex();
                        for ( int i = 0; i <= maxSize; i++ ) {
                            sb.append( address.getAddressLine( i ) );
                        }
                    }
                } catch ( IOException e ) {
                    // ネットワークエラーの時
                    e.printStackTrace();
                } catch ( IllegalArgumentException e ) {
                    // 緯度・経度が範囲外の値の時
                    e.printStackTrace();
                }
                Toast.makeText( mContext, "lat:" + lat + " lon:" + lon + " address:" + sb, Toast.LENGTH_LONG ).show();
            }
        } );
 
        // 移動ボタン。押すと設定の緯度経度に移動
        mButton02 = (Button) findViewById( R.id.Button02 );
 
        // Button02のイベント処理
        mButton02.setOnClickListener( new OnClickListener() {
            // Button02が押された時の処理
            @Override
            public void onClick( View v ) {
                GeoPoint point = new GeoPoint( 35709999, 139810767 ); // スカイツリー
                mMapController.animateTo( point );
            }
        } );
    }
 
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}