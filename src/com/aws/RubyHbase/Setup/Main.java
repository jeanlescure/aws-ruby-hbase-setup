package com.aws.RubyHbase.Setup;

import com.aws.RubyHbase.util.ProcessList;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        ProcessList pList = new ProcessList();
        File defaultFile = new File("/home/hadoop");
        
        // Install Git
        pList.add(  new ProcessBuilder("sudo", "yum", "install", "-y", "git"),
                    defaultFile    );
        
        // Install JRuby 1.7.12
        pList.add(  new ProcessBuilder("sudo", "wget", "https://gist.githubusercontent.com/jeanlescure/a64dd817680dfd4b2bd2/raw/e5bf462f980e5b803bf4fab86b1a48a18a6cc931/aws-maven.repo", "-O", "/etc/yum.repos.d/epel-apache-maven.repo"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("sudo", "yum", "install", "-y", "apache-maven"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("wget", "https://github.com/jruby/jruby/archive/1.7.12.tar.gz"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("tar", "-zxvf", "1.7.12.tar.gz"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("mvn", "package"),
                    new File("/home/hadoop/jruby-1.7.12")   );
                    
        // Get and setup Ruby HBase daemon from GitHub
        pList.add(  new ProcessBuilder("git", "clone", "https://github.com/jeanlescure/aws-ruby-hbase-daemon.git"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("cp", "-f", "aws-ruby-hbase-daemon/bin/*", "hbase/bin/"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("cp", "-r", "aws-ruby-hbase-daemon/ruby/daemon", "hbase/lib/ruby/"),
                    defaultFile   );
        
        // Install necessary gems
        pList.add(  new ProcessBuilder("sudo", "gem", "install", "tilt", "-v", "1.4.1"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("sudo", "gem", "install", "rack", "-v", "1.5.2"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("sudo", "gem", "install", "rack-protection", "-v", "1.5.3"),
                    defaultFile   );
        pList.add(  new ProcessBuilder("sudo", "gem", "install", "sinatra", "-v", "1.4.5"),
                    defaultFile   );
        
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