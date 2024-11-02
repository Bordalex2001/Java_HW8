package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Program {
	private static final int arrSize = 100;
	private static final int numTasks = 10;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		int[] arr = new int[arrSize];
		for (int i = 0; i < arrSize; i++) {
			arr[i] += i + 1;
		}

		int chunk = arrSize / numTasks;
		List<CompletableFuture<Long>> futures = new ArrayList<>();

		for (int i = 0; i < numTasks; i++) {
			int start = i * chunk;
			int end = (i == numTasks - 1) ? arrSize : start + chunk;

			CompletableFuture<Long> future = CompletableFuture.supplyAsync(() -> sumArrSection(arr, start, end));
			futures.add(future);
		}

		long total = 0;
		for (CompletableFuture<Long> future : futures) {
			total += future.get();
		}

		System.out.println("Total amount of futures: " + total);
	}

	private static long sumArrSection(int[] arr, int start, int end) {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += arr[i];
		}
		return sum;
	}
}