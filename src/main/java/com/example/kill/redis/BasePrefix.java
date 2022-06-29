package com.example.kill.redis;


public class BasePrefix implements KeyPrefix {
    private String prefix;

    public BasePrefix( String prefix) {
        this.prefix = prefix;
    }


    public String getPrefix() {
        String className = getClass().getSimpleName();
        System.out.println(className);
        return className+":" + prefix;
    }

}
