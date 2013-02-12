josm-zoom
=========

JOSM plugins which zooms to native tile-like maps levels. It is useful in pair with [google-maps-wms](https://github.com/gumik/google-maps-wms).

Installation
------------

* Download [JOSM sources](http://josm.openstreetmap.de/wiki/Download#Source).
* Copy _josm-zoom_ directory onto *josm/plugins*.
* Go to _zoom_ directory and invoke `ant`.
* Copy _josm/dist/zoom.jar_ to your josm plugins directory (_~/.josm/plugins_ on Linux)

Usage
-----

When you have map layer opened you can go to _Windows_ menu and check _Zoom_. New window will be shown in _Toggle Dialogs_ area. Two buttons (*+* and _-_) and slider allows you to zoom to specific level. The _o_ button will zoom to nearest not-fractional level.
