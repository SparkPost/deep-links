# Android/ Kotlin code example

Responds to Deep Links that are tracked via your ESP (such as SparkPost):

- follows the 302 redirect
- obtains the original URL from the `Location` header
- displays the received (tracked) URL and original URL on the app.

It is more efficient because it doesn't fetch the resolved web-page (which could be large).
This example uses the [OkHttp](https://square.github.io/okhttp/) library.

## Source files

`MainActivity.kt`:
- Class `MainActivity` receives the receives the `onNewIntent` event
- checks the intent action is an `appLinkAction`
- Updates the "result" TextView on the application surface with the received URL
- Calls `makeRequest`, which
  - Initialises `client` ready to handle our request, set to specifically not follow redirects
  - Builds a `request` to `url` (which will be a `GET` request by default)
  - Enqueues the request to the client, instantiating a callback
  - The callback occurs when the request completes. Either `onFailure` or `onResponse` is called.
  - `onResponse` looks for the `Location` header, and if non-null, logs it to the console and displays the `originalURL` on the app


`activity_main.xml`:
- The app, surfacing two `TextView`s that display the incoming (tracked) result URL and the original resolved URL.

`assetlinks.json`
- Generated using Android Studio Tools / App Links Assistant.

## Demonstration

Email can contain a mix of tracked deep links and tracked regular links that open in browser - see example [here](anim.md).

## Further work / "to do"

- Robustness to network problems or tracking endpoint unavailiability (e.g. time out).