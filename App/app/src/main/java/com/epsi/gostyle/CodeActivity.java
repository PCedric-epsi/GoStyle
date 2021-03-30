package com.epsi.gostyle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.epsi.gostyle.bean.CodeBean;

import java.util.ArrayList;
import java.util.List;

public class CodeActivity extends AppCompatActivity {

    private ListView listView;
    public static List<CodeBean> codeBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        listView = (ListView)findViewById(R.id.ltCode);
        ArrayAdapter<CodeBean> arrayAdapter = new ArrayAdapter<CodeBean>(this, android.R.layout.simple_list_item_1 , codeBeanList);
        listView.setAdapter(arrayAdapter);
    }

    /**
     *
     * @param code -> code from last (approved) QR CODE detected
     * @return true if code already exists in list
     */
    public static boolean isExist(String code){

        return codeBeanList.stream().anyMatch(codeBean -> codeBean.getName().equals(code));
    }
}