package com.example.agentdemo.utils;

import java.util.ArrayList;
import java.util.List;

public class ChunkUtil {
    public static List<String> splitText(String text, int size, int overlap){
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while(start < text.length()){
            int end = Math.min(start + size, text.length());
            chunks.add(text.substring(start, end));
            start = end - overlap;
        }
        return chunks;
    }
}
