package com.dyibing.myapp.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.dyibing.myapp.R;
import com.dyibing.myapp.bean.DataCenter;
import com.dyibing.myapp.bean.UploadResult;
import com.dyibing.myapp.common.Constant;
import com.dyibing.myapp.mvp.presenter.UserCenterPresenter;
import com.dyibing.myapp.mvp.view.UserCenterView;
import com.dyibing.myapp.net.HttpResult;
import com.dyibing.myapp.utils.SingleToast;
import com.dyibing.myapp.utils.Utils;
import com.dyibing.myapp.utils.tts.AudioUtils;
import com.dyibing.myapp.view.CircleImageView;
import com.dyibing.myapp.view.PhotoPopupWindow;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class EditUserActivity extends AppCompatActivity implements UserCenterView {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.tv_user_id_show)
    TextView tvUserIdShow;
    @BindView(R.id.et_user_grade)
    TextView etUserGrade;
    @BindView(R.id.circle_avatar)
    CircleImageView circleAvatar;
    @BindView(R.id.et_upload_avatar)
    TextView et_upload_avatar;


    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;
    private static final String IMAGE_FILE_NAME = "icon.jpg";
    private String mImageUri;
    private UserCenterPresenter userCenterPresenter;
    private PhotoPopupWindow mPhotoPopupWindow;
    private String receiveForestCoinStatus;
    private String avatarUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        receiveForestCoinStatus = getIntent().getStringExtra(Constant.RECEIVE_FOREST_COIN_STATUS);
        userCenterPresenter = new UserCenterPresenter(this, this);
        setDataToView();
    }

    private void setDataToView() {
        ButterKnife.bind(this);
        Utils.setText(DataCenter.getInstance().getUserId(), tvUserIdShow);
        AudioUtils.getInstance().speakText(getString(R.string.edit_user_tip));
    }

    @OnClick({R.id.circle_avatar, R.id.rl_user_grade, R.id.iv_start_explore, R.id.et_upload_avatar})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.rl_user_grade:
                OptionsPickerView pvUserGrade = new OptionsPickerBuilder(EditUserActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        Utils.setText(Utils.getGradeList().get(options1), etUserGrade);
                    }
                }).setSubCalSize(8).setContentTextSize(8).build();

                pvUserGrade.setPicker(Utils.getGradeList());
                pvUserGrade.show();
                break;

            case R.id.iv_start_explore:
                saveData();
                break;
            case R.id.et_upload_avatar:
            case R.id.circle_avatar:
                updateAvatar();
                break;
        }
    }

    /**
     * 更新头像
     */
    private void updateAvatar() {
        mPhotoPopupWindow = new PhotoPopupWindow(EditUserActivity.this, v -> {
            // 权限申请
            if (ContextCompat.checkSelfPermission(EditUserActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //权限还没有授予，需要在这里写申请权限的代码
                ActivityCompat.requestPermissions(EditUserActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
            } else {
                // 如果权限已经申请过，直接进行图片选择
                mPhotoPopupWindow.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                // 判断系统中是否有处理该 Intent 的 Activity
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                } else {
                    Toast.makeText(EditUserActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                }
            }
        }, v -> {
            // 权限申请
            if (ContextCompat.checkSelfPermission(EditUserActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(EditUserActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // 权限还没有授予，需要在这里写申请权限的代码
                ActivityCompat.requestPermissions(EditUserActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
            } else {
                // 权限已经申请，直接拍照
                mPhotoPopupWindow.dismiss();
                imageCapture();
            }
        });
        View rootView = LayoutInflater.from(EditUserActivity.this)
                .inflate(R.layout.activity_main, null);
        mPhotoPopupWindow.showAtLocation(rootView,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 保存用户信息
     */
    private void saveData() {
        String nickName = etUsername.getText().toString().trim();
        String userGrade = Utils.getRequestGrade(etUserGrade.getText().toString().trim());
        if (TextUtils.isEmpty(userGrade) || TextUtils.equals("点击选择", userGrade)) {
            SingleToast.showMsg("请选择年级！");
            AudioUtils.getInstance().speakText(getString(R.string.no_user_grade_tip));
            return;
        }
        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("nickName", nickName);
        paramsMap.put("userGrade", userGrade);
        paramsMap.put("avatarUrl", avatarUrl);
        String strEntity = new Gson().toJson(paramsMap);
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
        userCenterPresenter.saveUser(body);
    }

    @Override
    public void onUpdateCurrentDateTask(HttpResult httpResult) {
        if (httpResult != null) {
            if ("0000".equals(httpResult.getCode())) {
                SingleToast.showMsg("保存成功！！");
                if (Utils.isReceiveForestCoin(receiveForestCoinStatus)) {
                    startActivity(new Intent(EditUserActivity.this, ForestCoinActivity.class));
                } else {
                    startActivity(new Intent(EditUserActivity.this, MainActivity.class));
                }
                finish();
            } else {
                SingleToast.showMsg(httpResult.getMsg());
            }
        }
    }

    @Override
    public void onUpload(UploadResult uploadResult) {
        if (uploadResult != null) {
            if ("0000".equals(uploadResult.getCode())) {
                avatarUrl = uploadResult.getData().getUrl();
                if (!TextUtils.isEmpty(avatarUrl)) {
                    circleAvatar.setVisibility(View.VISIBLE);
                    et_upload_avatar.setVisibility(View.GONE);
                    Glide.with(this).load(avatarUrl).into(circleAvatar);
                    SingleToast.showMsg("头像上传成功！");
//                    HashMap<String, Object> paramsMap = new HashMap<>();
//                    paramsMap.put("avatarUrl", avatarUrl);
//                    String strEntity = new Gson().toJson(paramsMap);
//                    RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), strEntity);
//                    userCenterPresenter.saveUser(body);
                }
            } else {
                SingleToast.showMsg(uploadResult.getMsg());
            }
        }
    }

    private void uploadFile(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("file", file.getName(), requestBody);
        MultipartBody body = builder.build();//调用即可```
        userCenterPresenter.upload(body);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.i("lgq", "onRequestPermissionsResult ....." + requestCode);

        boolean isAllGranted = true;
        for (int grant : grantResults) {  // 判断是否所有的权限都已经授予了
            Log.i("lgq", "申请权限结果====" + grant);
            if (grant != PackageManager.PERMISSION_GRANTED) {
                isAllGranted = false;
                break;
            }
        }
        if (isAllGranted) { // 所有的权限都授予了
            if (requestCode == 200) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                // 判断系统中是否有处理该 Intent 的 Activity
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_IMAGE_GET);
                } else {
                    Toast.makeText(EditUserActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                }
            } else imageCapture();
//                    startCamera();
            Log.i("lgq", "同样同意kaiqil ..onRequestPermissionsResult...");
        } else {// 提示需要权限的原因
            Log.i("lgq", "同样反对kaiqil ..onRequestPermissionsResult...");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("拍照需要允许权限, 是否再次开启?")
                    .setTitle("提示")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(EditUserActivity.this, permissions, 200);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
            builder.create().show();
        }


    }

    /**
     * 处理回调结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 回调成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
                // 大图切割
                case REQUEST_BIG_IMAGE_CUTTING:
                    File file = new File(mImageUri);
                    uploadFile(file);
                    break;
                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        startBigPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    startBigPhotoZoom(temp);
            }
        }
    }

    /**
     * 小图模式中，保存图片后，设置到视图中
     * 将图片保存设置到视图中
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(storage + "/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                    }
                }
                File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
                // 保存图片
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                uploadFile(file);
            }

            // 在视图中显示图片
//            circleAvatar.setImageBitmap(photo);
        }
    }

    /**
     * 大图模式切割图片
     * 直接创建一个文件将切割后的图片写入
     */
    public void startBigPhotoZoom(File inputFile) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            mImageUri = file.getAbsolutePath();
        }

        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(EditUserActivity.this, inputFile), "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public void startBigPhotoZoom(Uri uri) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            mImageUri = file.getAbsolutePath(); // 将 uri 传出，方便设置到视图中
        }

        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 判断系统及拍照
     */
    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        File pictureFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pictureUri = FileProvider.getUriForFile(this,
                    "com.dyibing.myapp.fileProvider", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        // 去拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioUtils.getInstance().stopSpeaking();
    }
}
