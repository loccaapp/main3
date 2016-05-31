package managers;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import helper.OperationResult;
import models.Post;

public class PostManager extends BaseManager {

	public PostManager(){
		super();
	}
	
	public OperationResult insert(Post post){
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			int i = dbStatement.executeUpdate("INSERT INTO tp_post "
					+ "(user_id, location_id, post_type, post_text, like_count, dislike_count,is_replied, to_fb, to_twitter, to_instagram, status_id, create_ts, update_ts)"
             +" VALUES("+post.user_id+", "+ post.location_id+",'"+post.post_type+"', '"+ post.post_text + "', 0, 0, 'N', 'N', 'N', 'N', 'A',"+post.create_ts+","+post.update_ts+")");
			
		} catch (SQLException e) {
			OperationResult result = new OperationResult();
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		OperationResult result = new OperationResult();
		result.isSuccess = true;
		result.message = "Your post sent";		
		return result;	
	}
	
	public OperationResult getLikeAndDislikeCount(int post_id){
		OperationResult result = new OperationResult();
		try {
			dbStatement = (Statement)dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery("SELECT user_id, like_count, dislike_count FROM tp_post"+
					" WHERE post_id ="+ post_id+"");
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		Post post = new Post();		
		try {
			dbResultSet.next();
			post.user_id = dbResultSet.getInt("user_id");
			post.like_count = dbResultSet.getInt("like_count");
			post.dislike_count = dbResultSet.getInt("dislike_count");
			result.isSuccess = true;
			result.object = post;
			return result;
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
	}
	
	public OperationResult setLikeCount(int post_id, int count){
		OperationResult result = new OperationResult();
		try {
			dbStatement = (Statement)dbConnection.createStatement();
			dbStatement.executeUpdate("UPDATE tp_post SET like_count = "+count+" WHERE post_id ="+post_id+"");
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		result.isSuccess = true;
		result.message = "Post updated";
		return result;
	}

	public OperationResult setDislikeCount(int post_id, int count){
		OperationResult result = new OperationResult();
		try {
			dbStatement = (Statement)dbConnection.createStatement();
			dbStatement.executeUpdate("UPDATE tp_post SET dislike_count = "+count+" WHERE post_id ="+post_id+"");
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		result.isSuccess = true;
		result.message = "Post updated";
		return result;
	}

	public OperationResult getBestPostsByLocation(int location_id, int start, int count){
		
		OperationResult result = new OperationResult();
		String query = "SELECT * FROM tp_post, tp_location, tp_user WHERE "
				+ "tp_post.location_id = tp_location.location_id and "
				+ "tp_post.user_id = tp_user.user_id and "
				+ "tp_post.location_id = "+location_id +"  "
				+ "ORDER BY tp_post.like_count DESC  "
				+ "LIMIT "+start+", "+count+"";
		
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(query);						
			
			ArrayList<Post> posts = new ArrayList<Post>();
			while(dbResultSet.next()){
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_text = dbResultSet.getString("post_text");
				post.create_ts = dbResultSet.getTimestamp("create_ts");
				post.update_ts = dbResultSet.getTimestamp("update_ts");
				//post.post_type = dbResultSet.getString("post_type").charAt(0);
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.district_name = dbResultSet.getString("district_name");
				post.location.location_name = dbResultSet.getString("location_name"); 
				post.user.username = dbResultSet.getString("username");
				posts.add(post);
			}
			
			result.isSuccess = true;
			result.object = posts;
			result.message = "Yey " + posts.size();

			
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		return result;
	}
	
	public OperationResult getLastPostsByLocation(int location_id, int start, int count){
		
		OperationResult result = new OperationResult();
		String query = "SELECT * FROM tp_post, tp_location, tp_user WHERE "
				+ "tp_post.location_id = tp_location.location_id and "
				+ "tp_post.user_id = tp_user.user_id and "
				+ "tp_post.location_id = "+location_id +"  "
				+ "ORDER BY tp_post.update_ts DESC  "
				+ "LIMIT "+start+", "+count+"";
		
		try {
			dbStatement = (Statement) dbConnection.createStatement();
			dbResultSet = dbStatement.executeQuery(query);						
			
			ArrayList<Post> posts = new ArrayList<Post>();
			while(dbResultSet.next()){
				Post post = new Post();
				post.post_id = dbResultSet.getInt("post_id");
				post.user_id = dbResultSet.getInt("user_id");
				post.post_text = dbResultSet.getString("post_text");
				post.create_ts = dbResultSet.getTimestamp("create_ts");
				post.update_ts = dbResultSet.getTimestamp("update_ts");
				//post.post_type = dbResultSet.getString("post_type").charAt(0);
				post.like_count = dbResultSet.getInt("like_count");
				post.dislike_count = dbResultSet.getInt("dislike_count");
				post.location.district_name = dbResultSet.getString("district_name");
				post.location.location_name = dbResultSet.getString("location_name");
				post.user.username = dbResultSet.getString("username");
				posts.add(post);
			}
			
			result.isSuccess = true;
			result.object = posts;
			result.message = "Yey " + posts.size();

			
		} catch (SQLException e) {
			result.isSuccess = false;
			result.message = e.getMessage();
			return result;
		}
		
		return result;
	}
}
