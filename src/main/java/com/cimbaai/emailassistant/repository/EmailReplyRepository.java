package com.cimbaai.emailassistant.repository;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cimbaai.emailassistant.model.EmailReply;

@Repository
public interface EmailReplyRepository extends JpaRepository<EmailReply, Long> {

    List<EmailReply> findAllByOrderByCreatedAtDesc();

    List<EmailReply> findByTone(String tone);

    List<EmailReply> findByCreatedAtAfter(OffsetDateTime date);

    @Query("SELECT e FROM EmailReply e WHERE e.createdAt >= :thirtyDaysAgo ORDER BY e.createdAt DESC")
    List<EmailReply> findRecentReplies(OffsetDateTime thirtyDaysAgo);

    long countByTone(String tone);
}