// cc FileSystemCat Displays files from a Hadoop filesystem on standard output by using the FileSystem directly
//import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.io.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

// vv FileSystemCat
public class FileSystemCat {

  public static void main(String[] args) throws Exception {
//    String uri = args[0];
//    String uri = "hdfs://localhost/testFile2.txt";  // error 
    String uri = "hdfs://node1:9000/testFile2.txt";
    System.out.println(uri);
    Configuration conf = new Configuration();

    Writer writer = new OutputStreamWriter(new FileOutputStream("output.txt"),
            "UTF-8");
    Configuration.dumpConfiguration(conf, writer);

    Class cls = conf.getClass();
    Field f = cls.getDeclaredField("defaultResources");
    f.setAccessible(true);
    // 如果相关的类设置了 SecurityManage，就会报错，Java核心类实现了这个方法，
    // 阻止私有变量被改变。
    System.out.println(f.get(conf));

//    System.out.println(Configuration.class.getField(null));

    FileSystem fs = FileSystem.get(URI.create(uri), conf);
    InputStream in = null;
    try {
      in = fs.open(new Path(uri));
      IOUtils.copyBytes(in, System.out, 4096, false);
    } finally {
      IOUtils.closeStream(in);
    }
  }
}
// ^^ FileSystemCat
