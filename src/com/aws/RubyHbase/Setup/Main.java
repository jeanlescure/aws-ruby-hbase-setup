package com.aws.RubyHbase.Setup;

import com.aws.RubyHbase.util.ProcessList;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ProcessList pList = new ProcessList();
        
        // TODO: define real steps necessary to setup Ruby HBase
        
        // Install Git *test
        pList.add(  new ProcessBuilder("sudo", "yum", "install", "-y", "git"),
                    new File("/home/hadoop")    );
        
        // Run Git Clone *test
        pList.add(  new ProcessBuilder("git", "clone", "https://github.com/jeanlescure/hipster_sql_to_hbase.git"),
                    new File("/home/hadoop/ruby-hbase-setup")   );
        
        for(Iterator<ArrayList> i = pList.iterator(); i.hasNext(); ) {
            ArrayList<Object> iList = i.next();
            ProcessBuilder pb = ProcessBuilder.class.cast(iList.get(0));
            File f = File.class.cast(iList.get(1));
            pb.directory(f);
            
            try {
                Process p = pb.start();
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}