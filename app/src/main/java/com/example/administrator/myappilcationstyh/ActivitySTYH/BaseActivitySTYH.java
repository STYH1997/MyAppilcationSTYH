package com.example.administrator.myappilcationstyh.ActivitySTYH;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivitySTYH extends AppCompatActivity {
    /**
     * 购物车商品数量
     */
    private static int cartCount = 0;

    /**
     * 是否登录了
     *
     * @return
     */
    public boolean isLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("user", 0);
        return !TextUtils.isEmpty(sharedPreferences.getString("username", ""));
    }

    /**
     * 显示商品
     * @param goodsid
     */
    public void showGoods(int goodsid){
        Intent intent = new Intent(this, GoodsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("goods_id", goodsid);
        startActivity(intent);
    }


    public int getCartCount() {
        return cartCount;
    }

    public void setCartCount(int cartCount) {
        this.cartCount = cartCount;
    }

