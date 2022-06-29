package com.example.kill.redis;


public class UserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600*24 * 2;

    private UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey token = new UserKey("tk");

    public static void main(String[] args) {
        System.out.println(UserKey.token.getPrefix());
    }

}
