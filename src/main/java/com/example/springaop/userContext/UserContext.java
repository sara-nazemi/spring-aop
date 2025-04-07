package com.example.springaop.userContext;

public class UserContext {
    private static final ThreadLocal<String> currentRole = new ThreadLocal<>();

    public static  void setRole(String role){
        currentRole.set(role);
    }

    public static String getRole(){
        return  currentRole.get();
    }

    public static void clear(){
        currentRole.remove();
    }
}
