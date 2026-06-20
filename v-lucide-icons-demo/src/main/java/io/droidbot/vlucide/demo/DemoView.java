package io.droidbot.vlucide.demo;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
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

	private enum PresetColor {
		DEFAULT("Default", ""),
		BLACK("Black", "#000000"),
		WHITE("White", "#ffffff"),
		RED("Red", "#ef4444"),
		ORANGE("Orange", "#f97316"),
		AMBER("Amber", "#f59e0b"),
		YELLOW("Yellow", "#eab308"),
		LIME("Lime", "#84cc16"),
		GREEN("Green", "#22c55e"),
		EMERALD("Emerald", "#10b981"),
		TEAL("Teal", "#14b8a6"),
		CYAN("Cyan", "#06b6d4"),
		SKY("Sky", "#0ea5e9"),
		BLUE("Blue", "#3b82f6"),
		INDIGO("Indigo", "#6366f1"),
		VIOLET("Violet", "#8b5cf6"),
		PURPLE("Purple", "#a855f7"),
		FUCHSIA("Fuchsia", "#d946ef"),
		PINK("Pink", "#ec4899"),
		ROSE("Rose", "#f43f5e");

		private final String label;
		private final String hex;

		PresetColor(String label, String hex) {
			this.label = label;
			this.hex = hex;
		}

		public String getHex() {
			return hex;
		}

		@Override
		public String toString() {
			return label;
		}
	}

	private final List<LucideSvgIcon> icons = new ArrayList<>();
	private boolean darkTheme = false;

	public DemoView() {
		setSpacing(false);
		setPadding(true);
		setWidthFull();

		add(new H2("Lucide Icons for Vaadin"));

		var controls = new HorizontalLayout();
		controls.addClassName("controls-bar");
		controls.setWidthFull();
		controls.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.BASELINE);

		var colorCombo = new ComboBox<PresetColor>("Color");
		colorCombo.setItems(PresetColor.values());
		colorCombo.setValue(PresetColor.BLACK);
		colorCombo.setWidth("140px");

		var sizeField = new TextField("Size");
		sizeField.setValue("32px");
		sizeField.setWidth("100px");

		var strokeSelect = new Select<Double>();
		strokeSelect.setLabel("Stroke");
		strokeSelect.setItems(0.5, 1.0, 1.5, 2.0, 3.0, 4.0);
		strokeSelect.setValue(2.0);

		var themeToggle = new Button("Dark Mode");
		themeToggle.addClassName("theme-toggle");
		themeToggle.addClickListener(e -> toggleTheme(themeToggle));

		controls.add(colorCombo, sizeField, strokeSelect, themeToggle);
		add(controls);

		var grid = new Div();
		grid.addClassName("icon-grid");

		for (LucideIcon icon : LucideIcon.values()) {
			var svgIcon = icon.create();
			svgIcon.setSize(sizeField.getValue());
			svgIcon.setColor(colorCombo.getValue().getHex());
			svgIcon.setStrokeWidth(strokeSelect.getValue());
			icons.add(svgIcon);

			var card = new Div();
			card.addClassName("icon-card");

			var name = new Span(formatName(icon.name()));
			name.addClassName("icon-name");

			card.add(svgIcon, name);
			grid.add(card);
		}

		add(grid);

		colorCombo.addValueChangeListener(e -> {
			var color = e.getValue().getHex();
			for (var icon : icons) {
				icon.setColor(color);
			}
		});

		sizeField.addValueChangeListener(e -> {
			var val = e.getValue();
			if (val != null && !val.isBlank()) {
				for (var icon : icons) {
					icon.setSize(val);
				}
			}
		});

		strokeSelect.addValueChangeListener(e -> {
			for (var icon : icons) {
				icon.setStrokeWidth(e.getValue());
			}
		});
	}

	private void toggleTheme(Button toggle) {
		darkTheme = !darkTheme;
		getUI().ifPresent(ui -> {
			var themeList = ui.getElement().getThemeList();
			if (darkTheme) {
				themeList.add("dark");
				toggle.setText("Light Mode");
			} else {
				themeList.remove("dark");
				toggle.setText("Dark Mode");
			}
		});
	}

	private static String formatName(String name) {
		return name.toLowerCase().replace('_', ' ');
	}
}
