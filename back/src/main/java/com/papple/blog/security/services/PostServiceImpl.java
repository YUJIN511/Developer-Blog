package com.papple.blog.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.papple.blog.models.Post;
import com.papple.blog.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public List<Post> findByWriter(String writer) {
		return postRepository.findByWriter(writer);
	}

	@Override
	public Post save(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Optional<Post> findById(Long id) {
		return postRepository.findById(id);
	}
	
	@Override
	public List<Post> searchByWord(String word) {
		return postRepository.searchByWord(word);
	}

	@Override
	public void deleteById(Long id) {
		postRepository.deleteById(id);
		return;
	}

	@Override
	public List<Post> findHistoryByUser(String email) {
		return postRepository.findHistoryByUser(email);
	}

	@Override
	public List<Post> findMyHashPost(String hashtag, String email) {
		return postRepository.findMyHashPost(hashtag, email);
	}

	@Override
	public List<Post> findStorageByUser(String email) {
		return postRepository.findStorageByUser(email);
	}

	@Override
	public List<Post> findFollowLatestByUser(String email) {
		return postRepository.findFollowLatestByUser(email);
	}
	
	public List<Post> searchByHashtag(String word) {
		return postRepository.searchByHashtag(word);
	}

	@Override
	public void deleteByWriter(String email) {
		postRepository.deleteByWriter(email);
		return;
	}

	@Override
	public List<Post> searchPopularPost() {
		return postRepository.searchPopularPost();
	}

	@Override
	public List<Post> findFollowPopularByUser(String email) {
		return postRepository.findFollowPopularByUser(email);
	}

	@Override
	public void updatePicture(String picture, Long id) {
		postRepository.updatePicture(picture, id);
	}

	@Override
	public void deletePicture(Long id) {
		postRepository.deletePicture(id);
	}
}
