package Service;

import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;
import Model.Account;
import Model.Message;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountService accountService;

    public MessageService() {
        this.messageDAO = new MessageDAO();
        this.accountService = new AccountService();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
        this.accountService = new AccountService();
    }

    public Message addMessage(Message message) {
        String text = message.getMessage_text();
        int postedBy = message.getPosted_by();
        Account account = accountService.geAccount(postedBy);
        if(!text.isBlank() && text.length() <= 255 && account != null && postedBy == account.getAccount_id()) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

    public List<Message> getMessages() {
        return messageDAO.getMessages();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    public Message deleteMessageById(int messageId) {
        Message message = getMessageById(messageId);
        if(message != null) {
            messageDAO.deleteMessageById(messageId);
            return message;
        }
        return null;
    }

    public Message updateMessage(Message message) {
        Message oldMessage = getMessageById(message.getMessage_id());
        String text = message.getMessage_text();

        if(oldMessage != null && !text.isBlank() && text.length() <= 255) {
            messageDAO.updateMessage(message);
            return getMessageById(message.getMessage_id());
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }
}
