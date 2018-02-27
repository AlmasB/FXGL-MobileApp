## FXGL-MobileApp
An example game that uses FXGL and runs on a mobile device.

Note: you **need** to have a real Android 5.0+ device. 

### Build (For those who know already have android-sdk)

1. Open `build.gradle` and edit the following line so that it has the path to where
your `android-sdk` is extracted:

```
androidSdk = 'C:/Users/Almas/almas_program_files/android-sdk'
```

2. Build .apk (will be built to "build/javafxports/android/"):

```
./gradlew android
```

OR build and install to a connected device:

```
./gradlew androidInstall
```

### Build (For those who have SDK Manager GUI)

1. Download [Android SDK Tools](https://developer.android.com/studio/index.html#Other).
Scroll all the way down and at the bottom click on your platform SDK Tools.

2. Extract everything and go to `android-sdk`, open SDK Manager.

3. Install latest SDK Build-tools and SDK Tools 25 (Android 7.1.1 API 25 -> SDK Platform).

4. Open `build.gradle` and edit the following line so that it has the path to where
your `android-sdk` is extracted:

```
androidSdk = 'C:/Users/Almas/almas_program_files/android-sdk'
```

5. Build .apk (will be built to "build/javafxports/android/"):

```
./gradlew android
```

OR build and install to a connected device:

```
./gradlew androidInstall
```