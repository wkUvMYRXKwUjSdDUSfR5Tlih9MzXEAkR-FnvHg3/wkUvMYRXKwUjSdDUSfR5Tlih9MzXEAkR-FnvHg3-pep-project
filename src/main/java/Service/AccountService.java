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

    public Account getAccount(Account account) {
        return accountDAO.getAccountByUsername(account.getUsername());
    }

    public Account accountLogin(Account account) {
        Account checkedAccount = accountDAO.getAccountByUsername(account.getUsername());
        if(checkedAccount != null && checkedAccount.getPassword().equals(account.getPassword())) {
            return checkedAccount;
        }
        return null;
    }

    public Account geAccount(int accountId) {
        return accountDAO.getAccountByAccountID(accountId);
    }
}
