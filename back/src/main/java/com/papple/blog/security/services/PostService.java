package com.papple.blog.security.services;

import java.util.List;
import java.util.Optional;
import com.papple.blog.models.Post;

public interface PostService {
	List<Post> findAll();
	List<Post> findByWriter(String writer);
	Post save(Post post);
	Optional<Post> findById(Long id);
	void deleteById(Long id);
	void deleteByWriter(String email);
	List<Post> searchByWord(String word);
	List<Post> findHistoryByUser(String email);
	List<Post> findMyHashPost(String hashtag, String email);
	List<Post> findStorageByUser(String email);
	List<Post> findFollowLatestByUser(String email);
	List<Post> searchByHashtag(String word);
	List<Post> searchPopularPost();
	List<Post> findFollowPopularByUser(String email);
	void updatePicture(String picture, Long id);
	void deletePicture(Long id);
	int cntCategory(String email, String hashtag);
}
