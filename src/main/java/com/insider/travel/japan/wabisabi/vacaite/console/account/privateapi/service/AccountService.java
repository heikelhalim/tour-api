package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.service;

import com.insider.travel.japan.wabisabi.common.utils.TokenUtil;
import com.insider.travel.japan.wabisabi.serverlib.domain.dao.AdminAccountMasterDao;
import com.insider.travel.japan.wabisabi.serverlib.domain.dao.AdminAccountPasswordHistoryDao;
import com.insider.travel.japan.wabisabi.serverlib.domain.model.AdminAccountMaster;
import com.insider.travel.japan.wabisabi.serverlib.domain.model.AdminAccountPasswordHistory;
import com.insider.travel.japan.wabisabi.serverlib.domain.model.AdminAccountPasswordHistoryPK;
import com.insider.travel.japan.wabisabi.serverlib.utils.common.PasswordUtil;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.owasp.esapi.errors.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author JapanTravelInsider
 */
@RequestScoped
public class AccountService {
    
    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);
    
    @Inject
    private AdminAccountMasterDao adminAccountMasterDao;
    
    @Inject
    private AdminAccountPasswordHistoryDao adminAccountPasswordHistoryDao;

    public AdminAccountMaster createNewAccount( String fullName, String email, String password, String id) throws EncryptionException {

        AdminAccountMaster adminAccountMaster = new AdminAccountMaster();
        adminAccountMaster.setId(id);
        adminAccountMaster.setFullName(fullName);
        adminAccountMaster.setEmail(email);
        adminAccountMaster.setPassword(password);
        adminAccountMaster.setPasswordHash(PasswordUtil.getAccountPasswordHash(password, email));
        AdminAccountMaster insertNewAccount = adminAccountMasterDao.create(adminAccountMaster);

        AdminAccountPasswordHistoryPK adminAccountPasswordHistoryPK = new AdminAccountPasswordHistoryPK();
        adminAccountPasswordHistoryPK.setId(id);

        AdminAccountPasswordHistory adminAccountPasswordHistory = new AdminAccountPasswordHistory();
        adminAccountPasswordHistory.setAdminAccountPasswordHistoryPK(adminAccountPasswordHistoryPK);
        adminAccountPasswordHistory.setEmail(email);
        adminAccountPasswordHistory.setPassword(password);
        adminAccountPasswordHistory.setPasswordHash(PasswordUtil.getAccountPasswordHash(password, email));
        adminAccountPasswordHistoryDao.create(adminAccountPasswordHistory);

        return insertNewAccount;
    }

    public AdminAccountMaster createNewPassword(String email, String newPassword) throws EncryptionException {

        AdminAccountMaster adminAccountMaster = adminAccountMasterDao.findByEmail(email);
        adminAccountMaster.setEmail(email);
        adminAccountMaster.setPassword(newPassword);
        adminAccountMaster.setPasswordHash(PasswordUtil.getAccountPasswordHash(newPassword, email));
        AdminAccountMaster updatePassword = adminAccountMasterDao.edit(adminAccountMaster);

        AdminAccountPasswordHistoryPK adminAccountPasswordHistoryPK = new AdminAccountPasswordHistoryPK(adminAccountMaster.getId(), adminAccountMaster.getUpdateVersion() + 1);

        AdminAccountPasswordHistory adminAccountPasswordHistory = new AdminAccountPasswordHistory(adminAccountPasswordHistoryPK, newPassword);
        adminAccountPasswordHistory.setEmail(email);
        adminAccountPasswordHistory.setPassword(newPassword);
        adminAccountPasswordHistory.setPasswordHash(PasswordUtil.getAccountPasswordHash(newPassword, email));
        adminAccountPasswordHistoryDao.create(adminAccountPasswordHistory);

        return updatePassword;
    }
    
    public List<AdminAccountMaster> findAllGuide(int startIndex, int maxResults) {
        
        List<AdminAccountMaster> foundEntities = adminAccountMasterDao.findAll(startIndex, maxResults);
        
        if (foundEntities.isEmpty()) {
            return null;
        }
        return foundEntities;
    }
    
    public int countAll() {
        return adminAccountMasterDao.count();
    }

    public boolean isExistedAccount(String email) {
        if (adminAccountMasterDao.findByEmail(email) == null) {
            return true;
        }
        return false;
    }

    public boolean isSamePassword(String email, String currentPassword) throws EncryptionException {
        String currentPasswordHash = PasswordUtil.getAccountPasswordHash(currentPassword, email);
        AdminAccountMaster adminAccountMaster = adminAccountMasterDao.findByEmail(email);
        if (currentPasswordHash.equals(adminAccountMaster.getPasswordHash().trim())) {
            return false;
        }
        return true;
    }

    public boolean isContainedInHistory(String email, String currentPassword) {
        AdminAccountMaster adminAccountMaster = adminAccountMasterDao.findByEmail(email);
        List<AdminAccountPasswordHistory> adminAccountPasswordHistory = adminAccountPasswordHistoryDao.findLatest4ById(adminAccountMaster.getId());
        for (AdminAccountPasswordHistory entity : adminAccountPasswordHistory) {
            if (currentPassword.equals(entity.getPasswordHash().trim())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean hasEmailIdentity(String password, String email) {
        if(password.contains(email.substring(0, email.indexOf("@")))) {
            return true;
        }
        return false;
    }

    public static String tokenString() {
        return (TokenUtil.getToken()).substring(0, 32);
    }
    
}
