# voting-with-okta
A website that is integrated with Okta to allow people to vote on various things

#### What is this?

For the UX Hackathon in November 2017, I wanted to create a better experience for voting at the end of the hackathon, i.e. a better UX for voting.

This application will federate login's to Okta (via OpenId/SAML) to get an authenticated user, such that there's no sign up etc
* This is to avoid cheating or voting across multiple devices.

The application supports many modes of voting but currently is set to support voting for teams with the following criteria:
* Grand Prize 
* Most Creative
* Most Impactful

People will navigate to `/voting`, which will redirect them to login to Okta. 
After logging in successfully, they'll be directed by to the `/voting` page, wherein they can choose what they want to vote on.

The user selects the option and the voting page is displayed, with all teams as thumbnails that are clickable. 
Clicking on a the thumbnail brings up a caption that has more information about the team and buttons for the above votes.

Users can change their votes so long as the voting remains open.

There are other pages too for admin functions:
* `/voting/{voting-id}/admin` 
  * An admin page that allows to open/close voting and also add teams to the voting round
* `/voting/{voting-id}/results`
  * A page that is only viewable by the admin that shows the current scores/results of the voting round

#### OAuth/OpenId

Implemented the oauth2 flow, but the feature is disabled in PROD, subsequently, I went with SAML below instead.

#### SAML

Went with SAML instead for the login since Oauth is not enabled in our PROD site.

Some good starting points:
* https://github.com/oktadeveloper/okta-spring-boot-saml-example
* https://developer.okta.com/blog/2017/03/16/spring-boot-saml
* https://github.com/spring-projects/spring-security-saml-dsl/tree/master/samples/spring-security-saml-dsl-sample

#### Know issues

* [Bad credentials for 2+ hours tokens](https://www.google.com/search?ei=1esIWqC9KMGcmwG-s7XACw&q=Authentication+statement+is+too+old+to+be+used+with+value+spring&oq=Authentication+statement+is+too+old+to+be+used+with+value+spring&gs_l=psy-ab.3..35i39k1j0i8i30k1.3793.5976.0.6152.7.7.0.0.0.0.244.759.5j1j1.7.0....0...1.1.64.psy-ab..0.7.758...0i22i30k1.0._HXLvac-GCA)
* Untested 
* Most likely does not work on IE or look good on mobile 

### Todo

* Update teams on upload data (don't delete)
* add overflow not to hidden in jquery popup box
* add back the font-size for the css (make text better at responsive)
