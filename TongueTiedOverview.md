# Introduction #

TongueTied is a web based resource management tool that allows you to manage your static resources and translations.


# Details #

This tool helps with the creation of keywords with support for multi-language or multi-region resources.

One of the key features of TongueTied is that it allows static resources to be exported from the application and can import translations from resources into the application. The following formats are currently supported for both export and import:
  * Java Properties
  * Java FX Properties
  * .Net Resources (.resx)
  * CSV
  * Excel

TongueTied integrates an optional work flow around a keyword to track changes to a translation and ensure the validity of that translation. Operators are allowed to query a translation if they believe it to be incorrect. For more information see [this](TranslationWorkFlow.md) page about the workflow