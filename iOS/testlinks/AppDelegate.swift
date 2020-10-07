//
//  AppDelegate.swift
//  testlinks
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        return true
    }
    
    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }
    
    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }
    
    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }
    
    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }
    
    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }
    
    func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {
        // First attempt at handling a universal link
        print("Continue User Activity called: ")
        if userActivity.activityType == NSUserActivityTypeBrowsingWeb,
           let url = userActivity.webpageURL {
            //handle URL
            let r = Redirect()
            r.makeRequest(url: url, callback: { (location) in
                
                guard let locationURL = location else {return}
                
                print("locationURL", locationURL)
                
                // Show this on our simple example app
                DispatchQueue.main.async {
                    if let vc = self.window?.rootViewController as? ViewController {
                        vc.result.text = url.absoluteString
                        vc.originalURL.text = locationURL.absoluteString
                    }
                    
                }
            })
        }
        return true
    }
}

// More efficient click-tracking with HTTP GET to obtain the "302" response, but not follow the redirect through to the Location.
// The callback is used to return the Location header back from the async task = thanks @kleids
class Redirect : NSObject {
    var session: URLSession?
    
    override init() {
        super.init()
        session = URLSession(configuration: .default, delegate: self, delegateQueue: nil)
    }
    
    func makeRequest(url: URL, callback: @escaping (URL?) -> ()) {
        let task = self.session?.dataTask(with: url) {(data, response, error) in
            guard response != nil else {
                return
            }
            if let response = response as? HTTPURLResponse {
                if let l = response.value(forHTTPHeaderField: "Location") {
                    callback(URL(string: l))
                }
            }
        }
        task?.resume()
    }
}

extension Redirect: URLSessionDelegate, URLSessionTaskDelegate {
    func urlSession(_ session: URLSession, task: URLSessionTask, willPerformHTTPRedirection response: HTTPURLResponse, newRequest request: URLRequest, completionHandler: @escaping (URLRequest?) -> Void) {
        // Stops the redirection, and returns (internally) the response body.
        completionHandler(nil)
    }
}
