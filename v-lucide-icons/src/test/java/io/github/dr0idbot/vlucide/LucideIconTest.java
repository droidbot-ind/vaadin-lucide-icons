package io.github.dr0idbot.vlucide;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class LucideIconTest {

    @Test
    @DisplayName("every enum constant should resolve to an existing SVG resource")
    void allSvgsExist() {
        for (LucideIcon icon : LucideIcon.values()) {
            assertTrue(LucideIconFactory.exists(icon.getIconName()),
                    "SVG not found for icon: " + icon.getIconName());
        }
    }

    @Test
    @DisplayName("getIconName returns the kebab-case name")
    void getIconName() {
        assertEquals("save", LucideIcon.SAVE.getIconName());
        assertEquals("arrow-left", LucideIcon.ARROW_LEFT.getIconName());
        assertEquals("user-round", LucideIcon.USER_ROUND.getIconName());
    }

    @Test
    @DisplayName("create returns a non-null LucideSvgIcon")
    void create() {
        LucideSvgIcon icon = LucideIcon.SAVE.create();
        assertNotNull(icon);
    }

    @Test
    @DisplayName("create returns a unique instance each time")
    void createUnique() {
        assertNotSame(LucideIcon.SAVE.create(), LucideIcon.SAVE.create());
    }

    @Test
    @DisplayName("every enum constant can create an icon")
    void allCanCreate() {
        for (LucideIcon icon : LucideIcon.values()) {
            assertNotNull(icon.create(), "Failed to create icon: " + icon.getIconName());
        }
    }
}
