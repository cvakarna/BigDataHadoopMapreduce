package com.siva.hadoop.training.maxwordinafilewithcustomkey;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MaxLengthWordInaFilesJob implements Tool {

	// intializing the configuration obj
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
		Job maxLengthWordInaFilesKeyJob = new Job(getConf());

		// setting the job name
		maxLengthWordInaFilesKeyJob.setJobName("Orien IT MaxLengthWordInaFilesKey Job");

		// to call this as a jar
		maxLengthWordInaFilesKeyJob.setJarByClass(this.getClass());

		// setting custom mapper class
		maxLengthWordInaFilesKeyJob.setMapperClass(MaxLengthWordInaFilesKeyMapper.class);

		// setting custom reducer class
		maxLengthWordInaFilesKeyJob.setReducerClass(MaxLengthWordInaFilesKey1Reducer.class);

		// setting custom combiner class
		// maxLengthWordInaLineJob.setCombinerClass(WordCountReducer.class);

		// setting no of reducers
		// maxLengthWordInaFileJob.setNumReduceTasks(0);

		// setting custom partitioner class
		// maxLengthWordInaFilesJob.setPartitionerClass(WordCountPartitioner.class);

		// setting mapper output key class: K2
		maxLengthWordInaFilesKeyJob.setMapOutputKeyClass(Text.class);

		// setting mapper output value class: V2
		maxLengthWordInaFilesKeyJob.setMapOutputValueClass(Text.class);

		// setting reducer output key class: K3
		maxLengthWordInaFilesKeyJob.setOutputKeyClass(MaxLengthWordInaFilesKey.class);

		// setting reducer output value class: V3
		maxLengthWordInaFilesKeyJob.setOutputValueClass(NullWritable.class);

		// setting the input format class ,i.e for K1, V1
		maxLengthWordInaFilesKeyJob.setInputFormatClass(TextInputFormat.class);

		// setting the output format class
		maxLengthWordInaFilesKeyJob.setOutputFormatClass(TextOutputFormat.class);

		// setting the input file path
		FileInputFormat.addInputPath(maxLengthWordInaFilesKeyJob, new Path(args[0]));

		// setting the output folder path
		FileOutputFormat.setOutputPath(maxLengthWordInaFilesKeyJob, new Path(args[1]));

		Path outputpath = new Path(args[1]);
		// delete the output folder if exists
		outputpath.getFileSystem(conf).delete(outputpath, true);

		// to execute the job and return the status
		return maxLengthWordInaFilesKeyJob.waitForCompletion(true) ? 0 : -1;

	}

	public static void main(String[] args) throws Exception {
		// start the job providing arguments and configurations
		int status = ToolRunner.run(new Configuration(), new MaxLengthWordInaFilesJob(), args);
		System.out.println("My Status: " + status);
	}

}
