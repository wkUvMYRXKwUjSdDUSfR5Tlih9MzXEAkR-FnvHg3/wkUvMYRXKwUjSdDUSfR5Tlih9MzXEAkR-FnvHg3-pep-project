package Service;

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
}
