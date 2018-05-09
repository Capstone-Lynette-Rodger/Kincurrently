package com.kincurrently.repositories;

import com.kincurrently.models.Message;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{

    @Query(value = "select * from messages Join messages_message_recipients recipient ON messages.id = recipient.message_id " +
            "where message_read = 0 AND recipient.recipient_id = ?1", nativeQuery = true)
    List<Message> findUnreadMessages(long id);

    @Query(value = "select * from messages Join messages_message_recipients recipient ON messages.id = recipient.message_id " +
            "where messages.creator_id = ?1 OR recipient.recipient_id = ?1", nativeQuery = true)
    List<Message> findAllUsersMessages(long user);

    @Transactional
    @Modifying
    @Query(value = "Update messages JOIN messages_message_recipients recipient ON messages.id = recipient.message_id SET message_read = TRUE WHERE recipient_id = ?1", nativeQuery = true)
    int updateRead (long user);


}
