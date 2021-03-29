package com.epsi.gostyle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.epsi.gostyle.bean.CodeBean;

public class CodeActivity extends AppCompatActivity {

    ListView ltCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        //Items de l'Activity
        ltCode = (ListView)findViewById(R.id.ltCode);

        //Création des objets à lister
        CodeBean test1 = new CodeBean("Test1",15);
        CodeBean test2 = new CodeBean("Test2",30);
        CodeBean test3 = new CodeBean("Test3",50);

        //Ajout des objets dans une liste
        CodeBean[] codes = new CodeBean[]{test1,test2, test3};

        //Utilisation d'une liste pour l'adapter à l'item de l'Activity
        ArrayAdapter<CodeBean> arrayAdapter
                = new ArrayAdapter<CodeBean>(this, android.R.layout.simple_list_item_1 , codes);

        //Ajout de l'adaptation dans l'item
        ltCode.setAdapter(arrayAdapter);
    }
}