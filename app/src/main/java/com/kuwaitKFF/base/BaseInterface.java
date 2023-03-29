package com.kuwaitKFF.base;

/**
 * This interface is for implementing initialization and implementation method
 * in each activity
 * 
 * @author Vimal Rajpara
 * 
 */
public interface BaseInterface {
	/**
	 * This method is used for initialization of all objects like view, list,
	 * adapter and others.
	 */
	void initialization();

	/**
	 * This method is used for actual logic, this method must be called after
	 * initialization.
	 */
	void implementation();
}
