package com.portfolio.bloom.security.auth;

import java.lang.reflect.Method;
import java.util.Optional;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import lombok.RequiredArgsConstructor;
import com.portfolio.bloom.common.UserUtil;
import com.portfolio.bloom.domain.repository.VenueRepository;
import com.portfolio.bloom.error.CommonException;
import com.portfolio.bloom.error.ErrorEnum;
import com.portfolio.bloom.domain.common.Ownable;

@Component("ownableSecurity")
@RequiredArgsConstructor
public class OwnableSecurity {

    private final UserUtil userUtil;
    private final ApplicationContext applicationContext;
    private final VenueRepository venueRepository;

    /**
     * Checks if the current authenticated user is the owner of the specified entity.
     * 
     * <p>This method performs ownership verification by:
     * <ol>
     *   <li>Retrieving the entity from the repository using the provided ID</li>
     *   <li>For Package, AddOn, and Reservation entities, it resolves ownership through 
     *       the associated Venue (by checking the venue's owner)</li>
     *   <li>For other entities, it directly checks if the entity implements Ownable 
     *       and compares the entity's userId with the current user's ID</li>
     * </ol>
     * 
     * <p>Special handling:
     * <ul>
     *   <li>Package and AddOn: Ownership is determined by the venue they belong to</li>
     *   <li>Reservation: Ownership is determined by the venue the reservation is for</li>
     *   <li>Other entities: Must implement Ownable interface with getUserId() method</li>
     * </ul>
     * 
     * <p><b>Examples:</b>
     * <ul>
     *   <li><b>Reservation:</b> 
     *       <code>isOwner("reservation123", "reservation")</code><br>
     *       Checks: <code>reservations</code> table (id = "reservation123") → 
     *       Gets <code>venueId</code> column → 
     *       Checks <code>venues</code> table (id = venueId) → 
     *       Compares <code>venues.userId</code> with current user's ID
     *   </li>
     *   <li><b>Package:</b> 
     *       <code>isOwner("package456", "package")</code><br>
     *       Checks: <code>packages</code> table (id = "package456") → 
     *       Gets <code>venueId</code> column → 
     *       Checks <code>venues</code> table (id = venueId) → 
     *       Compares <code>venues.userId</code> with current user's ID
     *   </li>
     *   <li><b>AddOn:</b> 
     *       <code>isOwner("addon789", "addOn")</code><br>
     *       Checks: <code>addons</code> table (id = "addon789") → 
     *       Gets <code>venueId</code> column → 
     *       Checks <code>venues</code> table (id = venueId) → 
     *       Compares <code>venues.userId</code> with current user's ID
     *   </li>
     *   <li><b>Venue:</b> 
     *       <code>isOwner("venue321", "venue")</code><br>
     *       Checks: <code>venues</code> table (id = "venue321") → 
     *       Compares <code>venues.userId</code> directly with current user's ID
     *   </li>
     * </ul>
     * 
     * @param id The ID of the entity to check ownership for
     * @param repositoryBeanName The bean name of the repository (without "Repository" suffix).
     *                          For example, "reservation" for ReservationRepository,
     *                          "venue" for VenueRepository, etc.
     * @return true if the current user is the owner of the entity, false otherwise
     * @throws RuntimeException if an error occurs during the ownership check
     */
    public boolean isOwner(String id, String repositoryBeanName) {
        try {
            System.out.println("isOwner: " + id + " " + repositoryBeanName);
            Object repo = applicationContext.getBean(repositoryBeanName + "Repository");
            System.out.println("repo: " + repo);
            Method findById = ReflectionUtils.findMethod(repo.getClass(), "findByIdAndDeletedIsFalse", String.class);
            if (findById == null) return false;
            System.out.println("findById: " + findById);
            Optional<?> entityOpt = (Optional<?>) ReflectionUtils.invokeMethod(findById, repo, id);
            System.out.println("entityOpt: " + entityOpt);
            if (entityOpt.isEmpty()) 
                throw new CommonException(ErrorEnum.ENTITY_NOT_FOUND, (id+ " "));

            Object entity = entityOpt.get();
            System.out.println("entity: " + entity);

            if (!(entity instanceof Ownable)) 
                throw new CommonException(ErrorEnum.ENTITY_NOT_FOUND, (entity.getClass().getSimpleName() + " "));
            String currentUserId = userUtil.getCurrentUser().getId();
            return currentUserId.equals(((Ownable) entity).getUserId());
        } catch (Exception ex) {
            throw new RuntimeException("Error checking ownership: " + "isOwner(" + id + ", " + repositoryBeanName + "), " + ex.getMessage(), ex);
        }
    }
}

