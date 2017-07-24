package cn.tedu.trident;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import storm.trident.Stream;
import storm.trident.TridentState;
import storm.trident.TridentTopology;
import storm.trident.testing.FixedBatchSpout;
/**
 * State�洢���ݰ���
 */
public class TridentDemo6 {
	public static void main(String[] args) {
		//--����topology
		TridentTopology topology = new TridentTopology();
		

		/**
		 * �������ݵ�State 
		 * 	���Խ����ݳ־û���Stateָ����λ��
		 * 	State��������ṩ�Ժ���Ҫʱ�Ĳ�ѯ
		 */
		Stream s = topology.newStream("xx", new SentenceSpout())
			.partitionAggregate(new Fields("name"), new SentenceAggerator(), new Fields("name","count"))	
			;
		TridentState state = s.partitionPersist(new MyStateFactory(), new Fields("name","count"), new MyStateUpdater());
		
		/**
		 * ��ѯstate
		 */
		FixedBatchSpout spout2 = new FixedBatchSpout(new Fields("qname"), 1, new Values("xiaoming"),new Values("xiaohua"));
		spout2.setCycle(true);
		Stream s2 = topology.newStream("yy",spout2);
		s2 = s2.stateQuery(state, new Fields("qname"), new MyStateQuery(), new Fields("name","count"));
		s2.each(s2.getOutputFields(), new PrintFilter());
		
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
