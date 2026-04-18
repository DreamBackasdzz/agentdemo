package com.example.agentdemo.utils;

import org.apache.tika.Tika;

import java.io.File;

public class TikaUtil {
    public static String parseFile(File file) throws Exception{
        Tika tika = new Tika();
        return tika.parseToString(file);
    }
}
