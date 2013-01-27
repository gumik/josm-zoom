package gumik.josm.zoom;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.openstreetmap.josm.gui.SideButton;
import org.openstreetmap.josm.gui.dialogs.ToggleDialog;

class View extends ToggleDialog {

	public interface IViewListener {
		public void changedAbsolute(int zoom);
		public void changedRelative(int delta);
		public void aligned();
	}

	public View(double initialZoom, int minZoom, int maxZoom) {
		super("Zoom", "zoom-icon", "", null, 32, true);

		int roundedZoom = (int) Math.round(initialZoom);
		initSlider(roundedZoom, minZoom, maxZoom);
		initDecreaseBtn();
		initIncreaseBtn();
		initAlignBtn();
		initLabel(roundedZoom);

		FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
		JPanel panel = new JPanel(layout);
		panel.add(alignBtn);
		panel.add(decreaseBtn);
		panel.add(increaseBtn);
		panel.add(slider);
		panel.add(label);

		createLayout(panel, false, new Vector<SideButton>());
	}

	public void setViewListener(IViewListener listener) {
		this.listener = listener;
	}

	public void changeZoom(double newZoom) {
		slider.removeChangeListener(sliderChangeListener);
		slider.setValue((int) Math.round(newZoom));
		slider.addChangeListener(sliderChangeListener);
		setLabelText(newZoom);
	}

	private void setLabelText(double zoom) {
		DecimalFormat format = new DecimalFormat("#.##");
		label.setText(format.format(zoom));
	}

	private void initSlider(int initialZoom, int minZoom, int maxZoom) {
		sliderChangeListener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				fireChangedAbsolute(slider.getValue());
			}
		};

		slider = new JSlider(minZoom, maxZoom, initialZoom);
		slider.addChangeListener(sliderChangeListener);
	}

	private void initDecreaseBtn() {
		decreaseBtn = new JButton("-");
		decreaseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireChangedRelative(-1);
			}
		});
	}

	private void initIncreaseBtn() {
		increaseBtn = new JButton("+");
		increaseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireChangedRelative(+1);
			}
		});
	}

	private void initAlignBtn() {
		alignBtn = new JButton("o");
		alignBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fireAligned();
			}
		});
	}

	private void initLabel(int initialZoom) {
		label = new JLabel();
		setLabelText(initialZoom);
	}

	private void fireChangedAbsolute(int zoom) {
		if (listener != null) {
			listener.changedAbsolute(zoom);
		}
	}
	
	private void fireChangedRelative(int delta) {
		if (listener != null) {
			listener.changedRelative(delta);
		}
	}
	
	private void fireAligned() {
		if (listener != null) {
			listener.aligned();
		}
	}

	private JButton alignBtn;
	private JButton decreaseBtn;
	private JButton increaseBtn;
	private JLabel label;
	private JSlider slider;
	private ChangeListener sliderChangeListener;

	private IViewListener listener;

	private static final long serialVersionUID = 7035559813101065141L;
}
