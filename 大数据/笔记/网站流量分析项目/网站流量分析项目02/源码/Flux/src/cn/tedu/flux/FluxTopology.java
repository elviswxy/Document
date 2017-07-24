package cn.tedu.flux;

import java.util.UUID;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;

public class FluxTopology {
	public static void main(String[] args) {
		//SPOUT��id Ҫ��Ψһ
		String KAFKA_SPOUT_ID = "flux_spout";
		//Ҫ���ӵ�kafka��topic
		String CONSUME_TOPIC = "flux_topic";
		//Ҫ���ӵ�zookeeper�ĵ�ַ
		String ZK_HOSTS = "192.168.242.101:2181"; 

		//�趨���ӷ������Ĳ���
		BrokerHosts hosts = new ZkHosts(ZK_HOSTS);
		SpoutConfig spoutConfig = new SpoutConfig(hosts, CONSUME_TOPIC, "/" + CONSUME_TOPIC, UUID.randomUUID().toString());
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);
	   
		//��kafka��ȡ���ݷ���
		TopologyBuilder builder = new TopologyBuilder();
		
		
		builder.setSpout(KAFKA_SPOUT_ID, kafkaSpout);
		//��������
		builder.setBolt("ClearBolt", new ClearBolt()).shuffleGrouping(KAFKA_SPOUT_ID);
		//����pv
		builder.setBolt("PvBolt", new PVBolt()).shuffleGrouping("ClearBolt");
		//����uv
		builder.setBolt("UvBolt", new UvBolt()).shuffleGrouping("PvBolt");
		//����vv
		builder.setBolt("VvBolt", new VvBolt()).shuffleGrouping("UvBolt");
		//����newip
		builder.setBolt("NewIpBolt", new NewIpBolt()).shuffleGrouping("VvBolt");
		//����newcust
		builder.setBolt("NewCustBolt", new NewCustBolt()).shuffleGrouping("NewIpBolt");
		//�����������ص����ݿ���
		builder.setBolt("ToMysqlBolt", new ToMysqlBolt()).shuffleGrouping("NewCustBolt");
		
		builder.setBolt("PrintBolt", new PrintBolt()).shuffleGrouping("NewCustBolt");
		//�����ݳ־û���hbase�м�洢�У��������ʹ��
		builder.setBolt("ToHBaseBolt", new ToHbaseBolt()).shuffleGrouping("NewCustBolt");
		
		StormTopology topology = builder.createTopology();
		
		//--�ύTopology����Ⱥ����
		Config conf = new Config();
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("MyTopology", conf, topology);
		//--����10���Ӻ�ɱ��Topology�رռ�Ⱥ
		Utils.sleep(1000 * 1000);
		cluster.killTopology("MyTopology");
		cluster.shutdown();
	}
}
