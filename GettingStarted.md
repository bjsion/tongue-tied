# Introduction #

Here you will learn how to set up TongueTied after you have installed it. This howto assumes you have followed the [Installation Instructions](InstallationInstructions.md) and that the TongueTied server is running.


# Details #

TongueTied is a resource management tool. The basic idea is that a Keyword contains translations. Each translation of a keyword is identified by a Language, Country and Bundle. The default Language, Country and Bundle are already set up. Follow these instructions to set up additional Bundles, Countries and Languages.

  1. Login in as an administrator or developer (The default admin username / password is admin / admin).
  1. Create your resource bundles.
  1. Select the "Bundles" tab.
  1. Select the "New Bundle" option.
  1. Complete the form and save. Please note that the Resource Name of the bundle should **NOT** include the resource suffix (e.g. .properties, .resx, .csv etc). TongueTied will determine the resource file suffix when importing or exporting. Resource bundles can include a hierarchical structure which will be used when exporting to Java or .Net bundles. The format of the resource name should be delimited by the "\" or the "/" chararcters in the format "`/<SOME_DIR>/<RESOURCE_NAME>`" for example "`/org/tonguetied/example/mybundle`"
  1. Create a language.
  1. Select the "Languages" tab.
  1. Select the "New Language" option.
  1. Complete the form and save.
  1. Create your countries.
  1. Select the "Countries" tab.
  1. Select the "New Country" option.
  1. Complete the form and save.
  1. Start creating your translations

**N.B** that only "Administrator" or "Developer" roles have permission to add or edit Bundles, Countries or Languages.

Next: [Importing translations](ImportingTranslations.md)