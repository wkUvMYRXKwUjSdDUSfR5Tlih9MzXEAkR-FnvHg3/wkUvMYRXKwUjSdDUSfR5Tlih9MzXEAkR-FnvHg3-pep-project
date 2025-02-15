package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        Account checkedAccount = accountDAO.getAccountByUsername(account.getUsername());
        if(checkedAccount == null && account.getPassword().length() >= 4 && account.getUsername().length() > 0) {
            return accountDAO.insertAccount(account);
        }
        return null;
    }
}
