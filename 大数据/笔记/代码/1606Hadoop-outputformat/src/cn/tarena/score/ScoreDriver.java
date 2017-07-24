package cn.tarena.score;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;



public class ScoreDriver {
	public static void main(String[] args) throws Exception {
		Configuration conf=new Configuration();
		Job job=Job.getInstance(conf);
		
		job.setJarByClass(ScoreDriver.class);
		job.setMapperClass(ScoreMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		job.setReducerClass(ScoreReducer.class);
		//job.setOutputKeyClass(Text.class);
		//job.setOutputValueClass(Text.class);
		
		job.setOutputFormatClass(AuthOutputformat.class);
		
		//ע�⣬Hadoop�����Դ����Ȼ���ɶ������ļ�������ʵ�ֵ�˼�벻�Ƿ��������Ҫ���𿪣���Ϊ��reduce�׶η�����
		MultipleOutputs.addNamedOutput(job,"tominfo",TextOutputFormat.class, 
				Text.class, Text.class);
		MultipleOutputs.addNamedOutput(job,"roseinfo",AuthOutputformat.class, 
				Text.class, Text.class);
		MultipleOutputs.addNamedOutput(job,"otherinfo",AuthOutputformat.class, 
				Text.class, Text.class);
		
		
		
		
		FileInputFormat.setInputPaths(job,new Path("hdfs://192.168.234.191:9000/score"));
		FileOutputFormat.setOutputPath(job,new Path("hdfs://192.168.234.191:9000/score/result"));
		
		job.waitForCompletion(true);
		
	}
}
