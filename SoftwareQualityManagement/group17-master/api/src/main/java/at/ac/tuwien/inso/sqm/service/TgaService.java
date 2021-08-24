package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Tag;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface TgaService {

    /**
     * gibt alle gültigen Tags zurück.
     * kann nur verwendet werden, wenn ein Benutzer authentifiziert wird
     *
     * @return eine Liste aller gültigen {@link Tag}s
     * @deprecated verwende stattdessen #findAllValid
     */
    @Deprecated
    @PreAuthorize("isAuthenticated()")
    List<Tag> findAll();

    /**
     * gibt alle gültigen Tags zurück.
     * kann nur verwendet werden, wenn ein Benutzer authentifiziert wird
     *
     * @return eine Liste aller gültigen {@link Tag}s
     */
    @PreAuthorize("isAuthenticated()")
    List<Tag> findAllValid();

    /**
     * gibt einen Tag anhand seines Namen zurück.
     * kann nur verwendet werden, wenn ein Benutzer authentifiziert wird
     *
     * @param name
     * @return ein {@link Tag} Objekt
     */
    @PreAuthorize("isAuthenticated()")
    Tag findByName(String name);
}
