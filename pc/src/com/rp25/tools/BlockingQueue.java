package com.rp25.tools;
import java.util.Queue;
//very bad implementation of blockingqueue, which is not included in lejos
public class BlockingQueue<E> implements Queue<E> {

	public BlockingQueue() {
		super();
	}
	@SuppressWarnings("unchecked")
	E take(){
		while (super.empty()){

		}
		return (E) super.pop();
		
	}
}
