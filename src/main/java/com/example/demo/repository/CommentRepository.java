package com.example.demo.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	@Query(value = "SELECT * FROM sys.comment WHERE commented_item = ?1", nativeQuery = true)
	Set<Comment> findCommentsByItem(Long id);
	
	@Query(value = "SELECT * FROM sys.comment WHERE commenter = ?1", nativeQuery = true)
	Set<Comment> findUserComments(Long user_id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM sys.comment WHERE id IN (:id_list_items)", nativeQuery = true)
	void deleteCommentsByList (@Param("id_list_items") List<Long> id_list_items);
}
