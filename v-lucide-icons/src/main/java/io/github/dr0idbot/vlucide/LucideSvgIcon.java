package io.github.dr0idbot.vlucide;

import com.vaadin.flow.component.icon.SvgIcon;

/**
 * A Vaadin component that renders a Lucide SVG icon using the native
 * {@code <vaadin-icon>} web component.
 * <p>
 * Extends {@link SvgIcon} to inherit Vaadin's icon infrastructure: theming,
 * sizing, click handling, and tooltip support. SVGs are loaded via the
 * {@code src} attribute and rendered inside the web component's shadow DOM.
 * <p>
 * Supports customization of color, size, and stroke width.
 */
public class LucideSvgIcon extends SvgIcon {

	private static final long serialVersionUID = 1L;

	private static final String RESOURCE_PATH = "lucide/";
	private static final double DEFAULT_STROKE_WIDTH = 2.0;

	/**
	 * Creates a new LucideSvgIcon for the given icon name.
	 *
	 * @param iconName the Lucide icon file name (without .svg extension)
	 */
	public LucideSvgIcon(String iconName) {
		setSrc(RESOURCE_PATH + iconName + ".svg");
	}

	/**
	 * Sets the stroke width of the icon using Vaadin's native
	 * {@code --vaadin-icon-stroke-width} CSS custom property, which is applied by
	 * the {@code <vaadin-icon>} shadow DOM.
	 *
	 * @param strokeWidth the stroke width in pixels
	 */
	public void setStrokeWidth(double strokeWidth) {
		String value = strokeWidth == (int) strokeWidth ? String.valueOf((int) strokeWidth)
				: String.valueOf(strokeWidth);
		getStyle().set("--vaadin-icon-stroke-width", value);
	}

	/**
	 * Returns the current stroke width.
	 *
	 * @return the stroke width value, or 2.0 if not set
	 */
	public double getStrokeWidth() {
		String sw = getStyle().get("--vaadin-icon-stroke-width");
		if (sw == null || sw.isEmpty()) {
			return DEFAULT_STROKE_WIDTH;
		}
		return Double.parseDouble(sw);
	}

	/**
	 * Sets the icon color via the CSS {@code color} property.
	 * <p>
	 * Lucide icons are stroke-based ({@code stroke="currentColor"}), so
	 * {@code color} is used instead of {@code fill} to control the stroke color.
	 *
	 * @param color the CSS color value (e.g. {@code "#ef4444"}, {@code "red"})
	 */
	@Override
	public void setColor(String color) {
		getStyle().set("color", color);
	}

	/**
	 * Returns the current icon color.
	 *
	 * @return the CSS color value, or {@code null} if not set
	 */
	@Override
	public String getColor() {
		return getStyle().get("color");
	}

	/**
	 * Marks this icon as decorative (presentational only).
	 * <p>
	 * Decorative icons are hidden from screen readers and assistive technology. Use
	 * this for purely visual icons that don't convey meaningful information.
	 *
	 * @param decorative true to mark as decorative, false to remove
	 */
	public void setDecorative(boolean decorative) {
		if (decorative) {
			getElement().setAttribute("aria-hidden", "true");
			getElement().setAttribute("role", "presentation");
		} else {
			getElement().removeAttribute("aria-hidden");
			getElement().removeAttribute("role");
		}
	}

}
