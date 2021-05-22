package com.mapbox.rctmgl.components.annotation;

import android.graphics.PointF;
import android.view.View;

import androidx.annotation.NonNull;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.Projection;

/**
 * Subclass of MarkerView so we MarkerViewManager can implement remove/restoreViews
 */
public class MarkerView extends com.mapbox.mapboxsdk.plugins.markerview.MarkerView {
    View view;

    private LatLng latLng;
    private Projection projection;
    private OnPositionUpdateListener onPositionUpdateListener;

    public MarkerView(@NonNull LatLng latLng, @NonNull View view) {
        super(latLng, view);
        this.view = view;
        this.latLng = latLng;
    }

    public View getView() {
        return this.view;
    }

    public void update() {
        PointF point = projection.toScreenLocation(latLng);
        if (onPositionUpdateListener != null) {
            point = onPositionUpdateListener.onUpdate(point);
        }
        view.setX(point.x);
        view.setY(point.y);
    }

    @Override
    public void setOnPositionUpdateListener(OnPositionUpdateListener onPositionUpdateListener) {
        this.onPositionUpdateListener = onPositionUpdateListener;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }
}
