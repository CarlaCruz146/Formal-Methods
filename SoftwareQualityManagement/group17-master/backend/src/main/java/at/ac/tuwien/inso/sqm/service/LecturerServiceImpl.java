package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.repository.LecturerRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Service
public class LecturerServiceImpl implements LecturerService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LecturerServiceImpl.class);


    public static final String QR_PREFIX =
            "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht" +
                    "=qr&chl=";
    public static final String APP_NAME = "UIS";

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    @Transactional(readOnly = true)
    public LecturerEntity getLoggedInLecturer() {
        Long id = userAccountService.getCurrentLoggedInUser().getId();
        LOGGER.info("returning currently logged in lecturer with id " + id);
        return lecturerRepository.findLecturerByAccountId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> getOwnSubjects() {
        LOGGER.info("returning owned subjects for currently logged in lecturer");
        return subjectRepository
                .findByLecturersId(getLoggedInLecturer().getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> findSubjectsFor(LecturerEntity lecturer) {
        LOGGER.info("returning subjects for lecturer " + lecturer.toString());
        return subjectRepository.findByLecturersId(lecturer.getId());
    }

    @Override
    public String generateQRUrl(LecturerEntity lecturer)
            throws UnsupportedEncodingException {
        LOGGER.info("generating QR url for lecturer with id " + lecturer.getId());
        String url = "otpauth://totp/" + APP_NAME + ":" + lecturer.getEmail() +
                "?secret=" + lecturer.getTwoFactorSecret() + "&issuer=" + APP_NAME + "";
        return QR_PREFIX + URLEncoder.encode(url, "UTF-8");
    }
}
