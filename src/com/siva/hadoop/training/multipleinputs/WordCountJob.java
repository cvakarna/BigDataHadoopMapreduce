package com.siva.hadoop.training.multipleinputs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountJob implements Tool {
	// Initializing configuration object
	private Configuration conf;

	@Override
	public Configuration getConf() {
		return conf; // getting the configuration
	}

	@Override
	public void setConf(Configuration conf) {
		this.conf = conf; // setting the configuration
	}

	@Override
	public int run(String[] args) throws Exception {

		// initializing the job configuration
		Job wordCountJob = new Job(getConf());

		// setting the job name
		wordCountJob.setJobName("Orien IT WordCount Job");

		// to call this as a jar
		wordCountJob.setJarByClass(this.getClass());

		// setting custom mapper class
		// wordCountJob.setMapperClass(WordCountMapper.class);

		// setting custom reducer class
		wordCountJob.setReducerClass(WordCountReducer.class);

		// setting custom combiner class
		// wordCountJob.setCombinerClass(WordCountReducer.class);

		// setting no of reducers
		// wordCountJob.setNumReduceTasks(2);

		// setting custom partitioner class
		// wordCountJob.setPartitionerClass(WordCountPartitioner.class);

		// setting mapper output key class: K2
		wordCountJob.setMapOutputKeyClass(Text.class);

		// setting mapper output value class: V2
		wordCountJob.setMapOutputValueClass(LongWritable.class);

		// setting reducer output key class: K3
		wordCountJob.setOutputKeyClass(Text.class);

		// setting reducer output value class: V3
		wordCountJob.setOutputValueClass(LongWritable.class);

		// setting the input format class ,i.e for K1, V1
		// wordCountJob.setInputFormatClass(TextInputFormat.class);

		// setting the output format class
		wordCountJob.setOutputFormatClass(TextOutputFormat.class);

		MultipleInputs.addInputPath(wordCountJob, new Path(args[0]), TextInputFormat.class, WordCountSpaceMapper.class);
		MultipleInputs.addInputPath(wordCountJob, new Path(args[1]), KeyValueTextInputFormat.class, WordCountTabMapper.class);

		// setting the input file path
		// FileInputFormat.addInputPath(wordCountJob, new Path(args[0]));

		// setting the output folder path
		FileOutputFormat.setOutputPath(wordCountJob, new Path(args[2]));

		Path outputpath = new Path(args[2]);
		// delete the output folder if exists
		outputpath.getFileSystem(conf).delete(outputpath, true);

		// to execute the job and return the status
		return wordCountJob.waitForCompletion(true) ? 0 : -1;

	}

	public static void main(String[] args) throws Exception {
		// start the job providing arguments and configurations
		int status = ToolRunner.run(new Configuration(), new WordCountJob(), args);
		System.out.println("My Status: " + status);
	}

}
