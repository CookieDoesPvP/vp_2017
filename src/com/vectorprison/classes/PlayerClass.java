package com.vectorprison.classes;

import java.util.HashSet;
import java.util.Set;

import com.vectorprison.account.Account;
import com.vectorprison.quests.Quest;

public class PlayerClass {
	
	enum ClassType {
		Limbo, Miner, Trader, Warrior, Guard
	}
	
	public static Set<PlayerClass> classes = new HashSet<>();
	
	private String className;
	private ClassType classType; 
	
	public PlayerClass(String name, ClassType type) {
		
		className = name;
		classType = type;
		
		classes.add(this);
	}
	
    public static PlayerClass getPlayerClass(String classname) {
        for (PlayerClass pclass : classes) {
            if (pclass.getClassName().equals(classname)) {
                return pclass;
            }
        }
        return null;
    }
	
	public void setClassType(ClassType type) {
		classType = type;
	}
	
	public ClassType getClassType() {
		return classType;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String s) {
		className = s;
	}

}
