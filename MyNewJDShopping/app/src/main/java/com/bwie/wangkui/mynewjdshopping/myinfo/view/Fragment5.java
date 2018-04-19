package com.bwie.wangkui.mynewjdshopping.myinfo.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bwie.wangkui.mynewjdshopping.MyApplication;
import com.bwie.wangkui.mynewjdshopping.R;


import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    @InjectView(R.id.img)
    SimpleDraweeView img;
    private static String path = "/sdcard/myHead/";// sd路径
    private SimpleDraweeView sd;
    protected  Uri tempUri;
    private File defaltefile;
    private File defaltefile1;
    private Uri uri;
    private File file;
    private String fileName;
    private PopupWindow popupWindow;
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
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("设置头像");
//                String[] items = {"选择本地照片", "拍照"};
//                builder.setNegativeButton("取消", null);
//                builder.setItems(items, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case CHOOSE_PICTURE: // 选择本地照片
//                                Intent openAlbumIntent = new Intent(
//                                        Intent.ACTION_GET_CONTENT);
//                                openAlbumIntent.setType("image/*");
//                                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
//                                break;
//                            case TAKE_PICTURE: // 拍照
//                                Intent openCameraIntent = new Intent(
//                                        MediaStore.ACTION_IMAGE_CAPTURE);
//                                tempUri = Uri.fromFile(new File(Environment
//                                        .getExternalStorageDirectory(), "image.jpg"));
//                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
//                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
//                                startActivityForResult(openCameraIntent, TAKE_PICTURE);
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();

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
        //图库照片的路径
        if (requestCode==1&&resultCode==RESULT_OK) {
            cropPhoto(data.getData());
        }

        //相机的路径为f File f=new File(Environment.getExternalStorageDirectory(),"ni.jpg");
        if (requestCode==2&&resultCode==RESULT_OK){
            File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
            cropPhoto(Uri.fromFile(temp));// 裁剪图片
        }
        if (requestCode==3&&resultCode==RESULT_OK){
            if (data != null) {
                Bundle extras = data.getExtras();
                Bitmap head = extras.getParcelable("data");
                if (head != null) {
                    setPicToView(head);// 保存在SD卡中
                    uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), head, null,null));

                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);

        int[] arr = new int[]{12,21,32,43,15};
          int w = 0;
         for(int i=0;i<arr.length;i++){
             for(int j=0;j<arr.length;j++){
                 if(arr[j]>arr[j+1]){
                     w = arr[j];
                     arr[j] = arr[j+1];
                     arr[j+1] = w;
                 }
             }
         }


    }


    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        file = new File(path);
        file.mkdirs();// 创建文件夹
        // 图片名字
        fileName = path + "head1.jpg";
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
