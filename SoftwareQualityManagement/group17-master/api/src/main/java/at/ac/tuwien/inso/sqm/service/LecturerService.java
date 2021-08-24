package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface LecturerService {

    /**
     * gibt den aktuellen eingeloggten Vortragenden zurück.
     * <p>
     * Benutzer muss ein Vortragender sein
     *
     * @return der eingeloggte Vortragende
     */
    @PreAuthorize("hasRole('LECTURER')")
    LecturerEntity getLoggedInLecturer();

    /**
     * gibt alle Fächer, welche zum aktuellen eingeloggten Vortragenden.
     * gehören zurück
     * <p>
     * Benutzer muss ein Vortragender sein
     *
     * @return gibt alle Fächer, welche zum aktuellen eingeloggten Vortragenden.
     */
    @PreAuthorize("hasRole('LECTURER')")
    Iterable<Subject> getOwnSubjects();

    /**
     * findet Fächer für einen angegebenen Vortragenden. Vortragender sollte
     * nicht null sein
     * <p>
     * Benutzer muss authentifiziert werden
     *
     * @param lecturer the lecturer
     * @return  findet Fächer für einen angegebenen Vortragenden
     */
    @PreAuthorize("isAuthenticated()")
    List<Subject> findSubjectsFor(LecturerEntity lecturer);

    /**
     * generiert die Url für den QR Code.
     *
     * @param lecturer  id, email und Zwei-Faktor-Authentifizierung sollte
     *                  nicht null sein
     * @return die Url als String
     * @throws UnsupportedEncodingException
     */
    @PreAuthorize("hasRole('ADMIN')")
    String generateQRUrl(LecturerEntity lecturer)
            throws UnsupportedEncodingException;
}
