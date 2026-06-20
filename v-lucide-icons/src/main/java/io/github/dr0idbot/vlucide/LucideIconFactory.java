package io.github.dr0idbot.vlucide;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Factory for creating {@link LucideSvgIcon} instances.
 * <p>
 * Icon components created by this factory load SVG content via the
 * {@code <vaadin-icon>} web component's {@code src} attribute.
 * SVG resources are served as static files from the classpath
 * ({@code META-INF/resources/lucide/}) and fetched by the browser.
 * <p>
 * The factory also provides optional in-memory caching of SVG
 * content for validation, testing, or metadata access.
 */
public final class LucideIconFactory {

    private static final String RESOURCE_BASE = "META-INF/resources/lucide/";
    private static final Map<String, String> SVG_CACHE = new ConcurrentHashMap<>();

    private LucideIconFactory() {
    }

    /**
     * Creates a {@link LucideSvgIcon} for the given icon name.
     * <p>
     * The icon is rendered via the native {@code <vaadin-icon>} web
     * component, which loads the SVG file from the classpath resource
     * URL {@code lucide/<iconName>.svg}.
     *
     * @param iconName the icon file name (without {@code .svg} extension)
     * @return a new icon component backed by the vaadin-icon web component
     * @throws IllegalArgumentException if the SVG resource cannot be found
     */
    public static LucideSvgIcon create(String iconName) {
        if (!exists(iconName)) {
            throw new IllegalArgumentException(
                    "Lucide icon not found: " + iconName +
                    " (expected at " + RESOURCE_BASE + iconName + ".svg)");
        }
        return new LucideSvgIcon(iconName);
    }

    /**
     * Returns the SVG content for the given icon name, loading and
     * caching it from the classpath if not yet cached.
     * <p>
     * The cached content is the raw SVG markup as served to the browser.
     *
     * @param iconName the icon file name
     * @return the raw SVG markup
     * @throws IllegalArgumentException if the resource cannot be found
     * @throws RuntimeException         if reading the resource fails
     */
    public static String getSvgContent(String iconName) {
        return SVG_CACHE.computeIfAbsent(iconName, LucideIconFactory::loadSvg);
    }

    /**
     * Checks whether an icon's SVG resource exists on the classpath.
     *
     * @param iconName the icon file name
     * @return true if the resource exists at {@code META-INF/resources/lucide/<name>.svg}
     */
    public static boolean exists(String iconName) {
        String path = RESOURCE_BASE + iconName + ".svg";
        return LucideIconFactory.class.getClassLoader().getResource(path) != null;
    }

    /**
     * Returns the number of cached SVG resources.
     *
     * @return cache size
     */
    public static int cacheSize() {
        return SVG_CACHE.size();
    }

    /**
     * Clears the internal SVG content cache.
     */
    public static void clearCache() {
        SVG_CACHE.clear();
    }

    /**
     * Evicts a single entry from the cache.
     *
     * @param iconName the icon name to evict
     */
    public static void evict(String iconName) {
        SVG_CACHE.remove(iconName);
    }

    private static String loadSvg(String iconName) {
        String path = RESOURCE_BASE + iconName + ".svg";
        ClassLoader cl = LucideIconFactory.class.getClassLoader();
        try (InputStream is = cl.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalArgumentException(
                        "Lucide icon not found: " + iconName + " (expected at " + path + ")");
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Lucide icon: " + iconName, e);
        }
    }
}
