package com.example.project.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.Model.Message;
import com.example.project.Model.User;
import com.example.project.Repository.MessageRepository;

import net.minidev.json.JSONObject;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getUserMessages(User userId) {
        return messageRepository.findUserMessages(userId);
    }
    
    public List<Message> getUserChatRoomMessages(JSONObject chatRoomId) {
        JSONObject tokenString= new JSONObject(chatRoomId);
        String chatRoom= tokenString.getAsString("chat_room_id");
        
        return messageRepository.findUserChatRoomMessages(chatRoom);
    }

    private JdbcTemplate template;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

    @Autowired
    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }
    
    public List<String> getUserChatRooms(String username, int userId) {
        
        String queryString="WITH chat_rooms AS (SELECT chat_room_id, sender_user_id, reciever_user_id FROM message \r\n"
                + "                    UNION SELECT chat_room_id, reciever_user_id AS sender_user_id, sender_user_id AS reciever_user_id FROM message)\r\n"
                + "SELECT DISTINCT chat_room_id, \r\n"
                + "  CASE \r\n"
                + "    WHEN sender_user_id = "+userId+" THEN reciever_user_id\r\n"
                + "    ELSE sender_user_id\r\n"
                + "  END AS reciever_user_id\r\n"
                + "FROM chat_rooms WHERE sender_user_id = "+userId+"";
        return getAll(queryString);
    }
    
    public List<String> getAll(String queryString) {
        return template.query(queryString, new ResultSetExtractor<List<String>>() {

            @Override
            public List<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<String> list = new ArrayList<String>();
                while (rs.next()) {
                    List<String> list1 = new ArrayList<String>();
                    list1.add(rs.getString(1));
                    list1.add(rs.getString(2));
                    list.addAll(list1);
                }
                return list;
            }

        });
    }
    
    public JSONObject getUserChatRoomId(String senderUsername, String recieverUsername) {
        senderUsername= "'%"+senderUsername+"%'";
        recieverUsername= "'%"+recieverUsername+"%'";
        
        String queryString= "SELECT DISTINCT chat_room_id FROM message WHERE chat_room_id LIKE "+senderUsername+" and chat_room_id LIKE "+recieverUsername;
        
        JSONObject chatRoomId= new JSONObject();
        
        template.query(queryString, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                String idString= "";
                while (rs.next()) {
                    idString= rs.getString(1);
                    chatRoomId.put("chat_room_id", idString);
                }
                if (chatRoomId.isEmpty()) {
                    chatRoomId.put("chat_room_id", idString);
                }
                return idString;
            }
        });
        return chatRoomId;
    }

    
    public Message findMessage(int messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalStateException("Message with ID " + messageId + " does not exist"));
    }

    public Message addNewMessage(Message message) {      
       return messageRepository.save(message);
    }

    public void deleteMessage(int messageId) {
        boolean exists = messageRepository.existsById(messageId);
        if (!exists) {
            throw new IllegalStateException("Message with ID " + messageId + "does not exist");
        }
        messageRepository.deleteById(messageId);
    }

    @Transactional
    public String updateMessage(int messageId, Message Newmessage) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalStateException("Message with ID " + messageId + " does not exist"));

        if (Newmessage.getMessageBody() != null && Newmessage.getMessageBody().length() > 0) {
            message.setMessagebody(Newmessage.getMessageBody());
        }
        
        if (Newmessage.getTimestamp()!=null) {
            message.setTimestamp(Newmessage.getTimestamp());
        }
        
        return "Successfully updated records";
    }

}
