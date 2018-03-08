package com.rp25.motion.behavior;

public class BehaviorVariable{
	
	public String type;
	
	public BehaviorVariable(){
		this.type = "none";
	}
	
	public void set(String behavior){
		System.out.println("SET AS:"+behavior);
		this.type = behavior;
	}
	
	public void reset(){
		this.type = "none";
	}
	
	public String get(){
		return this.type;
	}
	
	public Boolean isBehavior(){
		if(!this.type.equals("none")){
			return true;
		}
		return false;
	}
}
