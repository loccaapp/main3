package test;

import helper.OperationResult;
import managers.LikeManager;

public class TestTransLikePost {

	public static void main(String[] args) {

		OperationResult result = new OperationResult();
		result = new LikeManager().transDislikePost(1000003,2);
		System.out.println(result.message);
	}

}
