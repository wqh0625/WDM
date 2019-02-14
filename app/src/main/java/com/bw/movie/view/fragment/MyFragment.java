package com.bw.movie.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.presenter.VersionsPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import com.bw.movie.bean.Result;
import com.bw.movie.bean.Result2;
import com.bw.movie.bean.UserInfoBean;
import com.bw.movie.core.base.BaseFragment;
import com.bw.movie.core.dao.DbManager;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.core.utils.FileUtils;
import com.bw.movie.presenter.FindUserHomeInfoPresenter;
import com.bw.movie.presenter.TopPhotoPresenter;
import com.bw.movie.presenter.UserSignInPresenter;
import com.bw.movie.view.myactivity.MessageActivity;
import com.bw.movie.view.myactivity.My_Attention_Activity;
import com.bw.movie.view.myactivity.My_Feedback_Activity;
import com.bw.movie.view.myactivity.My_Messiage_Activity;
import com.bw.movie.view.myactivity.My_Rccord_Activity;


public class MyFragment extends BaseFragment {

    @BindView(R.id.my_tv_nickName)
    TextView nickNameTv;
    @BindView(R.id.my_image_icom)
    SimpleDraweeView icon;
    @BindView(R.id.my_btn_qd)
    Button qdBtn;
    private FindUserHomeInfoPresenter findUserHomeInfoPresenter;
    private RelativeLayout photo;
    private RelativeLayout cancel;
    private RelativeLayout camera;
    private PopupWindow popWindow;
    private TopPhotoPresenter topPhotoPresenter;
    private UserSignInPresenter userSignInPresenter;

    private List<UserInfoBean> a;
    private View popView;
    private Dao<UserInfoBean, String> userDao;
    private int userId;
    private String sessionId;

    @Override
    public void initView(View view) {
        findUserHomeInfoPresenter = new FindUserHomeInfoPresenter(new find());
    }

    @Override
    public void onResume() {
        super.onResume();
        topPhotoPresenter = new TopPhotoPresenter(new top());
        userSignInPresenter = new UserSignInPresenter(new usersignIn());
        try {
            userDao = new DbManager(getActivity()).getUserDao();
            a = userDao.queryForAll();
            Log.v("/////--", a.toString());


            if (a.size() == 0) {
                icon.setImageResource(R.drawable.my_icon);
                nickNameTv.setText("未登录");
                qdBtn.setText("签到");
                return;
            } else {
                UserInfoBean userInfoBeana = a.get(0);
                String headPic = userInfoBeana.getHeadPic();
                String nickName = userInfoBeana.getNickName();
                if (headPic == null || headPic == "" || userInfoBeana == null) {
                    return;
                } else {
                    icon.setImageURI(Uri.parse(headPic));
                    nickNameTv.setText(nickName);
                }
                findUserHomeInfoPresenter.requestNet(userInfoBeana.getUserId(), userInfoBeana.getSessionId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    class usersignIn implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {

                qdBtn.setText("已签到");
            }
        }
        @Override
        public void fail(ApiException a) {
        }
    }

    class top implements DataCall<Result> {
        @Override
        public void success(Result data) {

            if (data.getStatus().equals("0000")) {
                icon.setImageURI(data.getHeadPath());
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setHeadPic(data.getHeadPath());
                try {
                    userDao.createOrUpdate(userInfoBean);
                    onResume();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    class find implements DataCall<Result<Result2>> {
        @Override
        public void success(Result<Result2> data) {
            if (data.getResult().getUserSignStatus() == 1) {
                qdBtn.setText("签到");
                nickNameTv.setText("" + data.getResult().getNickName());
            } else {
                qdBtn.setText("已签到");
                nickNameTv.setText("" + data.getResult().getNickName());
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mys;
    }

    @OnClick({R.id.mRb_messiage, R.id.mRb_aixin, R.id.mRb_logout, R.id.mRb_rccord, R.id.mRb_vsersion, R.id.mRb_feedback, R.id.my_btn_qd, R.id.my_image_icom, R.id.message, R.id.my_tv_nickName})
    public void onck(View v) {
        if (v.getId() == R.id.mRb_messiage) {
            if (student.size() == 0) {
                s();
            } else {
                startActivity(new Intent(getActivity(), My_Messiage_Activity.class));
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.mRb_aixin) {
            if (student.size() == 0) {
                s();
            } else {
                // 关注
                startActivity(new Intent(getActivity(), My_Attention_Activity.class));
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.mRb_vsersion) {
            // 版本
            if (student.size() == 0) {
                s();
            } else {
                userId = a.get(0).getUserId();
                sessionId = a.get(0).getSessionId();
                VersionsPresenter versionsPresenter = new VersionsPresenter(new VersionsCall());
                try {
                    String versionName = getVersionName(getContext());
                    versionsPresenter.requestNet(userId, sessionId, versionName);

                    versionsPresenter.requestNet(userId, sessionId, versionName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } else if (v.getId() == R.id.mRb_rccord) {
            if (student.size() == 0) {
                s();
            } else {
                // 购票记录
                startActivity(new Intent(getActivity(), My_Rccord_Activity.class));
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.mRb_feedback) {
            if (student.size() == 0) {
                s();
            } else { // 意见反馈
                startActivity(new Intent(getActivity(), My_Feedback_Activity.class));
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.mRb_logout) {


            // 退出登录 通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            //    设置Title的内容
            builder.setTitle("温馨提示");
            //    设置Content来显示一个信息
            builder.setMessage("确定退出登录吗？");
            //    设置一个PositiveButton
            builder.setPositiveButton("确认退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        Dao<UserInfoBean, String> userDao = new DbManager(getContext()).getUserDao();

                        userDao.deleteBuilder().delete();


                        List<UserInfoBean> userInfoBeans = userDao.queryForAll();
                        Log.v("asdas", userInfoBeans.size() + "");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    onResume();
                }
            });
            //    设置一个NegativeButton
            builder.setNegativeButton("暂不退出", null);
            //    显示出该对话框
            builder.show();

        } else if (v.getId() == R.id.my_btn_qd) {
            if (student.size() == 0) {
                s();
            } else {
                userSignInPresenter.requestNet(a.get(0).getUserId(), a.get(0).getSessionId());
            }
        } else if (v.getId() == R.id.my_image_icom) {

            if (student.size() == 0) {
                s();
            } else {
                popView = View.inflate(getActivity(), R.layout.my_icon_update, null);
                popWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, true);
                popWindow.setTouchable(true);
                popWindow.setBackgroundDrawable(new BitmapDrawable());
                popWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

                final View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mys, null);
                popWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
                camera = popView.findViewById(R.id.open_camera);

                photo = popView.findViewById(R.id.open_album);

                cancel = popView.findViewById(R.id.open_cancel);
                initPop(popView);
            }
        } else if (v.getId() == R.id.message) {
            if (student.size() == 0) {
                s();
            } else {
                startActivity(new Intent(getActivity(), MessageActivity.class));
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.my_tv_nickName) {
            if (student.size() == 0) {

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        findUserHomeInfoPresenter.unBind();
        topPhotoPresenter.unBind();
        userSignInPresenter.unBind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                File imageFile = FileUtils.getImageFile();
                String path = imageFile.getPath();

                topPhotoPresenter.requestNet(a.get(0).getUserId(), a.get(0).getSessionId(), path);
                popWindow.dismiss();
                break;
            case 1:
                Uri data1 = data.getData();

                String[] proj = {MediaStore.Images.Media.DATA};

                Cursor actualimagecursor = getActivity().managedQuery(data1, proj, null, null, null);

                int actual_image_column_index = actualimagecursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                actualimagecursor.moveToFirst();
                String img_path = actualimagecursor
                        .getString(actual_image_column_index);

                // 4.0以上平台会自动关闭cursor,所以加上版本判断,OK
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    actualimagecursor.close();
                }
                topPhotoPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), img_path);
                popWindow.dismiss();
                break;
            default:
                break;
        }
    }

    private void initPop(View popView) {

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消弹框
                popWindow.dismiss();
            }
        });
        //相册
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent openAlbumIntent = new Intent(
                            Intent.ACTION_PICK);
                    openAlbumIntent.setType("image/*");
                    //用startActivityForResult方法，待会儿重写onActivityResult()方法，拿到图片做裁剪操作
                    startActivityForResult(openAlbumIntent, 1);
                }

            }
        });
        //相机
        camera.setOnClickListener(new View.OnClickListener() {
            private Uri tempUri;

            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE);

                tempUri = Uri.parse(FileUtils.getDir("/image/bimap") + "1.jpg");
                Log.e("zmz", "=====" + tempUri);

                //启动相机程序
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(intent, 0);
            }
        });
    }

    /**
     * 获取版本号
     *
     * @throws PackageManager.NameNotFoundException
     */
    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        String version = packInfo.versionName;
        return version;
    }

    //查询新版本
    class VersionsCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getFlag() == 2) {
                Toast.makeText(getActivity(), "当前已是最新版本!", Toast.LENGTH_SHORT).show();
            } else if (data.getFlag() == 1) {
                Toast.makeText(getActivity(), "有新版本，请进行下载!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void fail(ApiException a) {
        }
    }
}
