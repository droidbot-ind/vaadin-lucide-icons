package io.github.dr0idbot.vlucide;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LucideIconFactoryTest {

    @AfterEach
    void tearDown() {
        LucideIconFactory.clearCache();
    }

    @Test
    @DisplayName("create returns a non-null LucideSvgIcon")
    void create() {
        LucideSvgIcon icon = LucideIconFactory.create("save");
        assertNotNull(icon);
    }

    @Test
    @DisplayName("create throws for unknown icon")
    void createUnknown() {
        assertThrows(IllegalArgumentException.class,
                () -> LucideIconFactory.create("nonexistent-icon-xyz"));
    }

    @Test
    @DisplayName("getSvgContent returns non-empty SVG markup")
    void getSvgContent() {
        String content = LucideIconFactory.getSvgContent("save");
        assertNotNull(content);
        assertFalse(content.isEmpty());
        assertTrue(content.contains("<svg"));
        assertTrue(content.contains("</svg>"));
    }

    @Test
    @DisplayName("exists returns true for known icons")
    void exists() {
        assertTrue(LucideIconFactory.exists("save"));
        assertTrue(LucideIconFactory.exists("arrow-left"));
        assertTrue(LucideIconFactory.exists("user-round"));
    }

    @Test
    @DisplayName("exists returns false for unknown icons")
    void existsUnknown() {
        assertFalse(LucideIconFactory.exists("nonexistent-icon-xyz"));
    }

    @Test
    @DisplayName("cache caches content after first load")
    void cache() {
        assertEquals(0, LucideIconFactory.cacheSize());
        LucideIconFactory.getSvgContent("save");
        assertEquals(1, LucideIconFactory.cacheSize());
        LucideIconFactory.getSvgContent("save");
        assertEquals(1, LucideIconFactory.cacheSize());
    }

    @Test
    @DisplayName("clearCache evicts all entries")
    void clearCache() {
        LucideIconFactory.getSvgContent("save");
        LucideIconFactory.getSvgContent("arrow-left");
        assertEquals(2, LucideIconFactory.cacheSize());
        LucideIconFactory.clearCache();
        assertEquals(0, LucideIconFactory.cacheSize());
    }

    @Test
    @DisplayName("evict removes a single entry from cache")
    void evict() {
        LucideIconFactory.getSvgContent("save");
        LucideIconFactory.getSvgContent("arrow-left");
        assertEquals(2, LucideIconFactory.cacheSize());
        LucideIconFactory.evict("save");
        assertEquals(1, LucideIconFactory.cacheSize());
    }

    @Test
    @DisplayName("re-fetching after evict works")
    void evictAndReload() {
        LucideIconFactory.getSvgContent("save");
        LucideIconFactory.evict("save");
        assertEquals(0, LucideIconFactory.cacheSize());
        String content = LucideIconFactory.getSvgContent("save");
        assertNotNull(content);
        assertEquals(1, LucideIconFactory.cacheSize());
    }
}
