part of mapbox_plugin;

/// Text labels for a [Marker] info window.
class InfoWindow {
  const InfoWindow({
    this.title,
    this.snippet,
    this.anchor = const Offset(0.5, 0.0),
    this.onTap,
  });

  /// Text labels specifying that no text is to be displayed.
  static const InfoWindow noText = InfoWindow();

  /// Text displayed in an info window when the user taps the marker.
  ///
  /// A null value means no title.
  final String title;

  /// Additional text displayed below the [title].
  ///
  /// A null value means no additional text.
  final String snippet;

  /// The icon image point that will be the anchor of the info window when
  /// displayed.
  ///
  /// The image point is specified in normalized coordinates: An anchor of
  /// (0.0, 0.0) means the top left corner of the image. An anchor
  /// of (1.0, 1.0) means the bottom right corner of the image.
  final Offset anchor;

  /// onTap callback for this [InfoWindow].
  final VoidCallback onTap;

  /// Creates a new [InfoWindow] object whose values are the same as this instance,
  /// unless overwritten by the specified parameters.
  InfoWindow copyWith({
    String titleParam,
    String snippetParam,
    Offset anchorParam,
    VoidCallback onTapParam,
  }) {
    return InfoWindow(
      title: titleParam ?? title,
      snippet: snippetParam ?? snippet,
      anchor: anchorParam ?? anchor,
      onTap: onTapParam ?? onTap,
    );
  }

  dynamic _toJson() {
    final Map<String, dynamic> json = <String, dynamic>{};

    void addIfPresent(String fieldName, dynamic value) {
      if (value != null) {
        json[fieldName] = value;
      }
    }

    addIfPresent('title', title);
    addIfPresent('snippet', snippet);
    addIfPresent('anchor', _offsetToJson(anchor));

    return json;
  }

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    if (other.runtimeType != runtimeType) return false;
    final InfoWindow typedOther = other;
    return title == typedOther.title &&
        snippet == typedOther.snippet &&
        anchor == typedOther.anchor;
  }

  @override
  int get hashCode => hashValues(title.hashCode, snippet, anchor);

  @override
  String toString() {
    return 'InfoWindow{title: $title, snippet: $snippet, anchor: $anchor}';
  }
}

/// Uniquely identifies a [Marker] among [GoogleMap] markers.
///
/// This does not have to be globally unique, only unique among the list.
@immutable
class MarkerId {
  MarkerId(this.value) : assert(value != null);

  /// value of the [MarkerId].
  final String value;

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    if (other.runtimeType != runtimeType) return false;
    final MarkerId typedOther = other;
    return value == typedOther.value;
  }

  @override
  int get hashCode => value.hashCode;

  @override
  String toString() {
    return 'MarkerId{value: $value}';
  }
}

/// Marks a geographical location on the map.
///
/// A marker icon is drawn oriented against the device's screen rather than
/// the map's surface; that is, it will not necessarily change orientation
/// due to map rotations, tilting, or zooming.
@immutable
class Marker {
  const Marker({
    @required this.markerId,
    this.consumeTapEvents = false,
    this.icon = IconMarker.defaultMarker,
    this.infoWindow = InfoWindow.noText,
    this.position = const LatLng(0.0, 0.0),
    this.onTap,
  });

  /// Uniquely identifies a [Marker].
  final MarkerId markerId;

  /// True if the marker icon consumes tap events. If not, the map will perform
  /// default tap handling by centering the map on the marker and displaying its
  /// info window.
  final bool consumeTapEvents;

  /// A description of the bitmap used to draw the marker icon.
  final IconMarker icon;

  /// A Google Maps InfoWindow.
  ///
  /// The window is displayed when the marker is tapped.
  final InfoWindow infoWindow;

  /// Geographical location of the marker.
  final LatLng position;

  /// Callbacks to receive tap events for markers placed on this map.
  final VoidCallback onTap;

  /// Creates a new [Marker] object whose values are the same as this instance,
  /// unless overwritten by the specified parameters.
  Marker copyWith({
    bool consumeTapEventsParam,
    Icon iconParam,
    InfoWindow infoWindowParam,
    LatLng positionParam,
    VoidCallback onTapParam,
  }) {
    return Marker(
      markerId: markerId,
      consumeTapEvents: consumeTapEventsParam ?? consumeTapEvents,
      icon: iconParam ?? icon,
      infoWindow: infoWindowParam ?? infoWindow,
      position: positionParam ?? position,
      onTap: onTapParam ?? onTap,
    );
  }

  /// Creates a new [Marker] object whose values are the same as this instance.
  Marker clone() => copyWith();

  Map<String, dynamic> _toJson() {
    final Map<String, dynamic> json = <String, dynamic>{};

    void addIfPresent(String fieldName, dynamic value) {
      if (value != null) {
        json[fieldName] = value;
      }
    }

    addIfPresent('markerId', markerId.value);
    addIfPresent('consumeTapEvents', consumeTapEvents);
    addIfPresent('icon', icon?._toJson());
    addIfPresent('infoWindow', infoWindow?._toJson());
    addIfPresent('position', position?._toJson());
    return json;
  }

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;
    if (other.runtimeType != runtimeType) return false;
    final Marker typedOther = other;
    return markerId == typedOther.markerId &&
        consumeTapEvents == typedOther.consumeTapEvents &&
        icon == typedOther.icon &&
        infoWindow == typedOther.infoWindow &&
        position == typedOther.position &&
        onTap == typedOther.onTap;
  }

  @override
  int get hashCode => markerId.hashCode;

  @override
  String toString() {
    return 'Marker{markerId: $markerId,  '
        'consumeTapEvents: $consumeTapEvents,  '
        'icon: $icon, infoWindow: $infoWindow, position: $position,  onTap: $onTap}';
  }
}

Map<MarkerId, Marker> _keyByMarkerId(Iterable<Marker> markers) {
  if (markers == null) {
    return <MarkerId, Marker>{};
  }
  return Map<MarkerId, Marker>.fromEntries(markers.map((Marker marker) =>
      MapEntry<MarkerId, Marker>(marker.markerId, marker.clone())));
}

List<Map<String, dynamic>> _serializeMarkerSet(Set<Marker> markers) {
  if (markers == null) {
    return null;
  }
  return markers.map<Map<String, dynamic>>((Marker m) => m._toJson()).toList();
}
