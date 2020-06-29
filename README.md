<h1 align="center">chardware</h1>
<div align="center">
  <strong> ingrosware continued by chardeenol.</strong>
</div>
<br />

# How to setup
Clone the workspace
```
https://github.com/CHARDNOL99/IngrosWare-master.git
```
Go into folder and open up a command prompt and do

**Eclipse** -
gradlew setupDevWorkspace eclipse build

**Intelij** -
gradlew setupDevWorkspace idea genIntellijRuns build

# Open in IDE
**Eclipse**
```Right click -> New -> Java Project -> Browse location -> Select IngrosWare folder -> Finish```

**Intelij**
```Open -> Select IngrosWare folder -> Import gradle project```

# Run

Add ```-Dfml.coreMods.load=best.reich.ingros.mixin.launch.IngrosLoader``` to VM options.
