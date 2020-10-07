# iOS / Swift code example

An example `apple-app-site-association` file is included. Update to match your app ID and bundle ID.

This basic example app responds to Deep Links that are tracked via your ESP (such as SparkPost), by following the 302 redirect, obtaining the original surfacing the received (tracked) link, and displaying both of these on the app.

This is more involved than simply fetching with `URLSession.shared.dataTask`. It is more efficient because it doesn't fetch the resolved web-page (which could be large).

## Source files

`AppDelegate.swift`:
- receives the `NSUserActivity` event
- checks the activity is a `webpageURL`
- Calls `Redirect`, which
  - Initialises a `URLSession`
  - issues an HTTP `GET` request using [`dataTask`](https://developer.apple.com/documentation/foundation/urlsession/1411554-datatask)
  - receives the 302 redirect
  - extracts the values of the `Location` header from the HTTP response
  - provides this back to the calling code as a `URL` object, via a callback
  - updates the `UILabels` to display the URLs on the app

The return mechanism must use a callback function, because `dataTask` is async.

`ViewController.swift`:
- Basic file, surfacing two `UILabels` to display the incoming (tracked) result URL and the original resolved URL.

## Example app running

Email can contain a mix of tracked deep links and tracked regular links that open in browser - see example [here](anim.md).

## Further work / "to do"

- From compile targets iOS 13 onwards, [SceneDelegate](https://developer.apple.com/documentation/uikit/uiscenedelegate) is used for some of the `AppDelegate` roles, including handling of Universal Links.
- Robustness to network problems or tracking endpoint unavailiability (e.g. time out).