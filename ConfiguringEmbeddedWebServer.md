# Introduction #

TongueTied is distributed with an embedded webserver (unless you are using the war file distribution). This document describes how to configure the embedded Jetty webserver.


# Details #

The file embeddedServer.properties contains the configuration options for the embedded Jetty server. It is located under the TongueTied home directory.

To change the port that the server is running on change the property `tonguetied.server.port` for http port and `tonguetied.server.secure.port` for the https port.