package com.iamvickyav.springbootjaxrssample.repo;

import com.iamvickyav.springbootjaxrssample.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author daika
 */
public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByAuthorContaining(@Param("author") String authorName);

    void deleteByAuthor(String author);
}
