package com.mapbox.rctmgl.components.annotation;

import android.graphics.PointF;

import androidx.annotation.NonNull;

import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.rctmgl.components.annotation.MarkerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Subclass of MarkerViewManager implementing removeViews and restoreViews
 */
public class MarkerViewManager extends com.mapbox.mapboxsdk.plugins.markerview.MarkerViewManager {
    private final List<MarkerView> markers = new ArrayList<>();
    private MapView mapView;
    private MapboxMap mapboxMap;

    public MarkerViewManager(MapView mapView, MapboxMap mapboxMap) {
        super(mapView, mapboxMap);
        this.mapView = mapView;
        this.mapboxMap = mapboxMap;
        this.mapboxMap.addOnCameraMoveListener(new MapboxMap.OnCameraMoveListener() {
             @Override
             public void onCameraMove() {
                 update();
             }
         });
    }

    public void addMarker(@NonNull MarkerView markerView) {
        super.addMarker(markerView);
        markers.add(markerView);
        markerView.setProjection(mapboxMap.getProjection());
    }

    public void removeMarker(@NonNull MarkerView markerView) {
        super.removeMarker(markerView);
        markers.remove(markerView);
    }

    public void removeViews() {
        for (MarkerView marker: markers) {
            mapView.removeView(marker.getView());
        }
    }

    public void restoreViews() {
        for (MarkerView marker: markers) {
            mapView.addView(marker.getView());
        }
    }

    private void update() {
        for (MarkerView marker : markers) {
            marker.update();
        }
    }
}