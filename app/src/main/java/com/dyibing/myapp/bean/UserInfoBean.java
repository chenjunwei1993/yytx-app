package com.dyibing.myapp.bean;

public class UserInfoBean {


    /**
     * userId : rw20071300002
     * nickName : 测试1
     * birthday : 2020-07-13 00:00:00
     * userSex :  F
     * userHobby : 爱好1
     * likeGift : 礼物1
     * likeCartoon : 动画1
     * likeIdol : 偶像1
     * likeGame : 游戏1
     * avatarUrl : http://test.xxzh.site/1594629374496.jpg
     * forestCoinCount : 30
     * likesCount : null
     */

    private String userId;
    private String nickName;
    private String birthday;
    private String userSex;
    private String userHobby;
    private String likeGift;
    private String likeCartoon;
    private String likeIdol;
    private String likeGame;
    private String avatarUrl;
    private int forestCoinCount;
    private int likesCount;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
}
