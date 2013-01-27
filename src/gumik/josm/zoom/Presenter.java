package gumik.josm.zoom;

import gumik.josm.zoom.View.IViewListener;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.openstreetmap.josm.gui.MapView;
import org.openstreetmap.josm.gui.dialogs.LayerListDialog.LayerListModel;
import org.openstreetmap.josm.gui.layer.WMSLayer;

public class Presenter {

	Presenter(final Calculator calc, final View view, final MapView mapView,
			final LayerListModel layerListModel) {

		mapView.addPropertyChangeListener(MapView.PROPNAME_SCALE,
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent arg) {
						actualZoom = calc.getZoomFromScale((Double) arg
								.getNewValue());
						view.changeZoom(actualZoom);
					}
				});

		view.setViewListener(new IViewListener() {

			@Override
			public void changedRelative(int delta) {
				actualZoom = calc.getChangedZoomRelative(actualZoom, delta);
				changeMapViewScale(calc, mapView, layerListModel,
						(int) actualZoom);
				view.changeZoom(actualZoom);
			}

			@Override
			public void changedAbsolute(int zoom) {
				actualZoom = zoom;
				changeMapViewScale(calc, mapView, layerListModel, zoom);
			}

			@Override
			public void aligned() {
				actualZoom = calc.getChangedZoomRelative(actualZoom, 0);
				changeMapViewScale(calc, mapView, layerListModel,
						(int) actualZoom);
			}
		});
	}

	private void changeMapViewScale(final Calculator calc,
			final MapView mapView, LayerListModel layerListModel, int zoom) {
		mapView.zoomTo(mapView.getCenter(), calc.getScaleFromZoom(zoom));
		
		List<WMSLayer> layers = mapView.getLayersOfType(WMSLayer.class);
		for (WMSLayer layer : layers) {
			if (layer.isVisible()) {
				layerListModel.setSelectedLayer(layer);
				new WMSLayer.ChangeResolutionAction().actionPerformed(new ActionEvent(
						this, 0, ""));
			}
		}
	}

	private double actualZoom;
}
