# Spec files
Update these to suit your own application signatures, and host in your `.well-known` folder.

It seems that Apple file can be hosted from your Organizational Domain's `.well-known` folder and they will be picked up  for tracking subdomains, if you have added a wildcard entitlement to your app.

Unfortunately at time of writing, Android does not appear to have the same subdomain association capability (you have to give a fully qualified URL), so you need to host the file(s) on the tracking subdomain's `.well-known` folder.