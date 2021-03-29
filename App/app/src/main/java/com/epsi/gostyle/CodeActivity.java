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

        //Items de l'Activity
        listView = (ListView)findViewById(R.id.ltCode);

        //Utilisation d'une liste pour l'adapter à l'item de l'Activity
        ArrayAdapter<CodeBean> arrayAdapter = new ArrayAdapter<CodeBean>(this, android.R.layout.simple_list_item_1 , codeBeanList);

        //Ajout de l'adaptation dans l'item
        listView.setAdapter(arrayAdapter);
    }

    public static boolean isExist(String code){

        return codeBeanList.stream().anyMatch(codeBean -> codeBean.getName().equals(code));
    }
}