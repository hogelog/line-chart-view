# line-chart-view  [![Travis CI Status](https://travis-ci.org/hogelog/line-chart-view.svg)](https://travis-ci.org/hogelog/line-chart-view)

Android line chart view library.

## Usage
### Add dependency
#### Gradle

```gradle
dependencies {
    compile 'org.hogel:line-chart-view:0.1.2'
}
```

#### Maven

```xml
<dependency>
    <groupId>org.hogel</groupId>
    <artifactId>line-chart-view</artifactId>
    <version>0.1.2</version>
</dependency>
```

### LineChartView

```java
List<LineChartView.Point> points = new ArrayList<LineChartView.Point>();
points.add(new LineChartView.Point(-17, -100));
points.add(new LineChartView.Point(4, 200));
points.add(new LineChartView.Point(5, 400));
points.add(new LineChartView.Point(6, 1100));
points.add(new LineChartView.Point(7, 700));

LineChartView lineChartView = new LineChartView(this, points);

chartContainer.addView(lineChartView);
```

![Line Chart](https://raw.githubusercontent.com/hogelog/line-chart-view/master/line-chart-view.png)

## Example
See [line-chart-view-demo](https://github.com/hogelog/line-chart-view/tree/master/line-chart-view-demo)

## API
See [Javadoc](http://hogelog.github.io/line-chart-view/javadoc/)

## Development version
Use sonatype snapshot repository.

```gradle
repositories {
    maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
}

dependencies {
    compile 'org.hogel:line-chart-view:0.1.3-SNAPSHOT'
}
```
