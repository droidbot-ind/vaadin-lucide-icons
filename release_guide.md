# Release Guide — `v-lucide-icons`

This guide describes how to release the `v-lucide-icons` module to Maven Central.

## Prerequisites

- GPG signing key published to a public keyserver
- `~/.m2/settings.xml` with credentials for:
  - `ossrh` (Sonatype OSSRH) — username/password
  - `central` (Central Portal) — token-based auth
- Project checked out on `master` branch

## Current State

Release-prep changes (groupId `io.github.dr0idbot`, package rename, explicit version) are already on `master`. The pre-merge master is tagged `v-master-1.0.0-SNAPSHOT`.

## Steps

### 1. Verify the Branch State

Ensure the following are set on `master`:

| File | Expected |
|------|----------|
| Root `pom.xml` | `<version>1.0.0-SNAPSHOT</version>` |
| `v-lucide-icons/pom.xml` | `<version>1.0.0</version>` (explicit) |
| `v-lucide-icons-demo/pom.xml` | No explicit `<version>` (inherits SNAPSHOT) |
| `v-lucide-icons-generator/pom.xml` | No explicit `<version>` (inherits SNAPSHOT) |
| All POMs | `<groupId>io.github.dr0idbot</groupId>` |
| All Java files | `package io.github.dr0idbot.vlucide.*` |

### 2. Run a Full Build

```bash
mvn clean install -pl v-lucide-icons
```

Make sure tests pass and the JAR is produced.

### 3. Deploy to Maven Central

```bash
mvn deploy -pl v-lucide-icons -P release
```

This will:
- Sign artifacts with GPG
- Upload to Sonatype OSSRH
- Auto-publish to Maven Central

### 4. Tag the Release

```bash
git tag -a v-lucide-icons-1.0.0 -m "Release v-lucide-icons 1.0.0"
git push origin v-lucide-icons-1.0.0
```

### 5. Bump Version for Next Iteration

In `v-lucide-icons/pom.xml`, bump the explicit version:

```xml
<version>1.0.1-SNAPSHOT</version>
```

Then commit and push to `master`:

```bash
git commit -am "Bump v-lucide-icons to 1.0.1-SNAPSHOT"
git push origin master
```

## Rollback

If a release fails mid-way:

```bash
# Drop the staging repository from Sonatype UI
# Then fix the issue and re-deploy
```

## Verification

- Check [Maven Central](https://central.sonatype.com/artifact/io.github.dr0idbot/v-lucide-icons)
- Add the dependency to a test project and confirm it resolves
