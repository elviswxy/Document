package cn.tarena.wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.util.LineReader;

/**
 * дһ���࣬extends RecordReader���൱��ʵ���Զ���������ȡ��
 */
public class AuthRecordReader extends RecordReader<IntWritable, Text>{
	private FileSplit fs;
	private IntWritable key;
	private Text value;
	//ע�⣬������org.apache.hadoop.util.LineReader;
	private LineReader reader;
	private int count=0;

	@Override
	public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
		fs=(FileSplit) split;
		Path path=fs.getPath();
		Configuration conf=new Configuration();
		FileSystem system=path.getFileSystem(conf);
		//�õ��ļ��������������ļ�����
		FSDataInputStream in=system.open(path);
		reader=new LineReader(in);
		
	}
	/*
	 *1.��������ᱻ��ε��ã����˷����ķ���ֵ��true��ʱ�򣬾ͻᱻ����һ�Ρ�
	 *2.ÿ��nextKeyValue()����һ�Σ�getCurrentKey()��getCurrentValue()Ҳ�ᱻ����һ��
	 *3.getCurrentKey()ÿ����һ�Σ������������ķ���ֵ����map������key
	 *4.getCurrentValue()ÿ����һ�Σ������������ķ���ֵ����map������value
	 */
	@Override
	public boolean nextKeyValue() throws IOException, InterruptedException {
		key=new IntWritable();
		value=new Text();
		Text tmp=new Text();
		int result=reader.readLine(tmp);
		if(result==0){
			//֤��û�������ݿɶ�
			return false;
		}else{
			count++;
			key.set(count);
			
			//������ôд��reader.readLine(value)����ô�����൱�ڶ�����
			value=tmp;
			return true;
		}
		
	}

	@Override
	public IntWritable getCurrentKey() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public Text getCurrentValue() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return value;
	}

	@Override
	public float getProgress() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void close() throws IOException {
		if(reader!=null)reader.close();
		
	}

}
