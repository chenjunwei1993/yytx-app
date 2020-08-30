package com.dyibing.myapp.bean;

import java.util.List;

public class FragmentResult {
    private List<FragmentBean> fragmentList;
    private List<GiftBean> cashGiftList;
    private int jigsawCount;

    public List<FragmentBean> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<FragmentBean> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public List<GiftBean> getCashGiftList() {
        return cashGiftList;
    }

    public void setCashGiftList(List<GiftBean> cashGiftList) {
        this.cashGiftList = cashGiftList;
    }

    public int getJigsawCount() {
        return jigsawCount;
    }

    public void setJigsawCount(int jigsawCount) {
        this.jigsawCount = jigsawCount;
    }
}
