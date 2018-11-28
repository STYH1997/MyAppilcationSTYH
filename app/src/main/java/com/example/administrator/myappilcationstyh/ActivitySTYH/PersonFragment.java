package com.example.administrator.myappilcationstyh.ActivitySTYH;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myappilcationstyh.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonFragment extends Fragment {
    private final int MY_FAVORITE = 1;
    private final int MY_ORDER = 2;
    private final int MY_ADDRESS = 3;
    private final int MY_ACCOUNT_BEFORE = 4;
    private final int MY_ACCOUNT_AFTER = 5;

    @BindView(R.id.user_img_view)
    ImageView userImgView;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_level)
    TextView userLevel;
    @BindView(R.id.personal_for_login)
    RelativeLayout personalForLogin;
    @BindView(R.id.personal_login)
    Button personalLogin;
    @BindView(R.id.personal_for_not_login)
    RelativeLayout personalForNotLogin;
    @BindView(R.id.person_my_order)
    RelativeLayout personMyOrder;
    @BindView(R.id.my_collect)
    RelativeLayout myCollect;
    @BindView(R.id.my_address)
    RelativeLayout myAddress;
    @BindView(R.id.my_account)
    RelativeLayout myAccount;
    @BindView(R.id.person_logout_layout)
    RelativeLayout personLogoutLayout;
    Unbinder unbinder;

    private MainActivity mainActivity;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.personal_login, R.id.person_my_order, R.id.my_collect,
            R.id.my_address, R.id.my_account, R.id.person_logout_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.personal_login://登录
                startActivity(new Intent(mainActivity,LoginActivity.class));
                break;
            case R.id.person_my_order://我的订单
                /*
                if(mainActivity.isLogin()){

                    startActivity(new Intent(mainActivity,MyOrderActivity.class));
                    return;
                }
                startActivityForResult(new Intent(mainActivity,LoginActivity.class),MY_ORDER);
                 */
                break;
            case R.id.my_collect://我的收藏
                break;
            case R.id.my_address://收货地址
                break;
            case R.id.my_account://修改密码
                if(mainActivity.isLogin()){
                    startActivityForResult(new Intent(mainActivity, ChangePWDActivity.class),MY_ACCOUNT_AFTER);
                    return;
                }
                startActivityForResult(new Intent(mainActivity,LoginActivity.class),MY_ACCOUNT_BEFORE);
                break;
            case R.id.person_logout_layout://退出登录
                new AlertDialog.Builder(mainActivity)
                        .setTitle("退出登录")
                        .setMessage("您确认要退出登录吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                personalLogout();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case MY_ORDER:
                break;
            case MY_FAVORITE:
                break;
            case MY_ADDRESS:
                break;
            case MY_ACCOUNT_BEFORE://未登录时修改密码，修改密码后进行登录
                if(resultCode == Activity.RESULT_OK && data.getBooleanExtra("logined",false)){
                    Intent intent = new Intent(mainActivity,ChangePWDActivity.class);
                    startActivityForResult(intent,MY_ACCOUNT_AFTER);
                }
                break;
            case MY_ACCOUNT_AFTER://登录时修改密码，修改完毕后进行登录
                if(requestCode == Activity.RESULT_OK){
                    startActivity(new Intent(mainActivity,LoginActivity.class));
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //初始化布局，根据登录状态显示不同的布局效果
    private void init(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",0);
        String uname = sharedPreferences.getString("uname","");
        if(TextUtils.isEmpty(uname)){
            personalForLogin.setVisibility(View.GONE);
            personalForNotLogin.setVisibility(View.VISIBLE);
            personLogoutLayout.setVisibility(View.GONE);
        }else{
            personalForLogin.setVisibility(View.VISIBLE);
            personalForNotLogin.setVisibility(View.GONE);
            personLogoutLayout.setVisibility(View.VISIBLE);
            userName.setText(uname);

            String image = sharedPreferences.getString("image", "");
            if (!TextUtils.isEmpty(image)) {
                ImageLoader.getInstance().displayImage(image, userImgView, ImageLoaderManager.user_options);
            }
        }
    }

    //退出登录时，清除本地用户信息
    private void personalLogout(){
        SharedPreferences.Editor localEditor = mainActivity.getSharedPreferences("user",0).edit();
        localEditor.remove("member_id");
        localEditor.remove("uname");
        localEditor.remove("email");
        localEditor.remove("image");
        localEditor.commit();
        init();
        Toast.makeText(mainActivity,"退出登录成功！",Toast.LENGTH_SHORT).show();
    }
}

