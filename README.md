# Vaadin Lucide Icons — Tools

This repository contains supporting tools for the [v-lucide-icons](https://github.com/dr0idbot/v-lucide-icons) library — a server-side [Lucide](https://lucide.dev) icon integration for Vaadin.

## Modules

| Module | Purpose |
|--------|---------|
| `v-lucide-icons-demo` | Spring Boot demo app to browse all icons from the released library. |
| `v-lucide-icons-generator` | Reads Lucide SVGs from `node_modules` and regenerates the enum + SVG resources for the library. |

## Quick Start

```bash
# Run the demo app
mvn -pl v-lucide-icons-demo spring-boot:run
```

Then open `http://localhost:8080`.

## Regenerating Icons

When a new version of [lucide-static](https://www.npmjs.com/package/lucide-static) is published with more icons:

1. Bump the version in `v-lucide-icons-generator/package.json`
2. Run `npm install` in that directory
3. Run the generator:
   ```
   mvn -pl v-lucide-icons-generator compile exec:java
   ```
4. Copy the generated files to the standalone project:
   ```bash
   cp -r generated/resources/* ../v-lucide-icons/src/main/resources/
   cp -r generated/java/* ../v-lucide-icons/src/main/java/
   ```
5. Verify the SVG and enum counts match
6. Commit the regenerated files in the standalone project
