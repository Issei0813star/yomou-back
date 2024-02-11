package com.yomou.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "point")
    private int point;

    @Column(name = "header")
    private String header;

    @Column(name = "content")
    private String content;

    @Column(name = "recommend_target")
    private String recommendTarget;

    @Column(name = "posted_at")
    private Timestamp postedAt;


}