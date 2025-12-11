package com.cimbaai.emailassistant.repository;

import com.cimbaai.emailassistant.model.EmailReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface EmailReplyRepository extends JpaRepository {

    // Find all replies ordered by creation date (newest first)
    List findAllByOrderByCreatedAtDesc();

    // Find replies by tone
    List findByTone(String tone);

    // Find replies created after a certain date
    List findByCreatedAtAfter(OffsetDateTime date);

    // Custom query to get recent replies (last 30 days)
    @Query("SELECT e FROM EmailReply e WHERE e.createdAt >= :thirtyDaysAgo ORDER BY e.createdAt DESC")
    List findRecentReplies(OffsetDateTime thirtyDaysAgo);

    // Count replies by tone
    long countByTone(String tone);
}