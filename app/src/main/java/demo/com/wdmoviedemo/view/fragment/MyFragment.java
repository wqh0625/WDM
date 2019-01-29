package demo.com.wdmoviedemo.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.Result2;
import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.base.BaseFragment;
import demo.com.wdmoviedemo.core.dao.DbManager;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.core.utils.FileUtils;
import demo.com.wdmoviedemo.core.utils.MyApp;
import demo.com.wdmoviedemo.presenter.FindUserHomeInfoPresenter;
import demo.com.wdmoviedemo.presenter.TopPhotoPresenter;
import demo.com.wdmoviedemo.presenter.UserSignInPresenter;
import demo.com.wdmoviedemo.view.HomeActivity;
import demo.com.wdmoviedemo.view.LoginActivity;
import demo.com.wdmoviedemo.view.myactivity.MessageActivity;
import demo.com.wdmoviedemo.view.myactivity.My_Attention_Activity;
import demo.com.wdmoviedemo.view.myactivity.My_Feedback_Activity;
import demo.com.wdmoviedemo.view.myactivity.My_Messiage_Activity;
import demo.com.wdmoviedemo.view.myactivity.My_Rccord_Activity;


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

    private List<UserInfoBean> student;
    private View popView;

    @Override
    public void initView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            DbManager dbManager = new DbManager(getActivity());
            student = dbManager.getStudent();
            if (student.size()==0) {
                Toast.makeText(getActivity(), "空", Toast.LENGTH_SHORT).show();
                icon.setImageResource(R.drawable.my_icon);
                nickNameTv.setText("未登录");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String headPic = userInfoBean.getHeadPic();
        String nickName = userInfoBean.getNickName();
        if (headPic == null || headPic == "" || userInfoBean == null) {
            return;
        } else {
            icon.setImageURI(Uri.parse(headPic));
            nickNameTv.setText(nickName);
        }
        topPhotoPresenter = new TopPhotoPresenter(new top());
        findUserHomeInfoPresenter = new FindUserHomeInfoPresenter(new find());
        findUserHomeInfoPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId());
        userSignInPresenter = new UserSignInPresenter(new usersignIn());

    }

    class usersignIn implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            if (data.getStatus().equals("0000")) {
                icon.setImageURI(data.getHeadPath());
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setHeadPic(data.getHeadPath());
                try {
                    DbManager dbManager = new DbManager(getActivity());
                    dbManager.insertStudent(userInfoBean);
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
            } else {
                qdBtn.setText("已签到");
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

    @OnClick({R.id.mRb_messiage, R.id.mRb_aixin, R.id.mRb_logout, R.id.mRb_rccord, R.id.mRb_vsersion, R.id.mRb_feedback, R.id.my_btn_qd, R.id.my_image_icom, R.id.message})
    public void onck(View v) {
        if (v.getId() == R.id.mRb_messiage) {
            if (userInfoBean.getUserId() == 0||student.size()==0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            } else {
                startActivity(new Intent(getActivity(), My_Messiage_Activity.class));
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.mRb_aixin) {
            if (userInfoBean.getUserId() == 0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);

            } else {
                // 关注
                startActivity(new Intent(getActivity(), My_Attention_Activity.class));
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.mRb_vsersion) {
            // 版本
        } else if (v.getId() == R.id.mRb_rccord) {
            if (userInfoBean.getUserId() == 0||student.size()==0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            } else {
                // 购票记录
                startActivity(new Intent(getActivity(), My_Rccord_Activity.class));
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        } else if (v.getId() == R.id.mRb_feedback) {
            if (userInfoBean.getUserId() == 0||student.size()==0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
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
                    DbManager dbManager = null;
                    try {
                        dbManager = new DbManager(getContext());
                        int i = dbManager.deleteStudentByS(userInfoBean);
                        Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
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

            if (userInfoBean == null || userInfoBean.getUserId() == 0||student.size()==0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            } else {
                userSignInPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId());
            }
        } else if (v.getId() == R.id.my_image_icom) {

            if (userInfoBean == null || userInfoBean.getSessionId() == "" || userInfoBean.getUserId() == 0||student.size()==0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
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

            cancel = popView.findViewById(R.id.open_cancel);
            initPop(popView);
        } else if (v.getId() == R.id.message) {
            if (userInfoBean.getUserId() == 0||student.size()==0) {
                DbManager dbManager = null;
                try {
                    dbManager = new DbManager(getContext());
                    int i = dbManager.deleteStudentByS(userInfoBean);
                    Toast.makeText(getContext(), "" + i, Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(MyApp.getContext(), LoginActivity.class);
                // 跳转
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            } else {
                startActivity(new Intent(getActivity(), MessageActivity.class));
                getActivity().overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
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

                topPhotoPresenter.requestNet(userInfoBean.getUserId(), userInfoBean.getSessionId(), path);
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
}
