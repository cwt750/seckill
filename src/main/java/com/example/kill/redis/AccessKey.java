package com.example.kill.redis;


public class AccessKey extends BasePrefix {
    public AccessKey(String prefix) {
        super(prefix);
    }

    public static AccessKey withExpire() {
        return new AccessKey("access");
    }

}
