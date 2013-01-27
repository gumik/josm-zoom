package gumik.josm.zoom;


public class Calculator {
	public double getZoomFromScale(double scale) {
		return Math.log(MERCATOR_MAX_WIDTH / (scale * TILE_SIZE)) / Math.log(2);
	}
	
	public double getScaleFromZoom(int zoom) {
		return (MERCATOR_MAX_WIDTH / TILE_SIZE) / Math.pow(2, zoom);
	}
	
	public int getChangedZoomRelative(double zoom, int delta) {
		zoom += delta;
		zoom = delta < 0
				? Math.ceil(zoom) 
				: delta > 0 
					? Math.floor(zoom)
					: Math.round(zoom);
		zoom = Math.max(Math.min(zoom, Calculator.MAX_ZOOM), Calculator.MIN_ZOOM);
		return (int)zoom;
	}

	public static final int MAX_ZOOM = 28;
	public static final int MIN_ZOOM = 1;
	
	private static final double MERCATOR_MAX_WIDTH = 40075016.685578488d;
	private static final int TILE_SIZE = 256;
}
