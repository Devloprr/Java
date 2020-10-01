import java.util.concurrent.*;
import java.util.*;

class ForkJoinDemo{
	public static void main(String ar[]){
		ForkJoinPool fjp = new ForkJoinPool();

		double[] nums = new double[100000];
		for(int i=0; i<nums.length; i++)
			nums[i] = (double) i;

		System.out.println("A Portion of the Original Sequence:");

		for(int i=0; i<10; i++)
			System.out.print(nums[i] + " ");
		System.out.println();

		SqrtTransform task = new SqrtTransform(nums, 0, nums.length);

	//	long st = System.currentTimeMillis();
		long st = System.nanoTime();
		fjp.invoke(task);
	//	long et = System.currentTimeMillis();
		long et = System.nanoTime();

		System.out.println("A Portion of the Transform Sequence:");

		for(int i=0; i<10; i++)
			System.out.format("%.4f ", nums[i]);
		System.out.println();
		System.out.println("Processors Available : " + Runtime.getRuntime().availableProcessors());
		System.out.println("Processors Used : " + fjp.getParallelism());
		System.out.println("Completion Time : " + (double)(et - st)/1000000000 + " sec.");
	}
}
class SqrtTransform extends RecursiveAction{
	final int seqthreshold = 1000;
	double[] data;
	int s, e;

	SqrtTransform(double[] val, int s, int e){
		data = val;
		this.s = s;
		this.e = e;
	}
	protected void compute(){
		if((e - s) < seqthreshold){
			for(int i=s; i<e; i++)
				data[i] = Math.sqrt(data[i]);
		}
		else{
			int m = (s + e) / 2;
			invokeAll(new SqrtTransform(data, s, m), new SqrtTransform(data, m, e));
		}
	}
}