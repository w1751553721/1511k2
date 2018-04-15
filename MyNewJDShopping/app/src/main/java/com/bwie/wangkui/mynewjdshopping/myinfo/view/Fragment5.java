package com.bwie.wangkui.mynewjdshopping.myinfo.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.wangkui.mynewjdshopping.MyApplication;
import com.bwie.wangkui.mynewjdshopping.R;
import com.bwie.wangkui.mynewjdshopping.myinfo.model.Utils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ThinkPad on 2018/4/1.
 */


public class Fragment5 extends Fragment {
    @InjectView(R.id.textView3)
    TextView textView3;//登录注册

    @InjectView(R.id.textView5)
    TextView textView5;//设置


    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    @InjectView(R.id.img)
    SimpleDraweeView img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment5, null);
        ButterKnife.inject(this, view);
        Log.e("=====", "onCreateView");
        return view;
    }

    @OnClick({R.id.img, R.id.textView5, R.id.textView3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img://头像
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("设置头像");
                String[] items = {"选择本地照片", "拍照"};
                builder.setNegativeButton("取消", null);
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case CHOOSE_PICTURE: // 选择本地照片
                                Intent openAlbumIntent = new Intent(
                                        Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                                break;
                            case TAKE_PICTURE: // 拍照
                                Intent openCameraIntent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                tempUri = Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "image.jpg"));
                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                                break;
                        }
                    }
                });
                builder.create().show();
                break;
            case R.id.textView5://设置
                startActivity(new Intent(getActivity(),InfoSetting.class));
                break;
            case R.id.textView3://登录注册
                startActivity(new Intent(getActivity(), LoginPage.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //得到数据库存入的值
        SharedPreferences preferences = MyApplication.getSharedPreferences();
        long uid = preferences.getLong("uid", -1);
        String username = preferences.getString("username", "13126990738");
        String icon = preferences.getString("icon", null);
        //说明有值
        if (uid != -1) {
              img.setImageURI(Uri.parse(icon));
           // Picasso.with(getActivity()).load(icon).into(img);
            textView3.setText(username);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:

                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();

        Bitmap photo = extras.getParcelable("data");
        Log.e("============", "setImageToView:" + photo);
        photo = Utils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
        img.setImageBitmap(photo);

        //  uploadPic(photo);

    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath + "");
        if (imagePath != null) {
            // 拿着imagePath上传了
            // ...
        }
    }

}
