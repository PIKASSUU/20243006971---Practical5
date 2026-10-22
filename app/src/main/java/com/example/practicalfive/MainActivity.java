package com.example.practicalfive;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 两个 Activity 必须用同一个 Key 传递数据
    public static final String EXTRA_MUNICIPALITY = "extra_municipality";
    private EditText etMunicipality;
    private final TextProcessor processor = new TextProcessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMunicipality = findViewById(R.id.etMunicipality);

        // 监听键盘的 Enter (Go) 键
        etMunicipality.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_DONE) {
                handleInput();
                return true;
            }
            return false;
        });
    }

    private void handleInput() {
        String input = etMunicipality.getText().toString();

        if (input.trim().isEmpty()) {
            etMunicipality.setError(getString(R.string.error_empty));
            return;
        }

        String normalized = processor.processMunicipality(input);
        if (normalized == null) {
            etMunicipality.setError(getString(R.string.error_invalid));
            Toast.makeText(this, R.string.error_invalid, Toast.LENGTH_SHORT).show();
            return;
        }

        // 验证成功，通过显式 Intent 跳转并传值
        Intent intent = new Intent(MainActivity.this, Municipality.class);
        intent.putExtra(EXTRA_MUNICIPALITY, normalized);
        startActivity(intent);
    }
}