package com.example.shang.xmlparsetest.parser;

import com.example.shang.xmlparsetest.Bean.Book;

import java.io.InputStream;
import java.util.List;

/**
 * Created by shang on 2017/7/18.
 */

//  公共接口
public interface BookParser {

    // 解析输入流，得到Book对象集合
    List<Book> parse(InputStream is) throws Exception;

    // 序列化Book对象集合，得到XML形式的字符串,方便写入为文件
    String serialize(List<Book> books)throws Exception;

}
