package com.dyibing.myapp.bean;

/**
 * Created by benson on 17-12-21.
 */

public class User extends LoginBean {
    /**
     * 协议头cookie
     */
    public String token = "";
    /**
     * 是否登录
     */
    private boolean isLogin;

    /**
     * 玩家id
     */
    private String userId;
    /**
     * 玩家账号
     */
    private String username;
    /**
     * 用户的登录密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;

    private String birthday;
    private String userSex;
    private String userHobby;
    private String likeGift;
    private String likeCartoon;
    private String likeIdol;
    private String likeGame;
    private String avatarUrl;
    private int forestCoinCount;
    private int forestCoinCount_ls;
    private int likesCount;

    public int getForestCoinCount_ls() {
        return forestCoinCount_ls;
    }

    public void setForestCoinCount_ls(int forestCoinCount_ls) {
        this.forestCoinCount_ls = forestCoinCount_ls;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserHobby() {
        return userHobby;
    }

    public void setUserHobby(String userHobby) {
        this.userHobby = userHobby;
    }

    public String getLikeGift() {
        return likeGift;
    }

    public void setLikeGift(String likeGift) {
        this.likeGift = likeGift;
    }

    public String getLikeCartoon() {
        return likeCartoon;
    }

    public void setLikeCartoon(String likeCartoon) {
        this.likeCartoon = likeCartoon;
    }

    public String getLikeIdol() {
        return likeIdol;
    }

    public void setLikeIdol(String likeIdol) {
        this.likeIdol = likeIdol;
    }

    public String getLikeGame() {
        return likeGame;
    }

    public void setLikeGame(String likeGame) {
        this.likeGame = likeGame;
    }

    public int getForestCoinCount() {
        return forestCoinCount;
    }

    public void setForestCoinCount(int forestCoinCount) {
        this.forestCoinCount = forestCoinCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public User() {
    }


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String  userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

