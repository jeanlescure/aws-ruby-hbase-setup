package com.aws.RubyHbase.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

public class ProcessList {
    private ArrayList<ArrayList> pList;

    public ProcessList(){
        pList = new ArrayList<ArrayList>();
    }
    
    public Iterator<ArrayList> iterator(){
        return pList.iterator();
    }
    
    public void add(ProcessBuilder pb, File f){
        ArrayList<Object> aList = new ArrayList<Object>();
        aList.add(pb);
        aList.add(f);
        pList.add(aList);
    }
}