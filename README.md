
# ♥ Love

### [Open Source Charity Software]

Love is an open source charity software package that allows people and organizations to setup a customized site within minutes. You can manage content, and donations. Love can accept individual donations as well as monthly contributions. 

Love is Open Source and leverages everything Open Source to get you up and running.

* Linux
* Java
* Apache
* Tomcat
* MySQL

You don't need to be a programmer in order to set this up.


### Developers

If you are a developer, here is what you need to get it running locally: 

* Install Java 8
* Install grails 3.3.9
* `cd Love/`
* `cp src/main/webapp/settings/settings.properties.example src/main/webapp/settings/settings.properties`
* Update `src/main/webapp/settings/settings.properties` with configurations for Mail & Stripe
* run `grails run-app`
* Browse `http://localhost:9264/ioc/admin`  for administration
* Browse to `http://localhost:9264/ioc` for home page and web front


