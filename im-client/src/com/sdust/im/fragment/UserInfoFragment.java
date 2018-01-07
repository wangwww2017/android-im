package com.sdust.im.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.sdust.im.BaseDialog;
import com.sdust.im.R;
import com.sdust.im.activity.LoginActivity;
import com.sdust.im.network.NetService;
import com.sdust.im.view.HandyTextView;
import com.sdust.im.view.TitleBarView;

public class UserInfoFragment extends Fragment {
    private Context mContext;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private Button mBtnChangeUser;
    private Button mBtnExitUser;
    private BaseDialog mBackDialog;
    private NetService mNetService = NetService.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_userinfo, null);
        findView();
        init();
        return mBaseView;
    }

    private void findView() {
        mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
        mBtnChangeUser = (Button) mBaseView.findViewById(R.id.btn_change_user);
        mBtnExitUser = (Button) mBaseView.findViewById(R.id.btn_exit_user);


    }

    private void init() {

        mBtnChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mBackDialog = BaseDialog.getDialog(mContext, "提示",
                        "确认要注销么?", "确认", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                                Intent intent = new Intent(mContext, LoginActivity.class);
                                startActivity(intent);

                                mNetService.closeConnection();
                                getActivity().onBackPressed();//销毁自己

                                showCustomToast("注销成功");
                            }
                        }, "取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);
                mBackDialog.show();
            }
        });

        mBtnExitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mBackDialog = BaseDialog.getDialog(mContext, "提示",
                        "确认要退出登录么?", "确认", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mNetService.closeConnection();
                                getActivity().finish();
//                                getActivity().onBackPressed();//销毁自己
                                //                System.exit(0);
                                showCustomToast("已退出登录");
                            }
                        }, "取消", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                mBackDialog.setButton1Background(R.drawable.btn_default_popsubmit);
                mBackDialog.show();

            }
        });

        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
        mTitleBarView.setTitleText("登录管理");
        //
        //		mTitleBarView.setBtnRight(R.string.app_login_manager);


    }


    /**
     * 显示自定义Toast提示(来自String)
     **/
    protected void showCustomToast(String text) {
        View toastRoot = LayoutInflater.from(mContext).inflate(
                R.layout.common_toast, null);
        ((HandyTextView) toastRoot.findViewById(R.id.toast_text)).setText(text);
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastRoot);
        toast.show();
    }


}

