package com.example.sharangirdhani.homework03;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by sharangirdhani on 9/22/17.
 */

public class Occurence implements Serializable {
    private String keyword;
    private int lineNum;
    private int lineIndex;
    private int color;
    private String finalValue;

    final static String[] colors = {"Red", "Blue", "Green"};

    public Occurence(String keyword, int lineNum, int lineIndex, int color, String finalValue) {
        this.keyword = keyword;
        this.lineNum = lineNum;
        this.lineIndex = lineIndex;
        this.color = color;
        this.finalValue = finalValue;
    }

    public String getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(String finalValue) {
        this.finalValue = finalValue;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Occurence{" +
                "keyword='" + keyword + '\'' +
                ", lineNum=" + lineNum +
                ", lineIndex=" + lineIndex +
                ", color=" + colors[color] +
                ", finalValue='" + finalValue + '\'' +
                '}';
    }

    public static Comparator<Occurence> comp = new Comparator<Occurence>() {
        @Override
        public int compare(Occurence o1, Occurence o2) {
            if(o1.lineNum != o2.lineNum) {
                return o1.lineNum - o2.lineNum;
            }
            else {
                return o1.lineIndex - o2.lineIndex;
            }
        }
    };
}
