package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.SemesterDto;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterServiceInterface {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SemesterServiceImpl.class);

    @Autowired
    private SemestreRepository semesterRepository;

    @Autowired
    private Clock clock;

    @Override
    @Transactional
    public SemesterDto create(SemesterDto semester) {
        LOGGER.info("creating semester " + semester.getLabel());
        return semesterRepository.save(semester.toEntity()).toDto();
    }

    @Override
    @Transactional(readOnly = true)
    public SemesterDto getCurrentSemester() {
        Semester semester = semesterRepository.findFirstByOrderByIdDesc();

        if (semester != null) {
            LOGGER.info("returning current semester " + semester.getLabel());
            return semester.toDto();
        } else {
            LOGGER.warn("current semester is null");
            return null;
        }
    }

    @Override
    public SemesterDto getOrCreateCurrentSemester() {
        LocalDateTime localDateTime = LocalDateTime.now(clock);
        Calendar now = GregorianCalendar
                .from(ZonedDateTime.of(localDateTime, ZoneId.systemDefault()));
        return getOrCreateCurrentSemester(now);
    }

    /**
     * If now is still in the current semester, we return the semester.
     * <p>
     * Otherwise the current semester will be created and returned
     *
     * @param now date to compare with
     * @return The current semester
     */
    public SemesterDto getOrCreateCurrentSemester(Calendar now) {
        SemesterDto current = getCurrentSemester();

        if (current == null || !current.isCurrent(now)) {
            LOGGER.info("Current semester is not started yet, creating a new one");
            return create(SemesterDto.calculateCurrentSemester(now));
        }

        return current;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SemesterDto> findAll() {
        LOGGER.info("finding all semesters");
        return convertSemesterListToSemesterDtoList(
                semesterRepository.findAllByOrderByIdDesc());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SemesterDto> findAllSince(SemesterDto semester) {
        LOGGER.info("finding all semesters since " + semester.getLabel());
        return convertSemesterListToSemesterDtoList(
                semesterRepository.findAllSince(semester.toEntity()));
    }

    private List<SemesterDto> convertSemesterListToSemesterDtoList(
            List<Semester> semesters) {
        List<SemesterDto> toReturn = new ArrayList<>();
        for (Semester s : semesters) {
            toReturn.add(s.toDto());
        }
        return toReturn;
    }

    private SemesterDto transform(Semester semester) {
        SemesterDto semesterDto = new SemesterDto();
        semesterDto.setId(semester.getId());
        semester.setType(semester.getType());
        semester.setYear(semester.getYear());
        return semesterDto;
    }
}
