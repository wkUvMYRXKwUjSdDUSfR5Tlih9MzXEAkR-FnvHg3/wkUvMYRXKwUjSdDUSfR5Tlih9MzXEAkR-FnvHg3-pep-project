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
        System.out.println("addAccount checkedAccount " + checkedAccount);
        if(checkedAccount == null && account.getPassword().length() >= 4 && account.getUsername().length() > 0) {
            Account addedAccount = accountDAO.insertAccount(account);
            System.out.println("addAccount addedAccount " + addedAccount);
            return addedAccount;
        }
        return null;
    }
}
