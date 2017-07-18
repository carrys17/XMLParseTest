package com.example.shang.xmlparsetest.parser;

import android.util.Xml;

import com.example.shang.xmlparsetest.Bean.Book;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shang on 2017/7/18.
 */

public class PULLParser implements BookParser {
    @Override
    public List<Book> parse(InputStream is) throws Exception {
        Book book = null;
        List<Book>books = null;

        XmlPullParser pullParser = Xml.newPullParser();     //由android.util.Xml创建一个XmlPullParser实例  
        pullParser.setInput(is,"utf-8");                    //设置输入流 并指明编码方式  

        int eventType = pullParser.getEventType();
        while (eventType!= XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    books = new ArrayList<>();
                    break;
                case XmlPullParser.START_TAG:
                    if (pullParser.getName().equals("book")) {
                        book = new Book();
                    }else if (pullParser.getName().equals("id")){
                        eventType = pullParser.next();
                        book.setId(Integer.parseInt(pullParser.getText()));
                    }else if (pullParser.getName().equals("name")){
                        eventType = pullParser.next();
                        book.setName(pullParser.getText());
                    }else if (pullParser.getName().equals("price")){
                        eventType = pullParser.next();
                        book.setPrice(Float.parseFloat(pullParser.getText()));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (pullParser.getName().equals("book")){
                        books.add(book);
                        book = null;
                    }
                    break;
            }
            eventType = pullParser.next();
        }
        return books;
    }

    @Override
    public String serialize(List<Book> books) throws Exception {
        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        serializer.setOutput(writer);
        serializer.startDocument("utf-8",true); // 1
        serializer.startTag("","books");

        for (Book book:books){
            serializer.startTag("","book");//2
            serializer.attribute("","id",book.getId()+"");

            serializer.startTag("","name");
            serializer.text(book.getName());
            serializer.endTag("","name");

            serializer.startTag("","price");
            serializer.text(book.getPrice()+"");
            serializer.endTag("","price");

            serializer.endTag("","book");//2

        }
        serializer.endTag("","books");
        serializer.endDocument(); // 1

        return writer.toString();
    }
}
