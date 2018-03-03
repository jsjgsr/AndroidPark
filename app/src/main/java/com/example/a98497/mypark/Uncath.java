package com.example.a98497.mypark;

import java.lang.Thread.UncaughtExceptionHandler;

public class Uncath implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// TODO Auto-generated method stub
		ex.printStackTrace();
		System.exit(0);
	}

}
