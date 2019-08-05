package org.jstl.compiler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "start")
public class Stopwatch {

	private final long start = System.nanoTime();
	
	public double elapsedMillis() {
		
		long end = System.nanoTime();
		
		return ((end - start) / 1000000.0D);
	}
}
