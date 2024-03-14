# Eclipse plugin template

## Project configuration files

see: http://ant4eclipse.org/book/export/html/13/

### `.project` file

Java Development Tools (JDT) projects contain usually at least a

- `org.eclipse.jdt.core.javabuilder` builder

that is responsible for (incrementally) building the project and have the

- `org.eclipse.jdt.core.javanature` nature:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<projectDescription>
    <name>Eclipse plugin template</name>
    <projects>
    </projects>
    <buildSpec>
        <buildCommand>
            <name>org.eclipse.jdt.core.javabuilder</name>
            <arguments>
            </arguments>
        </buildCommand>
    </buildSpec>
    <natures>
        <nature>org.eclipse.jdt.core.javanature</nature>
    </natures>
</projectDescription>
```

## Target platform setup

Before starting development, a target platform setup is needed. The target
is the set of plug-ins on which the application is based, that is, everything
except the plugin under development.

To debug against Capella 6.0.0 we define an according debug target:

```lua
dap.configurations.java = {
    {
        name = "Eclipse plugin template",
        request = "launch",
        type = "java",
        javaExec = "java",
        args =
        "-product org.polarsys.capella.rcp.product -launcher /opt/capella_6.0.0/eclipse -name Eclipse -data /mnt/volume/data/workspaces/RUNTIME --add-modules=ALL-SYSTEM -os linux -ws gtk -arch aarch64 -nl en_US -consoleLog",
        vmArgs =
        "-XX:+ShowCodeDetailsInExceptionMessages -Dorg.eclipse.swt.graphics.Resource.reportNonDisposed=true -Declipse.pde.launch=true -Dfile.encoding=UTF-8",
        classPaths = {
            "/opt/capella_6.0.0/plugins/org.eclipse.equinox.launcher_1.6.200.v20210416-2027.jar",
        },
        mainClass = "org.eclipse.equinox.launcher.Main",
    }
```

### `.classpath` file

Source code completion, searching, auto imports, all rely on a properly
configured classpath.

Also the build and debug classpath is resolved from the `.classpath` definition
file.

Beside the source code of the current development it is possible to add
classpath libraries to the classpath. One classpath library can be the so
called Execution Environment container (kind `con`).

Libraries (`.jar`) files can also be referenced with absolute and/ or relative
paths:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<classpath>
    <classpathentry kind="src" path="src" including="**/*.java" />
    <classpathentry kind="output" path="target/classes" />
    <classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER" />
    <classpathentry kind="lib" path="lib/commons-beanutils-1.8.3.jar"
      sourcepath="lib/commons-beanutils-1.8.3-sources.jar"/>
</classpath>
```

One can add `.jar` files and directories to the project classpath.

Those elements can be part of the project or reside outside of the project.
This kind (`lib`) of classpath entry is very similar to a classpath entry that
one can specify when using the `-classpath` argument with java or javac.

### `build.properties` file

Referenced by `mvn clean verify|compile|install` to pack the `.jar` file.

## Configuration files for Eclipse plug-ins

### `MANIFEST.MF`

- Must be shipped with `.jar` file
- Contains the OSGi configuration information.
- Is considered by `mvn dependency:tree` to resolve dependencies.

An Eclipse plug-in defines its meta data, like its unique identifier, its
exported API and its dependencies via the MANIFEST.MF file.

### `plugin.xml`

- Must be shipped with `.jar` file
- Contains information about Eclipse specific extension mechanisms
  (extension points and the extension definitions).

## Dependency management

To get all (including transitional) dependencies listed in the `MANIFEST.MF`
and `pom.xml` file:

```bash
mvn dependency:tree
```

## Questions

### Depedenency management: How do I see 2nd level dependencies (dependency of my dependency)?

Example: `Jackson Databind`

see https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind


```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.5.1</version>
</dependency>
```

### Depedenency management: How should I refer to my local `~/.m2/repository/` libs?

-> `.classpath` file

-> `MANIFEST.MF` file

### Depedenency management: How to sync `pom.xml` and `.classpath`?

One can list dependencies in the `pom.xml` file and get them downloaded from
the [MvnRepository](https://mvnrepository.com/). Needed external dependencies
can have further dependencies, which are resolved by Maven.

Next command will download all the dependencies needed to build the project,
including plugins and their dependencies, without actually building the
project:

```bash
mvn dependency:go-offline
```

Is there a know Maven/ Tychon plugin to get the dependencies easily into the
Eclipse `.classpath` and `MANIFEST.MF` files?

Has Nicolas ever seen the goal `build-classpath` for the Maven plugin
`dependency`?

`mvn dependency:build-classpath -Dmdep.outputFile=cp.txt`?

### Target platform: As debug target

Can I define a target platform that will be understood by the JDT Language
server and used as debug target?

### Target platform: What about the `.classpath` file?

and the `.classpath` file will be extended by
the target platform libraries?

### Documentation: Good Tycho doc except for https://tycho.eclipseprojects.io/doc/latest/

### Documentation: Builders as one can use them in `.project` file

## Notes

Feature: List of plugins providing a new functionality to the customer


Outcomes my prohect should always have:
1 Update site
