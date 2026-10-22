package com.example.practicalfive;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Municipality extends AppCompatActivity {

    private final TextProcessor processor = new TextProcessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality);

        TextView tvName = findViewById(R.id.tvMunicipalityName);
        ImageView ivImage = findViewById(R.id.ivMunicipality);
        TextView tvInfo = findViewById(R.id.tvMunicipalityInfo);
        Button btnVisit = findViewById(R.id.btnVisitSite);

        // 1. 接收并验证数据
        String raw = getIntent().getStringExtra(MainActivity.EXTRA_MUNICIPALITY);
        String currentMunicipality = processor.processMunicipality(raw);
        int index = processor.indexOf(raw);

        // 安全处理无效数据
        if (currentMunicipality == null || index < 0) {
            tvName.setText(R.string.error_no_data);
            tvInfo.setText(R.string.error_no_data);
            btnVisit.setEnabled(false);
            Toast.makeText(this, R.string.error_invalid, Toast.LENGTH_LONG).show();
            return;
        }

        // 获取对应的简介文本
        String[] summaries = getResources().getStringArray(R.array.municipalities_array);

        // 2. 根据城市选择颜色、图片和官网链接
        int colorRes;
        int imageRes;
        String url;

        switch (currentMunicipality) {
            case "Beijing":
                colorRes = R.color.municipality_Beijing;
                imageRes = R.drawable.beijing;
                url = "https://english.beijing.gov.cn/";
                break;
            case "Shanghai":
                colorRes = R.color.municipality_shanghai;
                imageRes = R.drawable.shanghai;
                url = "https://english.shanghai.gov.cn/";
                break;
            case "Tianjin":
                colorRes = R.color.municipality_tianjin;
                imageRes = R.drawable.tianjin;
                url = "https://en.tj.gov.cn/";
                break;
            case "Chongqing":
                colorRes = R.color.municipality_chongqing;
                imageRes = R.drawable.chongqing;
                url = "https://english.cq.gov.cn/";
                break;
            default:
                return;
        }

        int color = ContextCompat.getColor(this, colorRes);

        // 3. 动态更新 UI
        tvName.setText(currentMunicipality);
        tvName.setBackgroundColor(color);

        ivImage.setImageResource(imageRes);

        tvInfo.setText(summaries[index]);
        tvInfo.setTextColor(color);

        // 设置按钮背景颜色
        btnVisit.setBackgroundTintList(ColorStateList.valueOf(color));

        // 4. 设置 Visit site 隐式 Intent
        final String finalUrl = url;
        btnVisit.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalUrl));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, R.string.error_no_browser, Toast.LENGTH_SHORT).show();
            }
        });
    }
}