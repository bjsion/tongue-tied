# Frequently Asked Questions #

  1. **TongueTied fails to start in Tomcat after being deployed in Linux.**
> > This is related to the tomcat security manager. In the file /etc/init.d/tomcat6 change the value TOMCAT\_SECURITY=yes to TOMCAT\_SECURITY=no. Please note this is a temporary solution until a better recommendation can be made.
  1. **What is a bundle?**
> > A bundle is a way of grouping keywords. It is conceptually similar to a resource bundle in that translations can be categorised for keywords.
  1. **What is a "Default" bundle?**
> > The concept of a Default bundle is really just to help make creating translations easier and save some mouse clicks. When a new keyword is created then the translations are assigned the "Default" bundle by default. The default can change over time but there is only ever 1 default bundle at one time.
  1. **What is a "Global" bundle?**
> > A bundle marked as global means that the global translations will be included as part of the export. There can be many global bundles but a global bundle is not required to use TongueTied. It works like this, for example:


> myBundle has the keywords:
```
    key.one=one
    key.two=two
```
> globalBundle has the keywords:
```
    key.two=2
    key.three=3
```
> So when the export is performed, you will get for myBundle:
```
    key.one=one
    key.two=two
    key.three=3
```
> When doing an export the user has the option whether to include the global translations in every export.