package com.example.administrator.myappilcationstyh.ActivitySTYH;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FindPWDActivitySTYH extends BaseActivitySTYH {
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.title_back, R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.btn_submit:
                changePassword();
                break;
        }
    }

    //找回密码
    private void changePassword() {
        String email = etEmail.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(FindPWDActivity.this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(email);
        if (!m.matches()) {
            Toast.makeText(FindPWDActivity.this, "邮箱格式不正确！", Toast.LENGTH_SHORT).show();
            return;
        }
//联网请求
        MemberPresenter.findPassword(new ProgressDialogSubscriber<HttpResult>(this) {
            @Override
            public void onNext(HttpResult httpResult) {
                if (httpResult.getStatus() == 0) {
                    Toast.makeText(FindPWDActivity.this, "操作成功，请登录注册邮箱使用新的密码进行登录", Toast.LENGTH_LONG).show();
//重新登录
                    startActivity(new Intent(FindPWDActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(FindPWDActivity.this, httpResult.getMsg(), Toast.LENGTH_LONG).show();
                }

            }
        }, email);
    }

}

