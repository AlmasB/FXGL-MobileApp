## FXGL-MobileApp
An example game that uses FXGL and runs on a mobile device.

Note: you **need** to have a real Android 5.0+ device. 

### Build

If you have android tools installed, go to step 3.

1. Download [Android SDK Tools](https://developer.android.com/studio/index.html#Other).
Scroll all the way down and at the bottom click on your platform SDK Tools.

2. Extract everything and install the tools:

```
ANDROID_SDK/tools/bin/sdkmanager "platform-tools" "build-tools;27.0.3" "platforms;android-25" "extras;android;m2repository" "extras;google;m2repository"
```

3. Open `build.gradle` and edit the following line so that it has the path to where
your `android-sdk` is extracted:

```
androidSdk = 'PATH_TO_ANDROID_SDK'
```

OR

Define a global gradle property named `ANDROID_HOME` inside `~/.gradle/gradle.properties` that points to the location of the Android SDK:

```
ANDROID_HOME=/path/to/android-sdk-directory
```

4. Build .apk (will be built to "build/javafxports/android/"):

```
./gradlew android
```

OR build and install to a connected device:

```
./gradlew androidInstall
```
