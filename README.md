## FXGL-MobileApp
An example game that uses FXGL and runs on a mobile device.

### Build (Android)

Note: you **need** to have a real Android 5.0+ device. 

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

### Build (iOS)

Note: you **need** to have a Mac with MacOS X 10.11.5 or superior
and Xcode 8.x or superior.

1. Open Xcode with a new project, e.g. Single View Application

2. Go to Preferences -> Accounts and add your Apple ID

3. Connect your device to your Mac and enable your device as a developer device

4. In Xcode change the bundle name of the app to be `com.almasb.fxgltest.SampleGameApp`.
At this point Xcode should automatically create certificates and an iOS Development provisioning profile.

5. In Xcode select your device from the devices to run applications on and click run.

6. The following should build and install the game to a connected device:
(Note: the very first build will take ~20-30 mins)

```
./gradlew launchIOSDevice
```

If you have problems running on iOS, please read
[JavaFXPorts Docs](http://docs.gluonhq.com/javafxports/#_building_and_running).