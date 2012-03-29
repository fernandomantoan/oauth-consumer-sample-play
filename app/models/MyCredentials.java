package models;

import play.*;
import play.db.jpa.*;
import play.modules.oauthclient.ICredentials;

import javax.persistence.*;
import java.util.*;

@Entity
public class MyCredentials extends Model implements ICredentials {
    private String token;
    private String secret;

    public void setToken(String token) {
        this.token = token;
        save();
    }
    public String getToken() {
        return this.token;
    }
    public void setSecret(String secret) {
        this.secret = secret;
        save();
    }
    public String getSecret() {
        return this.secret;
    }
}