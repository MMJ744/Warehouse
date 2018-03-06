package com.rp25.motion.behavior;

import java.util.ArrayList;

public class BehaviorStack{
	
	ArrayList<String> stack;
	
	public BehaviorStack(){
		this.stack = new ArrayList<String>();
	}
	
	public Boolean isEmpty(){
		return (this.stack.size() == 0);
	}
	
	public String peek(){
		return this.stack.get(0);
	}
	
	public String pop(){
		if(!isEmpty()){
			return this.stack.remove(0);
		}
		return null;
	}
	
	public void add(String behavior){
		ArrayList<String> tempList = new ArrayList<String>();
		tempList.add(behavior);
		tempList.addAll(this.stack);
		this.stack = tempList;
	}

	
}
