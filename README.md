# line-chart-view  [![Travis CI Status](https://travis-ci.org/hogelog/line-chart-view.svg)](https://travis-ci.org/hogelog/line-chart-view)

Android line chart view library.

## Usage
### Add dependency
#### Gradle

```gradle
dependencies {
    compile 'org.hogel:line-chart-view:0.1.1'
}
```

#### Maven

```xml
<dependency>
    <groupId>org.hogel</groupId>
    <artifactId>line-chart-view</artifactId>
    <version>0.1.1</version>
</dependency>
```

### LineChartView

```java
        List<LineChartView.Point> points = new ArrayList<LineChartView.Point>();
        points.add(new LineChartView.Point(10, 200));
        points.add(new LineChartView.Point(20, 300));
        points.add(new LineChartView.Point(30, 500));
        points.add(new LineChartView.Point(40, 400));

        LineChartView lineChartView = new LineChartView(this, points);

        chartContainer.addView(lineChartView);
```

![Line Chart](https://raw.githubusercontent.com/hogelog/line-chart-view/master/line-chart.png)

### Example
See [line-chart-view-demo](https://github.com/hogelog/line-chart-view/tree/master/line-chart-view-demo)
