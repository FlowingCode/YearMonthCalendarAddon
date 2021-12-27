<!--
[![Published on Vaadin Directory](https://img.shields.io/badge/Vaadin%20Directory-published-00b4f0.svg)](https://vaadin.com/directory/component/year-month-calendar-addon)
[![Stars on vaadin.com/directory](https://img.shields.io/vaadin-directory/star/app-layout-addon.svg)](https://vaadin.com/directory/component/year-month-calendar-addon)
-->
[![Build Status](https://jenkins.flowingcode.com/job/YearMonthCalendar-addon/badge/icon)](https://jenkins.flowingcode.com/job/YearMonthCalendar-addon)

# Year/Month Calendar Add-on

Full year / month calendar for Vaadin 22+

## Features

* Java API for applying CSS class names to specific dates
* Selection listener
* Responsive layout

## Online demo

[Online demo here](http://addonsv22-staging.flowingcode.com/year-month-calendar)

<!--
## Download release

[Available in Vaadin Directory](https://vaadin.com/directory/component/year-month-calendar-addon)
-->

## Building and running demo

- git clone repository
- mvn clean install jetty:run

To see the demo, navigate to http://localhost:8080/

## Release notes

See [here](https://github.com/FlowingCode/YearMonthCalendarAddon/releases)

## Issue tracking

The issues for this add-on are tracked on its github.com page. All bug reports and feature requests are appreciated. 

## Contributions

Contributions are welcome, but there are no guarantees that they are accepted as such. Process for contributing is the following:

- Fork this project
- Create an issue to this project about the contribution (bug or feature) if there is no such issue about it already. Try to keep the scope minimal.
- Develop and test the fix or functionality carefully. Only include minimum amount of code needed to fix the issue.
- Refer to the fixed issue in commit
- Send a pull request for the original project
- Comment on the original issue that you have implemented a fix for it

## License & Author

Add-on is distributed under Apache License 2.0. For license terms, see LICENSE.txt.

YearMonthCalendar Addon is written by Flowing Code S.A.

# Developer Guide

## Getting started

Instantiate the component and configure a `ClassNameGenerator`

```
YearCalendar calendar = new YearCalendar();

calendar.setClassNameGenerator(date -> {
   if (TestUtils.isPublicHoliday(date)) {
     return "holiday";
   }
   if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
     return "weekend";
   }
   return null;
});
```
