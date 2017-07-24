package cn.tedu.trident;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.operation.builtin.Count;
/**
 * �����������
 */
public class TridentDemo4 {
	public static void main(String[] args) {
		//--����topology
		TridentTopology topology = new TridentTopology();
		
		//TODO
		Stream s = topology.newStream("xx", new SentenceSpout())
				.groupBy(new Fields("name"))
				.aggregate(new Count(),new Fields("count"))
			;
			
			s.each(s.getOutputFields(), new PrintFilter());
		
		
		//--�ύ����Ⱥ������
		Config conf = new Config();
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("MyTopology", conf, topology.build());
		
		//--����10���Ӻ�ɱ��Topology�رռ�Ⱥ
		Utils.sleep(1000 * 10);
		cluster.killTopology("MyTopology");
		cluster.shutdown();
		
	}
}
