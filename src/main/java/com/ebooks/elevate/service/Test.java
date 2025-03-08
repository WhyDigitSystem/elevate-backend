package com.ebooks.elevate.service;

import java.math.BigInteger;
import java.util.Scanner;

public class Test {

	public static void main(String args[])
	
	{
		Scanner sc= new Scanner(System.in);
		int age=23;
		String name="Justin";
		System.out.printf("Hi My name is %s, and i my Age is %d",name,+age);
		
		double x= 10000.0/3.0;
		System.out.println(x);
		
		System.out.printf("%4.2f",x);
		
		BigInteger ab=BigInteger.valueOf(1000123459);
		System.out.println(ab);
		
		
		
	}
}
