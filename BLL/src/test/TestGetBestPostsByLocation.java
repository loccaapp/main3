package test;

import helper.OperationResult;
import managers.PostManager;

public class TestGetBestPostsByLocation {

	public static void main(String[] args) {

		OperationResult result = new PostManager().getBestPostsByLocation(1, 0, 10);
		System.out.println(result.message);
	}

}
