package controllers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import models.MyCredentials;
import models.User;
import play.libs.WS;
import play.libs.WS.HttpResponse;
import play.modules.oauthclient.ICredentials;
import play.modules.oauthclient.OAuthClient;
import play.mvc.Before;
import play.mvc.Controller;

public class AuthenticationController extends Controller {
	
	private static ICredentials credentials;
	private static OAuthClient client;
	
	@Before(unless={"auth","callback"})
	static void checkAuthentication() throws Exception {
		if (session.get("user") == null) auth();
	}
	
	public static void auth() throws Exception {
		
		if (session.get("user") != null)
			ContactController.index();
			
		client = new OAuthClient("http://localhost:8080/oauth-provider-sample/oauth/request_token",
				"http://localhost:8080/oauth-provider-sample/oauth/access_token",
				"http://localhost:8080/oauth-provider-sample/oauth/confirm_access",
				"contacts-consumer-key",
				"oauth-tcc-secret-02");
        credentials = new MyCredentials();
        client.authenticate(credentials, "http://localhost:9000/auth/callback");
	}
	
	public static void callback() throws Exception {
		String verifier = params.get("oauth_verifier");
		credentials = MyCredentials.find("byToken", params.get("oauth_token")).first();
		client.retrieveAccessToken(credentials, verifier);
		
		HttpResponse response = client.sign(credentials, WS.url("http://localhost:8080/oauth-provider-sample/user/info"), "POST").post();
		
		JsonElement json = new JsonParser().parse(response.getString());
		User user = new Gson().fromJson(json.getAsJsonObject().get("user"), User.class);
		
		session.put("user_id", user.id);
		session.put("user", user.username);
		session.put("user_realname", user.realname);
		
		ContactController.index();
	}
	
	public static void logout() throws Exception {
		session.clear();
		redirect("http://localhost:8080/oauth-provider-sample/logout.do");
	}
}