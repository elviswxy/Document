package cn.tarena.common;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import rpc.domain.HttpAppHost;

public class OwnEnv {

	private static BlockingQueue<Map<CharSequence,HttpAppHost>> mapQueue=new LinkedBlockingQueue<>();

	public static BlockingQueue<Map<CharSequence, HttpAppHost>> getMapQueue() {
		return mapQueue;
	}


	

	
	
	
}
