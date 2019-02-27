package com.bbny.qifengwlw.spannablestringdemo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    LinearLayout layout_text;
    String text = "Hello this Fuck World";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layout_text = findViewById(R.id.layout_text);
        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE 是否影响字段前后添加的字段样式
        SpannableString span1 = new SpannableString(text);
        span1.setSpan(new ForegroundColorSpan(Color.RED), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        addNewText(span1);

        SpannableString span2 = new SpannableString(text);
        span2.setSpan(new BackgroundColorSpan(Color.CYAN), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        addNewText(span2);

        SpannableString span3 = new SpannableString(text);
        span3.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 6, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        addNewText(span3);

        SpannableString span4 = new SpannableString(text);
        span4.setSpan(new UnderlineSpan(), 11, 15, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        addNewText(span4);

        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        };
        builder.setSpan(clickableSpan,16,21,Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        builder.append(" !!!!!");
        addNewText(builder).setMovementMethod(LinkMovementMethod.getInstance());

        //SpannableString 与 SpannableStringBuilder 区别
        //SpannableString在创建的时候就需要指定好字符串，之后就不能更改了，而SpannableStringBuilder可以使用append()方法，在已有的富文本后添加新的富文本。
        //

        String h1 = "<b>"+"-----    html富文本     -----"+"</b>";
        addNewText(Html.fromHtml(h1));

        String h2 = "<b>"+text+"</b>";
        addNewText(Html.fromHtml(h2));

        String h3 = "<font color='#66ccff'>"+text+"</font>";
        addNewText(Html.fromHtml(h3));

        String h4 = "<i><a href='https://www.baidu.com'><font color='#66ccff'>"+text+"</font></a></i>";
        addNewText(Html.fromHtml(h4)).setMovementMethod(LinkMovementMethod.getInstance());

        //不能加载图片
        String h5 = "<img src='https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2074505036,2669528153&fm=111&gp=0.jpg'>"+"</img>";
        addNewText(Html.fromHtml(h5,imgGetter,null));

    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {

            Drawable drawable = null;
            URL url;
            try {
                url = new URL(source);
                Drawable.createFromStream(url.openStream(), "");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());

            return drawable;
        }
    };


    public <T> TextView addNewText(T span) {
        TextView textView = new TextView(MainActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(20,20,20,20);
        textView.setLayoutParams(params);
        if (span instanceof SpannableStringBuilder) {
            textView.setText((SpannableStringBuilder) span);
        }else if(span instanceof SpannableString){
            textView.setText((SpannableString) span);
        }else {
            //html富文本

        }
        layout_text.addView(textView);
        return textView;
    }

}
