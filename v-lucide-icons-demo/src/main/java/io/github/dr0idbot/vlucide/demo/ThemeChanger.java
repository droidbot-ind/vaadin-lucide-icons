package io.github.dr0idbot.vlucide.demo;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.shared.Registration;

public class ThemeChanger extends Select<String> {

	private static final long serialVersionUID = 1L;

	public ThemeChanger() {
		setLabel("Select Theme");
		setItems("style.css", "cvd-style.css", "compact-style.css");
		setValue("style.css");

		addValueChangeListener(
				event -> switchTheme(event.getSource().getUI().orElse(UI.getCurrent()), event.getValue()));
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		UI ui = attachEvent.getUI();
		Registration registration = ComponentUtil.getData(ui, Registration.class);
		// set default-style if no registration found
		if (registration != null) {
			switchTheme(ui, "default-style.css");
		}
	}

	/**
	 * Switch the current theme style sheet for a new one.
	 *
	 * @param ui         the ui in use, not {@code null}
	 * @param styleSheet new style to replace current with
	 */
	protected void switchTheme(UI ui, String styleSheet) {
		// Get previous style registration
		Registration registration = ComponentUtil.getData(ui, Registration.class);
		// If previous available remove style sheet
		if (registration != null) {
			registration.remove();
		}

		// Add the new style sheet
		registration = ui.getPage().addStyleSheet(styleSheet);

		// Store registration to remove style on later change
		ComponentUtil.setData(ui, Registration.class, registration);
	}
}