package io.droidbot.vlucide.demo;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import io.droidbot.vlucide.LucideIcon;
import io.droidbot.vlucide.LucideSvgIcon;

@Route("")
public class DemoView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private final List<LucideSvgIcon> icons = new ArrayList<>();

	public DemoView() {
		setSpacing(false);
		setPadding(true);
		setWidthFull();

		add(new H2("Lucide Icons for Vaadin — All Icons"));

		var controls = new HorizontalLayout();
		controls.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);
		controls.setSpacing(true);
		controls.getStyle().set("margin-bottom", "16px");

		var colorField = new TextField("Color (hex)");
		colorField.setPattern("^#[0-9a-fA-F]{6}$");
		colorField.setValue("#000000");
		colorField.setWidth("160px");

		var strokeSelect = new Select<Double>();
		strokeSelect.setLabel("Stroke Width");
		strokeSelect.setItems(0.5, 1.0, 1.5, 2.0, 3.0, 4.0);
		strokeSelect.setValue(2.0);

		var sizeSelect = new Select<String>();
		sizeSelect.setLabel("Size");
		sizeSelect.setItems("16px", "24px", "32px", "48px", "64px");
		sizeSelect.setValue("24px");

		controls.add(colorField, strokeSelect, sizeSelect);
		add(controls);

		var grid = new HorizontalLayout();
		grid.setWidthFull();
		grid.getStyle().set("flex-wrap", "wrap");
		grid.getStyle().set("gap", "4px");
		grid.getStyle().set("display", "flex");

		for (LucideIcon icon : LucideIcon.values()) {
			var svgIcon = icon.create();
			svgIcon.setSize(sizeSelect.getValue());
			svgIcon.setColor(colorField.getValue());
			svgIcon.setStrokeWidth(strokeSelect.getValue());
			icons.add(svgIcon);

			var card = new VerticalLayout();
			card.setSpacing(false);
			card.setPadding(false);
			card.setWidth("120px");
			card.setHeight("90px");
			card.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
			card.getStyle().set("border", "1px solid #e0e0e0");
			card.getStyle().set("border-radius", "8px");
			card.getStyle().set("padding", "8px");
			card.getStyle().set("box-sizing", "border-box");

			var name = new Span(formatName(icon.name()));
			name.getStyle().set("font-size", "10px");
			name.getStyle().set("text-align", "center");
			name.getStyle().set("line-height", "1.2");
			name.setWidthFull();

			card.add(svgIcon, name);
			grid.add(card);
		}

		add(grid);

		colorField.addValueChangeListener(e -> {
			var val = e.getValue();
			if (val != null && val.matches("^#[0-9a-fA-F]{6}$")) {
				for (var icon : icons) {
					icon.setColor(val);
				}
			}
		});

		strokeSelect.addValueChangeListener(e -> {
			for (var icon : icons) {
				icon.setStrokeWidth(e.getValue());
			}
		});

		sizeSelect.addValueChangeListener(e -> {
			for (var icon : icons) {
				icon.setSize(e.getValue());
			}
		});
	}

	private static String formatName(String name) {
		return name.toLowerCase().replace('_', ' ');
	}
}
