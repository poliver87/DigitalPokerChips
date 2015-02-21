package com.bidjee.digitalpokerchips.m;

import java.util.Random;

public class RandomNames {
	
	static String[] names = {"Peter",
		"Fred","Mary","Lucy","Bob",
		"Cara","Dain","Nathan","Halil",
		"Karim","Simon","Ben","Jean",
		"Jono","Dan","James","Todd",
		"Marcus","Michael","Alistair","Dean",
		"Gary","Lyn","Max","Rachel",
		"Jessie","Julia","Luke","Kaitlyn",
		"Jason","Ellen","Eddie","Paul",
		"Hayley","Kyle","Sarmaad","Hasib",
		"Murray","Whitney","Farrah","Tyson",
		"Anup","Rosie","Ronald","Toby",
		"Thai","Connaugh","Chloe","Glen",
		"Mark","David","Amelia","Alex",
		"Danny","Lauren","Felicity","Shin",
		"Gyo","Ellie","Pedram","Kieran",
		"Xavier","Zoe","Elias","Sophie",
		"Vikas","Anne","Alexander","Ross",
		"Daryl","Amy","Dhruv","Claire",
		"Duncan","Gad","Boon","Kristie",
		"Demi","Stewart","Hassan","Leon",
		"Johannes","Michelle","Hannah","Alyce",
		"Aymen","Ilaria","Gonja","Felix",
		"Scott","Oliver","Cirven","Evie"
		
	};
	
	
	public static String getRandomName() {
		int index = new Random().nextInt(names.length);
		String randomName = names[index];		
		return randomName;
	}

}
