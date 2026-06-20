# Vaadin Lucide Icons

[![CI Build](https://github.com/dr0idbot/vaadin-lucide-icons/actions/workflows/ci-build.yml/badge.svg)](https://github.com/dr0idbot/vaadin-lucide-icons/actions/workflows/ci-build.yml)

Server-side [Lucide](https://lucide.dev) icon integration for Vaadin 24+ (Java 25).

## Tech Stack

- **Vaadin 25** — `<vaadin-icon>` web component via `SvgIcon`
- **Java 25** — Multi-module Maven project
- **Lucide** — Open-source icons (ISC license)

## Modules

| Module | Purpose |
|--------|---------|
| `v-lucide-icons` | The icon library — enum, SVG icon component, factory. What consumers depend on. |
| `v-lucide-icons-demo` | Spring Boot demo app to browse all icons. |
| `v-lucide-icons-generator` | Reads Lucide SVGs from `node_modules` and regenerates the enum + SVG resources. |

## Run After Cloning

```bash
# Build the library (no demo)
mvn -f v-lucide-icons/pom.xml install

# Run the demo app
mvn -pl v-lucide-icons-demo spring-boot:run
```

Then open `http://localhost:8080`.

## Usage

```xml
<dependency>
    <groupId>io.github.dr0idbot</groupId>
    <artifactId>v-lucide-icons</artifactId>
    <version>1.0.0</version>
</dependency>
```

```java
// Create an icon
LucideIcon.SAVE.create();

// Customize
var icon = LucideIcon.STAR.create();
icon.setColor("#ff6b00");
icon.setSize("48px");
icon.setStrokeWidth(1.5);
button.setIcon(icon);

// Accessibility
icon.setDecorative(true);                           // hide from screen readers
icon.getElement().setAttribute("aria-label", "Star"); // labelled
```

### API

| Method | Source | Description |
|--------|--------|-------------|
| `setColor(String)` | `SvgIcon` | Icon color via CSS `color` |
| `setSize(String)` | `AbstractIcon` | Width and height (e.g. `"24px"`, `"2em"`) |
| `setStrokeWidth(double)` | `LucideSvgIcon` | SVG stroke width |
| `setDecorative(boolean)` | `LucideSvgIcon` | Mark as presentational |

### Public Types

- **`LucideIcon`** — enum of all icons
- **`LucideSvgIcon`** — extends Vaadin's `SvgIcon`
- **`LucideIconFactory`** — creates and validates icon instances

## Updating Lucide Icons

When a new version of [lucide-static](https://www.npmjs.com/package/lucide-static) is published with more icons:

1. Bump the version in `v-lucide-icons-generator/package.json`
2. Run `npm install` in that directory
3. Run the generator:
   ```
   mvn -pl v-lucide-icons-generator compile exec:java
   ```
4. Verify the SVG and enum counts match
5. Commit the regenerated files
