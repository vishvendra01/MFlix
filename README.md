<h1>MFlix</h1>

MFlix is a showcase android app for [Unsplash](https://https://unsplash.com/) on Android, built using Kotlin.

## Architecture and Tech-stack

* Built on MVVM architecture pattern
* Uses [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/), specifically ViewModel, LiveData and Room.
* Has a clean, gorgeous user interface with pretty animations, built using Android Transitions framework, and [Material Components for Android](https://github.com/material-components/material-components-android)
* Uses [Paging3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) for pagination
* Heavily uses [RxJava](https://github.com/ReactiveX/RxJava) for network calls, transformations, and database observation.
* Completely offline ready. MovieDB uses [Room](https://developer.android.com/topic/libraries/architecture/room) for managing a local SQLite database, which means that if you have seen some content already while you were online, you won't need an internet connection to see it again. Everything except movie trailers are cached.
* Uses [Retrofit](https://square.github.io/retrofit/) for making API calls.
* Uses [Glide](https://github.com/bumptech/glide) for image loading.
* Built on a Single-Activity Architecture. Every screen in the app is a fragment.