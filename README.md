# KoinMVVM

A simple Android mobile application that has been implemented using clean architecture alongside with Kotlin, Koin, MVVM, Retrofit, Room, Lifecycle, LiveData, Material Design, and custom pager library to retrieve news data from the NewsApi.org website. This simple mobile application purpose to just explore the clean Koin dependency injection architecture.

# Screenshots
![](app.gif)

# Note
To run the app you have to add the NewsApi.org key. I have taken this step due to limited access to daily requests for News. Getting a key would take just a few seconds :)

# Setup NewsApi.org API key
1. Visit <a href="https://newsapi.org/">NewsApi.org</a> and register. If you have already then just SignIn and get the key.
2. Copy your API key from the account section.
3. Open build.gradle(Module:app)
4. Find out the productFlavors section, in that section, there are two flavors available 1) development and 2) production. Please your API key into NEWS_API_KEY def variable.
<pre>
build.gradle(Module:app)

productFlavors{
  ------
  def NEWS_API_KEY
  development{
    ------
    NEWS_API_KEY = "PASTE HERE YOUR API KEY"
    ------
  }
  production {
    ------
    NEWS_API_KEY = "PASTE HERE YOUR API KEY"
    ------
  }
}</pre>

# Technologies & Methodologies which has used in this application
<ol>
<li><a href="https://kotlinlang.org/docs/reference/android-overview.html">Kotlin</a></li>
<li><a href="https://insert-koin.io/">Koin</a></li>
<li><a href="https://www.journaldev.com/20292/android-mvvm-design-pattern">MVVM</a></li>
<li><a href="https://square.github.io/retrofit/">Retrofit</a></li>
<li><a href="https://github.com/ReactiveX/RxAndroid">RxJava</a></li>
<li><a href="https://github.com/ReactiveX/RxKotlin">RxKotlin</a></li>
<li><a href="https://developer.android.com/topic/libraries/architecture/room">Room</a></li>
<li><a href="https://developer.android.com/jetpack/androidx/releases/lifecycle">Lifecycle</a></li>
<li><a href="https://developer.android.com/topic/libraries/architecture/livedata">LiveData</a></li>
<li><a href="https://material.io/develop/android/">Material Design</li>
</ol>

# Supported Android Versions
Android version targeted : Android 6.0 and above

# Contributing
All pull requests are welcome. For major changes, please first open an issue to discuss what you would like need to change.
