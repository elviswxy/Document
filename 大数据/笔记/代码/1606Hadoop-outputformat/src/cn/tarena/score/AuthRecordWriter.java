package cn.tarena.score;

import java.io.IOException;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

public class AuthRecordWriter<K,V> extends RecordWriter<K, V> {
	
	private FSDataOutputStream out;
	private String keyValueSeparator;
	private String lineSeparator;

	public AuthRecordWriter(FSDataOutputStream out, String string, String string2) {
		this.out=out;
		this.keyValueSeparator=string;
		this.lineSeparator=string2;
	}
	
	//key �������key�����job������û��reduce��key����mapper���key
	//�����reduce��keyʱreduce�����key
	//���value
	//ע�⣺д��ʱ��ע��˳��key �ָ�� value �зָ��
	//����Ǿ��������ĸ�ʽ�ģ����mapper����Ľ���ļ�ͬ����Ч
	@Override
	public void write(K key, V value) throws IOException, InterruptedException {
		out.write(key.toString().getBytes());
		out.write(keyValueSeparator.getBytes());
		out.write(value.toString().getBytes());
		out.write(lineSeparator.getBytes());
		
	}

	@Override
	public void close(TaskAttemptContext context) throws IOException, InterruptedException {
		out.close();
		
	}

}
