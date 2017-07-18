package com.example.shang.xmlparsetest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shang.xmlparsetest.Bean.Book;
import com.example.shang.xmlparsetest.parser.BookParser;
import com.example.shang.xmlparsetest.parser.DOMParser;
import com.example.shang.xmlparsetest.parser.PULLParser;
import com.example.shang.xmlparsetest.parser.SAXParser;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button SAXbtn1,SAXbtn2,DOMbtn1,DOMbtn2,PULLbtn1,PULLbtn2;
    BookParser bookParser;
    List<Book>books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        SAXbtn1.setOnClickListener(this);
        SAXbtn2.setOnClickListener(this);
        DOMbtn1.setOnClickListener(this);
        DOMbtn2.setOnClickListener(this);
        PULLbtn1.setOnClickListener(this);
        PULLbtn2.setOnClickListener(this);
    }

    private void initView() {
        SAXbtn1 = (Button) findViewById(R.id.SAXread);
        SAXbtn2 = (Button) findViewById(R.id.SAXwriter);
        DOMbtn1 = (Button) findViewById(R.id.DOMread);
        DOMbtn2 = (Button) findViewById(R.id.DOMwriter);
        PULLbtn1 = (Button) findViewById(R.id.PULLread);
        PULLbtn2 = (Button) findViewById(R.id.PULLwriter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SAXread:
                try {
                    InputStream is = getAssets().open("books.xml");
                    bookParser = new SAXParser();
                    books = bookParser.parse(is);
                    for (Book book:books){
                        Log.i("xyz",book.toString());
                    }
                    Toast.makeText(MainActivity.this,"读取成功，请看控制台输出。",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("xyz",e.getMessage());
                }
                break;

            case R.id.SAXwriter:
                try {
                    String xml = bookParser.serialize(books);
                    FileOutputStream fos = openFileOutput("booksshuchu.xml", Context.MODE_PRIVATE);
                    fos.write(xml.getBytes("utf-8"));
                    Toast.makeText(MainActivity.this,"写入成功，请打开DDMS查看",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case R.id.DOMread:
                try {
                    InputStream is = getAssets().open("books.xml");
                    bookParser = new DOMParser();
                    books = bookParser.parse(is);
                    for (Book book:books){
                        Log.i("xyz",book.toString());
                    }
                    Toast.makeText(MainActivity.this,"读取成功，请看控制台输出。",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("xyz",e.getMessage());
                }

                break;

            case R.id.DOMwriter:
                try {
                    String xml = bookParser.serialize(books);
                    FileOutputStream fos = openFileOutput("booksshuchu.xml", Context.MODE_PRIVATE);
                    fos.write(xml.getBytes("utf-8"));
                    Toast.makeText(MainActivity.this,"写入成功，请打开DDMS查看",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.PULLread:
                try {
                    InputStream is = getAssets().open("books.xml");
                    bookParser = new PULLParser();
                    books = bookParser.parse(is);
                    for (Book book:books){
                        Log.i("xyz",book.toString());
                    }
                    Toast.makeText(MainActivity.this,"读取成功，请看控制台输出。",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("xyz",e.getMessage());
                }

                break;

            case R.id.PULLwriter:
                try {
                    String xml = bookParser.serialize(books);
                    FileOutputStream fos = openFileOutput("booksshuchu.xml", Context.MODE_PRIVATE);
                    fos.write(xml.getBytes("utf-8"));
                    Toast.makeText(MainActivity.this,"写入成功，请打开DDMS查看",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }



}

