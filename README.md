# OAuth Consumer with Play Framework

This project is a simple contacts manager system, to be used as a sample of implementation of an OAuth 1.0a consumer, using [Play! Framework](http://playframework.org). It's main purpose is to consume the endpoint defined in the [oauth-provider-sample project](https://github.com/fernandomantoan/oauth-provider-sample).

## Pre-requisites

This project requires [Play Framework version 1.2.4](http://www.playframework.org/download), and is not supported in the latest 2.x versions, mainly because of the changes between versions 1.x and 2.x. It also needs a MySQL Server.

## Configuration

The configuration file is located at **app/conf/application.conf**, in this file you should locate the parameter **db** and configure as your server configuration is. For example, this is the default configuration:

	db=mysql://root:root@localhost/oauth_contacts

The OAuth endpoints aren't in any configuration files, to change them, the **AuthenticationController** must be modified. The OAuth module configuration is located in the **auth()** method, as showed in the following code:

	client = new OAuthClient("http://localhost:8080/oauth-provider-sample/oauth/request_token",
			"http://localhost:8080/oauth-provider-sample/oauth/access_token",
			"http://localhost:8080/oauth-provider-sample/oauth/confirm_access",
			"contacts-consumer-key",
			"oauth-tcc-secret-02");
    credentials = new MyCredentials();
    client.authenticate(credentials, "http://localhost:9000/auth/callback");

The **OAuthClient** class constructor defines:

* The request token endpoint;
* The access token endpoint;
* The acess confirmation endpoint;
* The OAuth consumer key;
* The OAuth consumer secret.

The **authenticate()** method has two parameters:

* The credentials class which will store the access token;
* The callback URL, which is mapped to the **callback()** method of the AuthenticationController class.

Another configuration is the user service URL, where the consumer will retrieve the user's data. The URL is defined in the callback() method:

	HttpResponse response = client.sign(credentials, WS.url("http://localhost:8080/oauth-provider-sample/user/info"), "POST").post();

And at last, the server logout URL, defined in the logout() method:

	redirect("http://localhost:8080/oauth-provider-sample/logout.do");

## Usage

When the user browses the application, the application will verify if there is a logged user, if not the **AuthenticationController** class will be called, executing the flow defined in the **auth()** method.

This logic is to configure the OAuthClient and call it's **authenticate(credentials, callback)** method, to ask for authentication/authorization in the OAuth endpoint. As showed in the configuration section, the callback is mapping to **auth/callback**, which is routed to the **callback()** method of the AuthenticationController.

In this method the access token will be retrieved and stored, and the user service endpoint defined in the **client.sign()** code, will be called to get the user profile data. Then the data will be stored in the session, and the user will be able to use the contacts manager system.

The **AuthenticationController** also has the **logout()** method, which will clear the consumer's session and redirect the user to the OAuth server logout.

In the application the user will be able to save contacts, list them and delete. Just simple features, as the main purpose is to show an OAuth 1.0a consumer implementation.

## Improvements

There are some improvements that could be useful to this project:

* The consumer could store the access token and implement an expiration verification, so that the OAuth endpoint wouldn't ask for authorization everytime that the login is prompted;
* Store the OAuth endpoints in a configuration file;
* Store the user service endpoint in a configuration file;
* Store the OAuth server logout URL in a configuration file.

## Monograph

The full monograph document can be found in my [blog](http://fernandomantoan.com/monografia-2/estudo-de-caso-de-uma-estrutura-de-autenticacao-unica-utilizando-o-protocolo-oauth/) and it's written in Brazilian Portuguese. The result of the monograph are three main applications, stored in github:

* An [OAuth server](https://github.com/fernandomantoan/oauth-provider-sample), which uses [Spring Framework](http://www.springsource.org) with many other libraries;
* A [bills management system](https://github.com/fernandomantoan/oauth-consumer-sample-zf), which uses [Zend Framework](http://framework.zend.com) and Zend_Oauth_Consumer component;
* A [contacts management system](https://github.com/fernandomantoan/oauth-consumer-sample-play), which uses [Play! Framework](http://www.playframework.org) and the OAuth module.