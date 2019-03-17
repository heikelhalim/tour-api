package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.service;

import com.insider.travel.japan.wabisabi.serverlib.domain.dao.MutexDao;
import com.insider.travel.japan.wabisabi.serverlib.domain.model.Mutex;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author JapanTravelInsider
 */
@RequestScoped
public class MutexService {
    
    @Inject
    private MutexDao mutexDao;

    //ID for locking row during account registration
    private static final int ACCOUNT_WRITE_ID = 1;

    public Mutex getPessimisticLock() {
        Mutex entity = mutexDao.findByIdAccountWrite(ACCOUNT_WRITE_ID);
        return entity;
    }
    
}
