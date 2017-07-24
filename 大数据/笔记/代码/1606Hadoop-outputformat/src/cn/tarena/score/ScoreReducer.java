package cn.tarena.score;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

public class ScoreReducer extends Reducer<Text, Text, Text, Text>{
	//ע�ⷺ�ͺ�reducer�ĵ������͵��ĸ����Ͷ�Ӧ��
	//ע�����Ҫ����
	private MultipleOutputs<Text,Text> mos;
	
	@Override
	protected void setup(Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
		mos=new MultipleOutputs<>(context);
	}
	

	@Override
	protected void reduce(Text name, Iterable<Text> values, Reducer<Text, Text, Text, Text>.Context context)
			throws IOException, InterruptedException {
		
		for(Text value:values){
			if(name.toString().equals("tom")){
				mos.write("tominfo",name, value);
			}
			else if(name.toString().equals("rose")){
				mos.write("roseinfo", name, value);
			}else {
				mos.write("otherinfo", name, value);
			}
		}
		
	}
}
