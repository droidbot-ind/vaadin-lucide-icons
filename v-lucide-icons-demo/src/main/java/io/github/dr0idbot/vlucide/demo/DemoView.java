package io.github.dr0idbot.vlucide.demo;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.ColorScheme;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import io.github.dr0idbot.vlucide.LucideIcon;
import io.github.dr0idbot.vlucide.LucideSvgIcon;

@Route("")
public class DemoView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	private static final int PAGE_SIZE = 120;

	private final List<LucideSvgIcon> allIcons = new ArrayList<>();
	private final List<Div> allCards = new ArrayList<>();
	private final Div grid = new Div();
	private final Span pageInfo = new Span();
	private int currentPage = 0;

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
		colorCombo.setValue(PresetColor.DEFAULT);
		colorCombo.setWidth(140, Unit.PIXELS);
		colorCombo.setItemLabelGenerator(PresetColor::getDisplayName);

		colorCombo.setRenderer(new ComponentRenderer<>(color -> {

			HorizontalLayout layout = new HorizontalLayout();
			layout.addClassName("color-item");

			Span swatch = new Span();
			swatch.addClassName("color-swatch");

			if (color != PresetColor.DEFAULT) {
				swatch.getStyle().set("--color-value", color.getDisplayName());
				swatch.addClassName("color-swatch-filled");
			} else {
				swatch.addClassName("color-swatch-default");
			}

			Span label = new Span(color.getDisplayName());

			layout.add(swatch, label);
			return layout;
		}));

		var sizeField = new NumberField("Size (px)");
		sizeField.setValue(40D);
		sizeField.setWidth(100, Unit.PIXELS);

		var strokeSelect = new Select<Double>();
		strokeSelect.setLabel("Stroke");
		strokeSelect.setItems(0.5, 1.0, 1.5, 2.0, 3.0, 4.0);
		strokeSelect.setValue(1.0);

		var themeChanger = new ThemeChanger();

		var themeToggle = new Button(LucideIcon.SUN.create());
		themeToggle.addClassName("theme-toggle");
		themeToggle.addClickListener(e -> toggleTheme(themeToggle));

		var pagination = new HorizontalLayout();
		pagination.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
		pagination.getStyle().set("margin-bottom", "8px");

		var prevBtn = new Button("Previous", e -> {
			if (currentPage > 0) {
				currentPage--;
				renderPage();
			}
		});

		var nextBtn = new Button("Next", e -> {
			int totalPages = totalPages();
			if (currentPage < totalPages - 1) {
				currentPage++;
				renderPage();
			}
		});

		pagination.add(prevBtn, pageInfo, nextBtn);

		controls.add(colorCombo, sizeField, strokeSelect, pagination, themeChanger, themeToggle);
		add(controls);

		grid.addClassName("icon-grid");

		for (LucideIcon icon : LucideIcon.values()) {
			var svgIcon = icon.create();
			svgIcon.setSize("40px");
			svgIcon.setStrokeWidth(strokeSelect.getValue());
			allIcons.add(svgIcon);

			var card = new Div();
			card.addClassName("icon-card");

			var name = new Span(formatName(icon.name()));
			name.addClassName("icon-name");

			card.add(svgIcon, name);
			allCards.add(card);
		}

		add(grid);
		renderPage();

		colorCombo.addValueChangeListener(e -> {
			var hex = e.getValue().getHex();
			for (var icon : allIcons) {
				icon.setColor(hex);
			}
		});

		sizeField.addValueChangeListener(e -> {
			var val = e.getValue();
			if (val != null) {
				for (var icon : allIcons) {
					icon.setSize(val + "px");
				}
			}
		});

		strokeSelect.addValueChangeListener(e -> {
			for (var icon : allIcons) {
				icon.setStrokeWidth(e.getValue());
			}
		});
	}

	private void renderPage() {
		grid.removeAll();
		int totalPages = totalPages();
		int start = currentPage * PAGE_SIZE;
		int end = Math.min(start + PAGE_SIZE, allCards.size());

		for (int i = start; i < end; i++) {
			grid.add(allCards.get(i));
		}

		pageInfo.setText((currentPage + 1) + " / " + totalPages);
	}

	private int totalPages() {
		return (int) Math.ceil((double) allCards.size() / PAGE_SIZE);
	}

	private void toggleTheme(Button toggle) {
		getUI().ifPresent(ui -> {
			boolean darkMode = ColorScheme.Value.DARK.equals(ui.getPage().getColorScheme());
			ui.getPage().setColorScheme(darkMode ? ColorScheme.Value.LIGHT : ColorScheme.Value.DARK);
			toggle.setIcon(darkMode ? LucideIcon.SUN.create() : LucideIcon.MOON.create());
		});
	}

	private static String formatName(String name) {
		return name.toLowerCase().replace('_', ' ');
	}
}
