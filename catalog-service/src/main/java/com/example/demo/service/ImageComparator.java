package com.example.demo.service;

import java.util.Comparator;

import com.example.demo.dto.PictureDTO;
import com.example.demo.entity.Image;

public class ImageComparator implements Comparator<PictureDTO>{
	private String compareParam;
	private String compareOrder;
	private int compareResult = 0;
	public ImageComparator(String compareParam, String order) {
		this.compareParam = compareParam;
		this.compareOrder = order;
	}
	
	@Override
	public int compare(PictureDTO o1, PictureDTO o2) {
		switch (compareParam){
			case "name": {
				compareResult = o1.getName().compareTo(o2.getName());
			}
			case "date": {
				//дописати
			}case "rate": {
				//дописати
			}case "price": {
				//дописати
			}
		}
		if(compareOrder.equals("ASC")) {
			return compareResult;
		}
		return compareResult * -1;
	}
}
