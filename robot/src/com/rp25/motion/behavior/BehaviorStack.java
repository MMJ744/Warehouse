package com.rp25.motion.behavior;

import java.util.Stack;

public class BehaviorStack{
	
	Stack stack;
	
	public BehaviorStack(){
		this.stack = new Stack();
	}
	
	public String peek(){
		return (String) this.stack.peek();
	}
	
	public String pop(){
		return (String) this.stack.pop();
	}
	
	public void add(String behavior){
		this.stack.add(behavior);
	}
	
	public Boolean isEmpty(){
		return this.stack.isEmpty();
	}
	
}
