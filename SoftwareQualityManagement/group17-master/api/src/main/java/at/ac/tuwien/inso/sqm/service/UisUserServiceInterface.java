package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.UisUserEntity;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface UisUserServiceInterface {

    /**
     * returns all matching UisUsers that fit the searchFilter and are on the
     * provided Pageable.
     * the query should be ordered desc. by the user IDs
     * can only be used by ADMINs.
     *
     * @param searchFilter the search filter
     * @param pageable the pageable object
     * @return all matching UisUsers that fit the searchFilter and are on the provided Pageable.
     */
    @PreAuthorize("hasRole('ADMIN')")
    Page<UisUserEntity> findAllMatching(String searchFilter, Pageable pageable);

    /**
     * @param id should not be null and not <1
     * @return UisUserEntity
     * @throws BusinessObjectNotFoundException returns an
     * {@link UisUserEntity} with the provided id. if no user can be found
     *                                         can only be used by ADMINs. if
     *                                         no user is found a
     *                                {@link BusinessObjectNotFoundException}
     *                                will be thrown
     */
    @PreAuthorize("hasRole('ADMIN')")
    UisUserEntity findOne(long id) throws BusinessObjectNotFoundException;

    /**
     * looks up if a UisUserEntity with the provided id exists in the
     * repository. returns a true boolean if the id exists.
     *
     * @param id the id
     * @return boolean true if it exists, otherwise false
     */
    @PreAuthorize("isAuthenticated()")
    boolean existsUserWithIdentificationNumber(String id);

    /**
     * returns true if there already is a user with this emailadress.
     * user has to be admin
     *
     * @param email the email
     * @return eturns true if there already is a user with this email, otherwise false
     */
    @PreAuthorize("hasRole('ADMIN')")
    boolean existsUserWithMailAddress(String email);
}
