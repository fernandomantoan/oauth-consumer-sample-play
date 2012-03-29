package controllers;

import play.modules.oauthclient.OAuthClient;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() throws Exception {
        OAuthClient client = new OAuthClient("http://localhost:8080/oauth-provider-sample/oauth/request_token",
		"http://localhost:8080/oauth-provider-sample/oauth/access_token",
		"http://localhost:8080/oauth-provider-sample/oauth/confirm_access",
		"agenda-consumer-key",
		"oauth-tcc-secret-02");
        MyCredentials credentials = new MyCredentials();
        client.authenticate(credentials, "http://localhost:9000/callback");
        render();
    }

}
