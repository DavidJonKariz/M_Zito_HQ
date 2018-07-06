package com.example.dijonkariz.m_zito_hq;

import android.content.SharedPreferences;

import com.example.dijonkariz.m_zito_hq.Entities.AccessToken;

public class TokenManager {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static TokenManager INSTANCE = null;

    private TokenManager(SharedPreferences prefs)
    {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    static synchronized TokenManager getInstance(SharedPreferences prefs)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new TokenManager(prefs);
        }
        return INSTANCE;
    }

    void  saveToken(AccessToken token)
    {
        editor.putString("ACCESS_TOKEN", token.getAccessToken()).commit();
        editor.putString("REFRESH_TOKEN", token.getRefreshToken()).commit();
    }

    void deleteToken()
    {
        editor.remove("ACCESS_TOKEN").commit();
        editor.remove("REFRESH_TOKEN").commit();
    }

    public AccessToken getToken()
    {
        AccessToken token = new AccessToken();
        token.setAccessToken(prefs.getString("ACCESS_TOKEN", null));
        token.setRefreshToken(prefs.getString("REFRESH_TOKEN", null));
        return token;
    }
}
