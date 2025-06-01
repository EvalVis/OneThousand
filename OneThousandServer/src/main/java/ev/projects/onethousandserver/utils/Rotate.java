package ev.projects.onethousandserver.utils;

import ev.projects.onethousandserver.server.ServerMain;

public class Rotate {
	
	public static int passCall(int current, boolean[] callers) {
		int next = current;
		do {
		if(next >= (ServerMain.userCount - 1)) {
			next = 0;
		}
		else next++;
		}
		while(!callers[next]);
		return next;
	}
	
	public static int passPlay(int current) {
		if(current >= (ServerMain.userCount - 1)) return 0;
		return current + 1;
	}

}
