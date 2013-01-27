package gumik.josm.zoom;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.gui.dialogs.LayerListDialog;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;

public class ZoomPlugin extends Plugin {

	public ZoomPlugin(PluginInformation info) {
		super(info);
	}

	@Override
	public void mapFrameInitialized(MapFrame oldFrame, MapFrame newFrame) {
		if (oldFrame == null && newFrame != null) {
			Calculator calc = new Calculator();
			View view = new View(calc.getZoomFromScale(Main.getProjection().getDefaultZoomInPPD()), Calculator.MIN_ZOOM, Calculator.MAX_ZOOM);
			new Presenter(calc, view, Main.map.mapView, LayerListDialog.getInstance().getModel());
			newFrame.addToggleDialog(view);
		}
	}
}

