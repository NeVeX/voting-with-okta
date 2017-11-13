# voting-with-okta
A website that is integrated with Okta to allow people to vote on various things

#### OAuth/OpenId

Implemented the oauth2 flow, but the feature is disabled in PROD

#### SAML

Went with SAML instead for the login
* https://github.com/oktadeveloper/okta-spring-boot-saml-example
* https://developer.okta.com/blog/2017/03/16/spring-boot-saml
* https://github.com/spring-projects/spring-security-saml-dsl/tree/master/samples/spring-security-saml-dsl-sample

#### Know issues

* [Bad credentials for 2+ hours tokens](https://www.google.com/search?ei=1esIWqC9KMGcmwG-s7XACw&q=Authentication+statement+is+too+old+to+be+used+with+value+spring&oq=Authentication+statement+is+too+old+to+be+used+with+value+spring&gs_l=psy-ab.3..35i39k1j0i8i30k1.3793.5976.0.6152.7.7.0.0.0.0.244.759.5j1j1.7.0....0...1.1.64.psy-ab..0.7.758...0i22i30k1.0._HXLvac-GCA)

### Todo

* Update teams on upload data (don't delete)
* add overflow not to hidden in jquery popup box
* add back the font-size for the css 
