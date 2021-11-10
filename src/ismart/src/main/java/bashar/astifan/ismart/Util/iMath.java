package bashar.astifan.ismart.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 *
 *
 * @author Bashar Astifan <br>
 *         <a href=
 *         "astifan.online"
 *         >Read More</a> <br>
 * @version 2.0
 *
 */
public class iMath {
	
	
public static int getRandom(int max,int min){
	Random r = new Random();
	return r.nextInt(max - min + 1) + min;
}
public static int getNearPercent(int value,int target){
	return (value/target)*100;
}
public static List<String>  randomizeArray(ArrayList<String> arr){
	
	long seed = System.nanoTime();
	List<String> indexer = new ArrayList<String>();
	indexer.clear();
	indexer=arr;
	Collections.shuffle(indexer, new Random(seed));
	return indexer;
	
}
public static List<Integer> randomizeArray(List<Integer> arr){
	
	long seed = System.nanoTime();
	List<Integer> indexer = new ArrayList<Integer>();
	indexer.clear();
	indexer=arr;
	Collections.shuffle(indexer, new Random(seed));
	return indexer;
	
}
}
