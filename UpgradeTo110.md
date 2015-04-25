# Introduction #

This page details how to upgrade from the previous version to 1.1.0

# Details #

**N.B.** that you only need to perform these actions if you are upgrading from a previous version of TongueTied

  1. **BACK UP YOUR DATABASE FIRST**. Create a back up of you database first. Please follow the instructions for your database of choice.
  1. Stop the old server.
  1. Run the file `<`db-name`>`-update.sql. It is located in the TongueTied.war file under:
> > `<TONGUETIED_HOME>/tonguetied/webapp/WEB-INF/sql/update/1.1.0`
  1. Start the new server (making sure to point to the existng database).