# TwitterLite
Twitter lite app is a micro social media service(Twitter LITE version) with light weight UIs. It has social, cloud-tech(Firebase), Offline-storage caching. Twitter lite is pure Kotlin based project, uses firebase products(Authentication, Firestore, Cloud-storage).
Tiwtter lite uses Android Official recommended architecture MVVM with the modifying of Clean Architecture. Twitter Lite has scalability with Test-driven development (TDD) ( Unit test, Integration testing, UI test ).

## Tech

- [Kotlin - 1.8.20](https://kotlinlang.org/docs/releases.html#release-details)
- [Jetpack Libraries](https://developer.android.com/jetpack)
- [MVVM with Clean Architecture](https://developer.android.com/topic/architecture)
- [Room Database :SQLite](https://developer.android.com/training/data-storage/room)
- [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html)
- [Coroutines :concurrency](https://developer.android.com/kotlin/coroutines)
- [Flow :Stream programming](https://developer.android.com/kotlin/flow)
- [Dagger Hilt :Dependency Injection](https://developer.android.com/training/dependency-injection/hilt-android)
- [Timber :Logging](https://github.com/JakeWharton/timber)
- [Material Components](https://developer.android.com/design/ui/mobile/guides/components/material-overview)
- [Firebase Authentication](https://firebase.google.com/docs/auth)
- [Firebase CLoud FireStore](https://firebase.google.com/docs/firestore)
- [Firebase Cloud Storage](https://firebase.google.com/docs/storage)
- [Glide](https://github.com/bumptech/glide)
- JUnit4, JUnit5
- Espresso

## How to run twitter lite app in Android Studio?
- Clone project with [this link](https://github.com/htetarkarzaw/TwitterLite.git)
- Android studio will download necessary plugins and sync with gradle files.
- Check out latest commit point at `main` branch and sync project.
- Make sure that you use `JavaVersion_17` for your gradle JDK.
- After that you can run on your device or emulator.

## How to run twitter lite app test?
### To run Dao Test
- Open `src/androidTest/java/com/htetarkarzaw/twitterlite/data/FeedDaoTest.kt` and Run `FeedDaoTest`

### To run ui Test
- Open `src/androidTest/java/com/htetarkarzaw/twitterlite/ui_test/TiwtterLiteUiTest.kt` and Run `TiwtterLiteUiTest`

### To run unit Test
- Open `src/test[unitTest]/java/com/htetarkarzaw/twitterlite/utils/DateTimeUtilTest.kt` and Run `DateTimeUtilTest`
- Open `src/test[unitTest]/java/com/htetarkarzaw/twitterlite/utils/InputCheckerUtilTest.kt` and Run `InputCheckerUtilTest`
