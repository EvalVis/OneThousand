package ev.projects.onethousand.utils;

import java.util.List;

public class Misc {
	
	public static void sleep() {
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sleep(int milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static Integer[] ListToArray(List<Integer> list) {
		Integer[] array = new Integer[list.size()];
		for(int i = 0; i < list.size(); i++) array[i] = list.get(i);
		return array;
	}

}
