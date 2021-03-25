package EPSI.mspr;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class CodeActivity extends AppCompatActivity{

    private ListView ltCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        ltCode = (ListView)findViewById(R.id.ltCode);

        CodeBean test1 = new CodeBean("Test1",15);
        CodeBean test2 = new CodeBean("Test2",30);
        CodeBean test3 = new CodeBean("Test3",50);

        CodeBean[] codes = new CodeBean[]{test1,test2, test3};

        ArrayAdapter<CodeBean> arrayAdapter
                = new ArrayAdapter<CodeBean>(this, android.R.layout.simple_list_item_1 , codes);

        ltCode.setAdapter(arrayAdapter);
    }
}
