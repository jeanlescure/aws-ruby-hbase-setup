package com.aws.RubyHbase.Setup;

import com.aws.RubyHbase.util.ProcessList;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;
import java.io.Console;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        ProcessList pList = new ProcessList();
        File defaultFile = new File("/home/hadoop");
        
        // Install Git
        pList.add(  new ProcessBuilder("sudo", "yum", "install", "-y", "git"),
                    defaultFile    );
        
        // Install JRuby 1.7.12
        pList.add(  new ProcessBuilder("wget", "https://s3-us-west-2.amazonaws.com/ruby-hbase/jruby-1.7.12.tar.gz"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("tar", "-zxf", "jruby-1.7.12.tar.gz"),
                    defaultFile   );
                    
        // Get and setup Ruby HBase daemon from GitHub
        pList.add(  new ProcessBuilder("git", "clone", "https://github.com/jeanlescure/aws-ruby-hbase-daemon.git"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("cp", "-f", "aws-ruby-hbase-daemon/bin/hbase", "hbase/bin/hbase"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("cp", "-f", "aws-ruby-hbase-daemon/bin/rb-daemon.rb", "hbase/bin/rb-daemon.rb"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("cp", "-r", "aws-ruby-hbase-daemon/ruby/daemon", "hbase/lib/ruby/"),
                    defaultFile   );
        
        // Install necessary gems
        pList.add(  new ProcessBuilder("sudo", "gem", "install", "tilt", "-v", "1.4.1", "--no-ri", "--no-rdoc"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("sudo", "gem", "install", "rack", "-v", "1.5.2", "--no-ri", "--no-rdoc"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("sudo", "gem", "install", "rack-protection", "-v", "1.5.3", "--no-ri", "--no-rdoc"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("sudo", "gem", "install", "sinatra", "-v", "1.4.5", "--no-ri", "--no-rdoc"),
                    defaultFile   );
                    
        PrintWriter out = System.console().writer();
        
        for(Iterator<ArrayList> i = pList.iterator(); i.hasNext(); ) {
            ArrayList<Object> iList = i.next();
            ProcessBuilder pb = ProcessBuilder.class.cast(iList.get(0));
            File f = File.class.cast(iList.get(1));
            pb.directory(f);
            pb.redirectErrorStream(true);
            
            out.println(pb.command().get(0).concat(" ".concat(pb.command().get(1).concat("..."))));
            
            try {
                Process p = pb.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) out.println("Task: " + line);
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}