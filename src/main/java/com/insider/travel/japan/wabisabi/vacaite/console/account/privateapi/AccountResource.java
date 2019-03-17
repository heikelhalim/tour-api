package com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi;

import com.insider.travel.japan.wabisabi.serverlib.domain.model.AdminAccountMaster;
import com.insider.travel.japan.wabisabi.serverlib.domain.model.Mutex;
import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.request.NewAccountRequest;
import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.request.NewPasswordRequest;
import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.service.AccountService;
import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.service.MutexService;
import com.insider.travel.japan.wabisabi.vacaite.console.account.privateapi.validator.constraints.Range;
import com.insider.travel.japan.wabisabi.vacaite.console.account.response.AdminListingsLog;
import com.insider.travel.japan.wabisabi.vacaite.console.account.response.AdminListingsResponse;
import com.insider.travel.japan.wabisabi.vacaite.console.account.response.ErrorResponse;
import com.insider.travel.japan.wabisabi.vacaite.console.account.utils.SiteErrorMessage;
import com.insider.travel.japan.wabisabi.vacaite.console.account.utils.SiteLogMessage;
import java.net.URI;
import java.text.ParseException;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.owasp.esapi.errors.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author JapanTravelInsider
 */
@Path("account")
public class AccountResource {
    
    private static final Logger LOG = LoggerFactory.getLogger(AccountResource.class);
    
    @Inject
    private MutexService mutexService;
    
    @Inject
    private AccountService accountService;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createAccount(@Valid @NotNull NewAccountRequest request, @Context UriInfo uriInfo) throws EncryptionException {
        
        //ID for locking row during account registration
        Mutex entity = mutexService.getPessimisticLock();
        if (entity == null) {
            LOG.error(SiteLogMessage.ERROR_SYSTEM_ERROR.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse(SiteErrorMessage.SYSTEM_ERROR.getMessageCode(), SiteErrorMessage.SYSTEM_ERROR.getMessage())).build();
        }

        //check if account is already being registered
        if (!accountService.isExistedAccount(request.getEmail())) {
            LOG.error(SiteErrorMessage.ACCOUNT_EXISTED_ERROR.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(SiteErrorMessage.ACCOUNT_EXISTED_ERROR.getMessageCode(), SiteErrorMessage.ACCOUNT_EXISTED_ERROR.getMessage())).build();
        }
        
        // Password has email identity confirmation
        if (accountService.hasEmailIdentity(request.getPassword(), request.getEmail())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                new ErrorResponse(
                    SiteErrorMessage.ERROR_PASSWORD_HAS_EMAIL_IDENTITY.getMessageCode(),
                    SiteErrorMessage.ERROR_PASSWORD_HAS_EMAIL_IDENTITY.getMessage())
            ).build();
        }

        String id = accountService.tokenString();
        AdminAccountMaster adminAccountMaster = accountService.createNewAccount(
            request.getFullName(),
            request.getEmail(),
            request.getPassword(),
            id
        );

        URI uri = uriInfo.getRequestUriBuilder().path(adminAccountMaster.getId()).build();
        LOG.info(SiteLogMessage.ACCOUNT_REGISTERED.getMessage());
        return Response.created(uri).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updatePassword(@Valid @NotNull NewPasswordRequest request, UriInfo uriInfo) throws EncryptionException, ParseException {

        //ID for locking row during updating password
        Mutex entity = mutexService.getPessimisticLock();
        if (entity == null) {
            LOG.error(SiteLogMessage.ERROR_SYSTEM_ERROR.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorResponse(SiteErrorMessage.SYSTEM_ERROR.getMessageCode(), SiteErrorMessage.SYSTEM_ERROR.getMessage())).build();
        }

        //check if account is already being registered
        if (accountService.isExistedAccount(request.getEmail())) {
            LOG.error(SiteLogMessage.ERROR_SYSTEM_ERROR.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(SiteErrorMessage.ACCOUNT_NOT_EXISTED.getMessageCode(), SiteErrorMessage.ACCOUNT_NOT_EXISTED.getMessage())).build();
        }
        
        // Password has email identity confirmation
        if (accountService.hasEmailIdentity(request.getNewPassword(), request.getEmail())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                new ErrorResponse(
                    SiteErrorMessage.ERROR_PASSWORD_HAS_EMAIL_IDENTITY.getMessageCode(),
                    SiteErrorMessage.ERROR_PASSWORD_HAS_EMAIL_IDENTITY.getMessage())
            ).build();
        }

        //check if new password is similar to previous password
        if (!accountService.isSamePassword(request.getEmail(), request.getNewPassword())) {
            LOG.error(SiteLogMessage.ERROR_SYSTEM_ERROR.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(SiteErrorMessage.SAME_PASSWORD_ERROR.getMessageCode(), SiteErrorMessage.SAME_PASSWORD_ERROR.getMessage())).build();
        }

        //check if new password has been used for the last 4 times
        if (accountService.isContainedInHistory(request.getEmail(), request.getNewPassword())) {
            LOG.error(SiteLogMessage.ERROR_SYSTEM_ERROR.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(SiteErrorMessage.SAME_4PASSWORD_ERROR.getMessageCode(), SiteErrorMessage.SAME_4PASSWORD_ERROR.getMessage())).build();
        }

        AdminAccountMaster adminAccountMaster = accountService.createNewPassword(
            request.getEmail(),
            request.getNewPassword()
        );
        
        LOG.info(SiteLogMessage.PASSWORD_UPDATED.getMessage());
        return Response.ok().build();

    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByAccount(
            @Range(name = "startIndex", min = 0, max = 10000) @DefaultValue("0") @QueryParam("startIndex") String startIndex,
            @Range(name = "maxResults", min = 1, max = 1000) @DefaultValue("30") @QueryParam("maxResults") String maxResults
    ) throws ParseException {
        
        int intStartIndex = Integer.parseInt(startIndex);
        int intMaxResults = Integer.parseInt(maxResults);
        int intTotalSize = accountService.countAll();
        List<AdminAccountMaster> guideList = accountService.findAllGuide(intStartIndex, intMaxResults);
        
        if (guideList == null) {
            return Response.noContent().build();
        }
        return Response.ok().entity(convertResponse(guideList, intStartIndex, intMaxResults, intTotalSize, guideList.size())).build();
    }
    
    private AdminListingsResponse convertResponse(List<AdminAccountMaster> adminEntities, int startIndex, int maxResults, int totalSize, int fetchSize) throws ParseException {
               
        AdminListingsResponse responses = new AdminListingsResponse();
        for (AdminAccountMaster adminEntity : adminEntities) {
            AdminListingsLog adminResponse = new AdminListingsLog();
            adminResponse.setFullName(adminEntity.getFullName());
            adminResponse.setEmail(adminEntity.getEmail());
            responses.getResponses().add(adminResponse);
        }
        responses.setStartIndex(startIndex);
        responses.setMaxResults(maxResults);
        responses.setTotalSize(totalSize);
        responses.setFetchSize(fetchSize);
        
        return responses;
    }
    
}
