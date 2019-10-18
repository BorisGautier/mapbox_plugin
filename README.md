# Mapbox PLUGIN Native


Ce plugin Flutter pour mapbox-gl-native permet d’intégrer des cartes vectorielles interactives et personnalisables dans un widget Flutter en incorporant des vues Android et iOS.

![screenshot.png](screenshot.png)

## Installer
Ce projet est disponible sur [pub.dartlang](https://pub.dev/packages/mapbox_plugin/versions/0.0.1). Suivez les instructions pour intégrer un package dans votre application Flutter.
### Exécution de l'exemple d'application

- Installez [Flutter](https://flutter.io/get-started/) et validez son installation avecflutter doctor
- Cloner ce référentiel avec `git clone https://github.com/BorisGautier/mapbox_plugin.git`
- Exécutez l'application avec `cd mapbox_plugin/example && flutter run`

#### Mapbox Access Token

Ce projet utilise des tuiles vecteur MapBox, ce qui nécessite un compte MapBox et un jeton d'accès MapBox. Obtenez un jeton d'accès gratuit sur [la page de votre compte Mapbox](https://www.mapbox.com/account/access-tokens/).

##### Android
Une fois la clé obtenue, placez-la dans le répertoire Android du projet: ```android/app/src/main/AndroidManifest.xml:```

```<manifest ...
  <application ...
    <meta-data android:name="com.mapbox.token" android:value="YOUR_TOKEN_HERE" />
```

#### iOS
Ajoutez ces lignes à votre info.plist

```plist
<key>io.flutter.embedded_views_preview</key>
<true/>
<key>MGLMapboxAccessToken</key>
<string>YOUR_TOKEN_HERE</string>
```
