package com.example.cocktail;

public class UserInfo {
    private String nickname;
    private String phone;

    public UserInfo(String nickname, String phone){
        this.nickname=nickname;
        this.phone=phone;
    }

    public String getNickname(){
        return this.nickname;
    }

    public void setNickname(String nickname){
        this.nickname=nickname;
    }
    public String getPhone(){
        return this.phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }
}
