package com.bwie.wangkui.mynewjdshopping.myinfo.model;

import android.content.Context;
import android.widget.Toast;

import com.bwie.wangkui.mynewjdshopping.API;


/**
 * Created by ThinkPad on 2018/3/17.
 */

public class Aerifly {
   //手机号验证
    public static boolean numbleRex(Context content, String tel){
        if(tel.length()!=11){
            Toast.makeText(content, "手机号必须为11位", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(tel.matches(API.numblerex)){

            }else{
                Toast.makeText(content, "手机号码格式不正确", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    //密码验证
    public static boolean pwdRex(Context content,String pwd){
        if(pwd.length()<6){
            Toast.makeText(content, "密码长度必须大于六位", Toast.LENGTH_SHORT).show();
            return false;
        }else{

            if(pwd.matches(API.pwdrex)){
                Toast.makeText(content, "密码不能包含特殊字符", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
      return true;

    }
}
