package com.tosan.ymsg;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class YmsgDefaults {
	public static final int PORT = 5050;

	private static Set<String> SERVERS ;
//	= new HashSet<String>(Arrays.asList(
//			"scs.msg.yahoo.com", "scsa.msg.yahoo.com", "scsc.msg.yahoo.com"));

	public static Set<String> getServers() {
		// TODO = delete it
		 if (SERVERS == null) {
		 SERVERS = new HashSet<String>();
		 SERVERS.add("scs.msg.yahoo.com");
		 SERVERS.add("scsa.msg.yahoo.com");
		 SERVERS.add("scsc.msg.yahoo.com");
		 }
		return SERVERS;
	}

}
