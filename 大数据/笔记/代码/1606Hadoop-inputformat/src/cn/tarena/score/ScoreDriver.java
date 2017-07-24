package cn.tarena.score;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import cn.tarena.wordcount.AuthInputFormat;
import cn.tarena.wordcount.WordDriver;
import cn.tarena.wordcount.WordMapper;

public class ScoreDriver {

	public static void main(String[] args) throws Exception {
		Configuration conf=new Configuration();
		Job job=Job.getInstance(conf);
		
		job.setJarByClass(ScoreDriver.class);
		//job.setMapperClass(ScoreMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		//job.setInputFormatClass(ScoreInputFormat.class);
		
		//Hadoop ������Դʹ������˵����
		//1.����ָ��job.setMapperClass(ScoreMapper.class)�����������Ϊ�ж��mapper
		//2.setInputFormatClass(ScoreInputFormat.class)�������Ҳû�����壬���Բ��õ���
		//3.FileInputFormat.setInputPaths Ҳ���õ�����
		//4.���Mapper�����key��value�ķ���Ҫһ��
		MultipleInputs.addInputPath(job, new Path("hdfs://192.168.234.191:9000/score/score.txt"),
				ScoreInputFormat.class,ScoreMapper.class);
		
		MultipleInputs.addInputPath(job, new Path("hdfs://192.168.234.191:9000/score/score1.txt"),
				TextInputFormat.class,ScoreMapper1.class);
		
		
		
		//FileInputFormat.setInputPaths(job,new Path("hdfs://192.168.234.191:9000/score"));
		FileOutputFormat.setOutputPath(job,new Path("hdfs://192.168.234.191:9000/score/result"));
		
		job.waitForCompletion(true);
		
	}
}
