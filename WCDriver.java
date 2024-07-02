import java.io.IOException;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

// The WCDriver class extends Configured and implements Tool interface
public class WCDriver extends Configured implements Tool {

    // The run method is the main entry point for the job execution
    public int run(String[] args) throws IOException {
        // Check if there are at least two arguments (input and output paths)
        if (args.length < 2) {
            System.out.println("Please provide valid inputs");
            return -1; // Return error code if arguments are insufficient
        }

        // Create a new JobConf object for this job
        JobConf conf = new JobConf(WCDriver.class);

        // Set the input and output paths for the job from the command line arguments
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        // Set the Mapper and Reducer classes for the job
        conf.setMapperClass(WCMapper.class);
        conf.setReducerClass(WCReducer.class);

        // Set the output key and value types for the Map phase
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);

        // Set the output key and value types for the Reduce phase
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);

        // Run the job using JobClient
        JobClient.runJob(conf);

        // Return 0 to indicate successful execution
        return 0;
    }

    // The main method is the entry point of the program
    public static void main(String[] args) throws Exception {
        // Run the job and get the exit code
        int exitCode = ToolRunner.run(new WCDriver(), args);

        // Print the exit code
        System.out.println(exitCode);
    }
          }
