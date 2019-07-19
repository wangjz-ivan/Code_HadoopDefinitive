// cc FileSystemCat Displays files from a Hadoop filesystem on standard output by using the FileSystem directly
//import java.io.InputStream;
import java.net.URI;
import java.io.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

// vv FileSystemCat
public class FileSystemCat {

  public static void main(String[] args) throws Exception {
    String uri = args[0];
    System.out.println(uri);
    Configuration conf = new Configuration();

    Writer writer = new OutputStreamWriter(new FileOutputStream("output.txt"),
            "UTF-8");
    Configuration.dumpConfiguration(conf, writer);

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
