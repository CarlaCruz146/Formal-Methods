package at.ac.tuwien.inso.sqm.initializer;

import at.ac.tuwien.inso.sqm.entity.EctsDistributionEntity;
import at.ac.tuwien.inso.sqm.entity.Feedback;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.MarkEntity;
import at.ac.tuwien.inso.sqm.entity.PendingAcountActivation;
import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanRegistration;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.entity.UisUserEntity;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.FeedbackRepository;
import at.ac.tuwien.inso.sqm.repository.GradeRepository;
import at.ac.tuwien.inso.sqm.repository.PendingAccountActivationRepository;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.repository.StudyPlanRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectForStudyPlanRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import at.ac.tuwien.inso.sqm.repository.TagRepository;
import at.ac.tuwien.inso.sqm.repository.UisUserRepository;
import at.ac.tuwien.inso.sqm.repository.UserAccountRepository;
import at.ac.tuwien.inso.sqm.service.student_subject_prefs.StudentSubjectPreferenceStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Arrays.asList;

@Component
public class DataInitializer {

    private final Map<String, Subject>
            subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester1 =
            new HashMap<String, Subject>() {
                {
                    put("VU Programmkonstruktion",
                            new Subject("VU Programmkonstruktion",
                                    new BigDecimal(8.8)));
                    put("UE Studieneingangsgespr??ch",
                            new Subject("UE Studieneingangsgespr??ch",
                                    new BigDecimal(0.2)));
                    put("VU Technische Grundlagen der Informatik", new Subject(
                            "VU Technische Grundlagen der Informatik",
                            new BigDecimal(6.0)));
                    put("VO Algebra und Diskrete Mathematik f??r Informatik " +
                            "und Wirtschaftsinformatik", new Subject(
                            "VO Algebra und Diskrete Mathematik f??r " +
                                    "Informatik und Wirtschaftsinformatik",
                            new BigDecimal(4.0)));
                    put("UE Algebra und Diskrete Mathematik f??r Informatik " +
                            "und Wirtschaftsinformatik", new Subject(
                            "UE Algebra und Diskrete Mathematik f??r " +
                                    "Informatik und Wirtschaftsinformatik",
                            new BigDecimal(5.0)));
                    put("VU Formale Modellierung",
                            new Subject("VU Formale Modellierung",
                                    new BigDecimal(3.0)));
                    put("VU Datenmodellierung",
                            new Subject("VU Datenmodellierung",
                                    new BigDecimal(3.0)));
                }
            };
    private final Map<String, Subject>
            subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester2 =
            new HashMap<String, Subject>() {
                {
                    put("VU Algorithmen und Datenstrukturen 1",
                            new Subject("VU Algorithmen und Datenstrukturen 1",
                                    new BigDecimal(6.0)));
                    put("VU Algorithmen und Datenstrukturen 2",
                            new Subject("VU Algorithmen und Datenstrukturen 2",
                                    new BigDecimal(3.0)));
                    put("VU Einf??hrung in Visual Computing",
                            new Subject("VU Einf??hrung in Visual Computing",
                                    new BigDecimal(6.0)));
                    put("VU Gesellschaftliche Spannungsfelder der Informatik",
                            new Subject(
                                    "VU Gesellschaftliche Spannungsfelder der" +
                                            " Informatik",
                                    new BigDecimal(3.0)));
                    put("VU Basics of Human Computer Interaction", new Subject(
                            "VU Basics of Human Computer Interaction",
                            new BigDecimal(3.0)));
                    put("VO Analysis f??r Informatik und Wirtschaftsinformatik",
                            new Subject("VO Analysis f??r Informatik und " +
                                    "Wirtschaftsinformatik",
                                    new BigDecimal(2.0)));
                    put("UE Analysis f??r Informatik und Wirtschaftsinformatik",
                            new Subject("UE Analysis f??r Informatik und " +
                                    "Wirtschaftsinformatik",
                                    new BigDecimal(4.0)));
                    put("VU Objektorientierte Modellierung",
                            new Subject("VU Objektorientierte Modellierung",
                                    new BigDecimal(3.0)));
                }
            };
    @SuppressWarnings("checkstyle:MagicNumber")
    private final Map<String, Subject>
            subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester3 =
            new HashMap<String, Subject>() {
                {
                    put("VU Objektorientierte Programmiertechniken",
                            new Subject(
                                    "VU Objektorientierte Programmiertechniken",
                                    new BigDecimal(3.0)));
                    put("VU Funktionale Programmierung",
                            new Subject("VU Funktionale Programmierung",
                                    new BigDecimal(3.0)));
                    put("VO Betriebssysteme", new Subject("VO Betriebssysteme",
                            new BigDecimal(2.0)));
                    put("UE Betriebssysteme", new Subject("UE Betriebssysteme",
                            new BigDecimal(4.0)));
                    put("VU Introduction to Security",
                            new Subject("VU Introduction to Security",
                                    new BigDecimal(3.0)));
                    put("VU Daten- und Informatikrecht",
                            new Subject("VU Daten- und Informatikrecht",
                                    new BigDecimal(3.0)));
                    put("VU Datenbanksysteme",
                            new Subject("VU Datenbanksysteme",
                                    new BigDecimal(6.0)));
                    put("VO Statistik und Wahrscheinlichkeitstheorie",
                            new Subject("VO Statistik und " +
                                    "Wahrscheinlichkeitstheorie",
                                    new BigDecimal(3.0)));
                    put("UE Statistik und Wahrscheinlichkeitstheorie",
                            new Subject("UE Statistik und " +
                                    "Wahrscheinlichkeitstheorie",
                                    new BigDecimal(3.0)));
                }
            };
    private final Map<String, Subject>
            subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester4 =
            new HashMap<String, Subject>() {
                {
                    put("VU Einf??hrung in die K??nstliche Intelligenz",
                            new Subject("VU Einf??hrung in die K??nstliche " +
                                    "Intelligenz", new BigDecimal(3.0)));
                    put("VU Theoretische Informatik und Logik",
                            new Subject("VU Theoretische Informatik und Logik",
                                    new BigDecimal(6.0)));
                    put("VO Software Engineering und Projektmanagement",
                            new Subject("VO Software Engineering und " +
                                    "Projektmanagement", new BigDecimal(3.0)));
                    put("PR Software Engineering und Projektmanagement",
                            new Subject("PR Software Engineering und " +
                                    "Projektmanagement", new BigDecimal(6.0)));
                }
            };
    private final Map<String, Subject>
            subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester5 =
            new HashMap<String, Subject>() {
                {
                    put("VO Verteilte Systeme",
                            new Subject("VO Verteilte Systeme",
                                    new BigDecimal(3.0)));
                    put("UE Verteilte Systeme",
                            new Subject("UE Verteilte Systeme",
                                    new BigDecimal(3.0)));
                    put("VU Gesellschaftswissenschaftliche Grundlagen der " +
                            "Informatik", new Subject(
                            "VU Gesellschaftswissenschaftliche " +
                                    "Grundlagen der Informatik",
                            new BigDecimal(3.0)));
                    put("VU Interface and Interaction Design",
                            new Subject("VU Interface and Interaction Design",
                                    new BigDecimal(3.0)));
                    put("VU Einf??hrung in wissensbasierte Systeme", new Subject(
                            "VU Einf??hrung in wissensbasierte Systeme",
                            new BigDecimal(3.0)));
                    put("SE Wissenschaftliches Arbeiten",
                            new Subject("SE Wissenschaftliches Arbeiten",
                                    new BigDecimal(3.0)));
                }
            };
    private final Map<String, Subject>
            subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester6 =
            new HashMap<String, Subject>() {
                {
                    put("PR Bachelorarbeit f??r Informatik und " +
                            "Wirtschaftsinformatik", new Subject(
                            "PR Bachelorarbeit f??r Informatik und " +
                                    "Wirtschaftsinformatik",
                            new BigDecimal(10.0)));
                }
            };
    private final Map<String, Subject>
            subjectsOptionalBachelorSoftwareAndInformationEngineering =
            new HashMap<String, Subject>() {
                {
                    put("VU Prop??deutikum f??r Informatik",
                            new Subject("VU Prop??deutikum f??r Informatik",
                                    new BigDecimal(6.0)));
                    put("VO Deklaratives Probleml??sen",
                            new Subject("VO Deklaratives Probleml??sen",
                                    new BigDecimal(3.0)));
                    put("UE Deklaratives Probleml??sen",
                            new Subject("UE Deklaratives Probleml??sen",
                                    new BigDecimal(3.0)));
                    put("VU Logikprogrammierung und Constraints", new Subject(
                            "VU Logikprogrammierung und Constraints",
                            new BigDecimal(6.0)));
                    put("VO Abstrakte Maschinen",
                            new Subject("VO Abstrakte Maschinen",
                                    new BigDecimal(3.0)));
                    put("UE Abstrakte Maschinen",
                            new Subject("UE Abstrakte Maschinen",
                                    new BigDecimal(3.0)));
                    put("VU Microcontroller", new Subject("VU Microcontroller",
                            new BigDecimal(7.0)));
                    put("UE Programmierung von Betriebssystemen", new Subject(
                            "UE Programmierung von Betriebssystemen",
                            new BigDecimal(3.0)));
                    put("VU ??bersetzerbau", new Subject("VU ??bersetzerbau",
                            new BigDecimal(6.0)));
                    put("VO Echtzeitsysteme", new Subject("VO Echtzeitsysteme",
                            new BigDecimal(2.0)));
                    put("VU Dependable Systems",
                            new Subject("VU Dependable Systems",
                                    new BigDecimal(4.0)));
                    put("VU Internet Security",
                            new Subject("VU Internet Security",
                                    new BigDecimal(3.0)));
                    put("VU Internet Security",
                            new Subject("VU Internet Security",
                                    new BigDecimal(3.0)));
                    put("VU Privacy Enhancing Technology",
                            new Subject("VU Privacy Enhancing Technology",
                                    new BigDecimal(3.0)));
                    put("UE Daten- und Informatikrecht",
                            new Subject("UE Daten- und Informatikrecht",
                                    new BigDecimal(3.0)));
                    put("VU Vertrags- und Haftungsrecht",
                            new Subject("VU Vertrags- und Haftungsrecht",
                                    new BigDecimal(3.0)));
                    put("VU Semistrukturierte Daten",
                            new Subject("VU Semistrukturierte Daten",
                                    new BigDecimal(3.0)));
                    put("VU Web Engineering", new Subject("VU Web Engineering",
                            new BigDecimal(4.5)));
                    put("VU Computerstatistik",
                            new Subject("VU Computerstatistik",
                                    new BigDecimal(3.0)));
                    put("VU Datenanalyse", new Subject("VU Datenanalyse",
                            new BigDecimal(3.0)));
                    put("VU Statistical Computing",
                            new Subject("VU Statistical Computing",
                                    new BigDecimal(3.0)));
                    put("VO Logik f??r Wissensrepr??sentation",
                            new Subject("VO Logik f??r Wissensrepr??sentation",
                                    new BigDecimal(3.0)));
                    put("UE Logik f??r Wissensrepr??sentation",
                            new Subject("UE Logik f??r Wissensrepr??sentation",
                                    new BigDecimal(3.0)));
                    put("VO Computernumerik", new Subject("VO Computernumerik",
                            new BigDecimal(3.0)));
                    put("UE Computernumerik", new Subject("UE Computernumerik",
                            new BigDecimal(1.5)));
                    put("VO Multivariate Statistik",
                            new Subject("VO Multivariate Statistik",
                                    new BigDecimal(4.5)));
                    put("UE Multivariate Statistik",
                            new Subject("UE Multivariate Statistik",
                                    new BigDecimal(1.5)));
                    put("VU Statistische Simulation und computerintensive " +
                            "Methoden", new Subject(
                            "VU Statistische Simulation und " +
                                    "computerintensive Methoden",
                            new BigDecimal(3.0)));
                    put("VU Argumentieren und Beweisen",
                            new Subject("VU Argumentieren und Beweisen",
                                    new BigDecimal(6.0)));
                    put("VU Programm- und Systemverifikation",
                            new Subject("VU Programm- und Systemverifikation",
                                    new BigDecimal(6.0)));
                    put("VU Softwareprojekt-Beobachtung und -Controlling",
                            new Subject("VU Softwareprojekt-Beobachtung und " +
                                    "-Controlling", new BigDecimal(6.0)));
                    put("VU Software-Qualit??tssicherung",
                            new Subject("VU Software-Qualit??tssicherung",
                                    new BigDecimal(6.0)));
                    put("VU Usability Engineering",
                            new Subject("VU Usability Engineering",
                                    new BigDecimal(3.0)));
                    put("SE Coaching als F??hrungsinstrument 2",
                            new Subject("SE Coaching als F??hrungsinstrument 2",
                                    new BigDecimal(3.0)));
                    put("SE Didaktik in der Informatik",
                            new Subject("SE Didaktik in der Informatik",
                                    new BigDecimal(3.0)));
                    put("VO EDV-Vertragsrecht",
                            new Subject("VO EDV-Vertragsrecht",
                                    new BigDecimal(1.5)));
                    put("VO Einf??hrung in Technik und Gesellschaft",
                            new Subject(
                                    "VO Einf??hrung in Technik und Gesellschaft",
                                    new BigDecimal(3.0)));
                    put("SE Folgenabsch??tzung von Informationstechnologien",
                            new Subject("SE Folgenabsch??tzung von " +
                                    "Informationstechnologien",
                                    new BigDecimal(3.0)));
                    put("VU Forschungsmethoden",
                            new Subject("VU Forschungsmethoden",
                                    new BigDecimal(3.0)));
                    put("VU Kommunikation und Moderation",
                            new Subject("VU Kommunikation und Moderation",
                                    new BigDecimal(3.0)));
                    put("VU Kooperatives Arbeiten",
                            new Subject("VU Kooperatives Arbeiten",
                                    new BigDecimal(3.0)));
                    put("SE Rechtsinformationsrecherche im Internet",
                            new Subject("SE Rechtsinformationsrecherche im " +
                                    "Internet", new BigDecimal(3.0)));
                    put("VU Softskills f??r TechnikerInnen",
                            new Subject("VU Softskills f??r TechnikerInnen",
                                    new BigDecimal(3.0)));
                    put("VU Techniksoziologie und Technikpsychologie",
                            new Subject("VU Techniksoziologie und " +
                                    "Technikpsychologie", new BigDecimal(3.0)));
                    put("VO Theorie und Praxis der Gruppenarbeit", new Subject(
                            "VO Theorie und Praxis der Gruppenarbeit",
                            new BigDecimal(3.0)));
                    put("SE Wissenschaftliche Methodik",
                            new Subject("SE Wissenschaftliche Methodik",
                                    new BigDecimal(3.0)));
                    put("SE Scientific Presentation and Communication",
                            new Subject("SE Scientific Presentation and " +
                                    "Communication", new BigDecimal(3.0)));
                    put("PV Privatissimum aus Fachdidaktik Informatik",
                            new Subject("PV Privatissimum aus Fachdidaktik " +
                                    "Informatik", new BigDecimal(4.0)));
                    put("VU Pr??sentation und Moderation",
                            new Subject("VU Pr??sentation und Moderation",
                                    new BigDecimal(3.0)));
                    put("VO Deduktive Datenbanken",
                            new Subject("VO Deduktive Datenbanken",
                                    new BigDecimal(2.0)));
                }
            };
    private final Map<String, Subject> subjectsFreeChoiceInformatics =
            new HashMap<String, Subject>() {{
                put("VU Pilots in Mobile Interaction: User-centered " +
                        "Interaction Research and Evaluation", new Subject(
                        "VU Pilots in Mobile Interaction: " +
                                "User-centered Interaction Research and " +
                                "Evaluation", new BigDecimal(3.0)));
                put("VU Robotik f??r RoboCup",
                        new Subject("VU Robotik f??r RoboCup",
                                new BigDecimal(6.0)));
                put("KO Reflections on ICTs and Society",
                        new Subject("KO Reflections on ICTs and Society",
                                new BigDecimal(6.0)));
                put("VU Science of Information 1: Transdisciplinary " +
                        "Foundations of Informatics", new Subject(
                        "VU Science of Information 1: " +
                                "Transdisciplinary Foundations of Informatics",
                        new BigDecimal(3.0)));
                put("VU Parallel Computing",
                        new Subject("VU Parallel Computing",
                                new BigDecimal(6.0)));
                put("PR Praktikum aus IT-Security",
                        new Subject("PR Praktikum aus IT-Security",
                                new BigDecimal(6.0)));
                put("SE Formale Semantik nat??rlicher Sprache",
                        new Subject("SE Formale Semantik nat??rlicher Sprache",
                                new BigDecimal(3.0)));
                put("VO Ausgew??hlte Kapitel der Technischen Informatik",
                        new Subject("VO Ausgew??hlte Kapitel der Technischen " +
                                "Informatik", new BigDecimal(3.0)));
                put("SE Seminar f??r DiplomandInnen",
                        new Subject("SE Seminar f??r DiplomandInnen",
                                new BigDecimal(2.0)));
                put("SE Computer Vision Seminar f??r DiplomandInnen",
                        new Subject(
                                "SE Computer Vision Seminar f??r DiplomandInnen",
                                new BigDecimal(2.0)));
                put("VU Softwaretechische Konzepte und " +
                                "Infrastrukturtechnologien zur Identifikation" +
                                " von Objekten " +
                                "und Ger??ten: RFID, Smartcards, NFC und " +
                                "Mobile Phones",
                        new Subject("VU Softwaretechische Konzepte und " +
                                "Infrastrukturtechnologien zur " +
                                "Identifikation von Objekten und Ger??ten: " +
                                "RFID, Smartcards, NFC und Mobile Phones",
                                new BigDecimal(3.0)));
                put("VU Advanced Services Engineering",
                        new Subject("VU Advanced Services Engineering",
                                new BigDecimal(3.0)));
                put("SE Forschungsseminar f??r DiplomandInnen",
                        new Subject("SE Forschungsseminar f??r DiplomandInnen",
                                new BigDecimal(3.0)));
                put("VO Finanzm??rkte, Finanzintermediation und Kapitalanlage",
                        new Subject(
                                "VO Finanzm??rkte, Finanzintermediation und " +
                                        "Kapitalanlage", new BigDecimal(3.5)));
                put("VU Methodisches, industrielles Software-Engineering mit " +
                                "Funktionalen Sprachen am Fallbeispiel Haskell",
                        new Subject("VU Methodisches, industrielles " +
                                "Software-Engineering mit Funktionalen " +
                                "Sprachen am Fallbeispiel Haskell",
                                new BigDecimal(3.0)));
                put("VU IT Governance",
                        new Subject("VU IT Governance", new BigDecimal(3.0)));
                put("VU Current Trends in Computer Science",
                        new Subject("VU Current Trends in Computer Science",
                                new BigDecimal(3.0)));
                put("VU Programmierung von Strategie-Spielen",
                        new Subject("VU Programmierung von Strategie-Spielen",
                                new BigDecimal(3.0)));
                put("VU Medienanalyse und Medienreflexion",
                        new Subject("VU Medienanalyse und Medienreflexion",
                                new BigDecimal(3.0)));
                put("EX Exkursion",
                        new Subject("EX Exkursion", new BigDecimal(3.0)));
                put("VU Privacy Enhancing Technologies",
                        new Subject("VU Privacy Enhancing Technologies",
                                new BigDecimal(3.0)));
                put("VU Theoretische Informatik und Logik f??r CI", new Subject(
                        "VU Theoretische Informatik und Logik f??r CI",
                        new BigDecimal(6.0)));
                put("SE Theorie und Praxis der Evaluierung von innovativen " +
                        "User Interfaces", new Subject(
                        "SE Theorie und Praxis der Evaluierung von " +
                                "innovativen User Interfaces",
                        new BigDecimal(3.0)));
                put("VU Br??ckenkurs Programmierung f??r Studienanf??ngerinnen",
                        new Subject("VU Br??ckenkurs Programmierung f??r " +
                                "Studienanf??ngerinnen", new BigDecimal(3.0)));
                put("VU Mobile (App) Software Engineering",
                        new Subject("VU Mobile (App) Software Engineering",
                                new BigDecimal(3.0)));
                put("VU Runtime Verification",
                        new Subject("VU Runtime Verification",
                                new BigDecimal(3.0)));
                put("VU 2D and 3D Image Registration",
                        new Subject("VU 2D and 3D Image Registration",
                                new BigDecimal(3.0)));
                put("VU Mobile Robotik",
                        new Subject("VU Mobile Robotik", new BigDecimal(4.5)));
                put("SE Critical Algorithm Studies",
                        new Subject("SE Critical Algorithm Studies",
                                new BigDecimal(3.0)));
                put("VU Br??ckenkurs Programmierung f??r Studienanf??nger",
                        new Subject("VU Br??ckenkurs Programmierung f??r " +
                                "Studienanf??nger", new BigDecimal(3.0)));
            }};
    private final Map<String, Tag> tags = new HashMap<String, Tag>() {{
        put("Programmieren", new Tag("Programmieren"));
        put("Java", new Tag("Java", false));
        put("Debug", new Tag("Debug"));
        put("Rekursion", new Tag("Rekursion"));
        put("Boolsche Algebra", new Tag("Boolsche Algebra"));
        put("RISC", new Tag("RISC"));
        put("CISC", new Tag("CISC"));
        put("Pipelining", new Tag("Pipelining"));
        put("ROM", new Tag("ROM"));
        put("PROM/EPROM", new Tag("PROM/EPROM"));
        put("2 Complement", new Tag("2 Complement"));
        put("1 Complement", new Tag("1 Complement"));
        put("Gatterschaltungen", new Tag("Gatterschaltungen"));
        put("Befehlssatz", new Tag("Befehlssatz"));
        put("Digital", new Tag("Digital"));
        put("Zahlentheorie", new Tag("Zahlentheorie"));
        put("Aussagenlogik", new Tag("Aussagenlogik"));
        put("Mengenlehre", new Tag("Mengenlehre"));
        put("Kombinatorik,", new Tag("Kombinatorik,"));
        put("Differenzengleichungen", new Tag("Differenzengleichungen"));
        put("Graphentheorie", new Tag("Graphentheorie"));
        put("Algebraische Strukturen", new Tag("Algebraische Strukturen"));
        put("Lineare Algebra", new Tag("Lineare Algebra"));
        put("Codierungstheorie", new Tag("Codierungstheorie"));
        put("UE", new Tag("UE"));
        put("VU", new Tag("VU"));
        put("VO", new Tag("VO"));
        put("Automaten", new Tag("Automaten"));
        put("regul??re Ausdr??cke", new Tag("regul??re Ausdr??cke"));
        put("Grammatiken", new Tag("Grammatiken"));
        put("Petri-Netze", new Tag("Petri-Netze"));
        put("Pr??dikatenlogik", new Tag("Pr??dikatenlogik"));
        put("EER", new Tag("EER"));
        put("Relationenmodel", new Tag("Relationenmodel"));
        put("Dom??nenkalk??l", new Tag("Dom??nenkalk??l"));
        put("Datenbanksprachen", new Tag("Datenbanksprachen"));
        put("Relationale Entwurfstheorie",
                new Tag("Relationale Entwurfstheorie"));
        put("Normalformen", new Tag("Normalformen"));
        put("Datenintegrit??t", new Tag("Datenintegrit??t"));
        put("SQL", new Tag("SQL"));
        put("JDBC", new Tag("JDBC"));
        put("DBMS", new Tag("DBMS"));
        put("Algorithmen", new Tag("Algorithmen"));
        put("Datenstrukturen", new Tag("Datenstrukturen"));
        put("Visual Computing", new Tag("Visual Computing"));
        put("MATLAB", new Tag("MATLAB"));
        put("Fourier", new Tag("Fourier"));
        put("Analysis", new Tag("Analysis"));
        put("Langweilig", new Tag("Langweilig"));
        put("Theorie", new Tag("Theorie"));
        put("Praxis", new Tag("Praxis"));
        put("Gesellschaft", new Tag("Gesellschaft"));
        put("Human Computer Interaction",
                new Tag("Human Computer Interaction"));
        put("Laplace", new Tag("Laplace"));
        put("Laplace", new Tag("Laplace"));
        put("Folgen", new Tag("Folgen"));
        put("Reihen", new Tag("Reihen"));
        put("Stetigkeit", new Tag("Stetigkeit"));
        put("Grenzwerte", new Tag("Grenzwerte"));
        put("Nullstellen", new Tag("Nullstellen"));
        put("Differentialrechnung", new Tag("Differentialrechnung"));
        put("Funktionen", new Tag("Funktionen"));
        put("Integralrechnung", new Tag("Integralrechnung"));
        put("Objektorientiert", new Tag("Objektorientiert"));
        put("UML", new Tag("UML"));
        put("Grundlagen", new Tag("Grundlagen"));
        put("Hardware", new Tag("Hardware"));
        put("Software", new Tag("Software"));
        put("Computer Science", new Tag("Computer Science"));
        put("Mathe", new Tag("Mathe"));
        put("Spa??", new Tag("Spa??"));
        put("Einfach", new Tag("Einfach"));
        put("Diffizil", new Tag("Diffizil"));
        put("Programmiersprachen", new Tag("Programmiersprachen"));
        put("Gruppenarbeit", new Tag("Gruppenarbeit"));
        put("Abstraktion", new Tag("Abstraktion"));
        put("Teamfaehigkeit", new Tag("Teamfaehigkeit"));
        put("Generizit??t", new Tag("Generizit??t"));
        put("Haskell", new Tag("Haskell"));
        put("Funktional", new Tag("Funktional"));
        put("Lambda-Kalkuel", new Tag("Lambda-Kalkuel"));
        put("Thread", new Tag("Thread"));
        put("Prozes", new Tag("Prozes"));
        put("Synchronisation", new Tag("Synchronisation"));
        put("Betriebssystem", new Tag("Betriebssystem"));
        put("C", new Tag("C"));
        put("Scheduling", new Tag("Scheduling"));
        put("Multithreading", new Tag("Multithreading"));
        put("Deadlock", new Tag("Deadlock"));
        put("Semaphore", new Tag("Semaphore"));
        put("Sequencer", new Tag("Sequencer"));
        put("Eventcounts", new Tag("Eventcounts"));
        put("Producer-Consumer", new Tag("Producer-Consumer"));
        put("Speicherverwaltung", new Tag("Speicherverwaltung"));
        put("Filesysteme", new Tag("Filesysteme"));
        put("Netzwerke", new Tag("Netzwerke"));
        put("Security", new Tag("Security"));
        put("Eventcounts", new Tag("Eventcounts"));
        put("Social engineering", new Tag("Social engineering"));
        put("Physical Break-Ins", new Tag("Physical Break-Ins"));
        put("Dumpster Diving", new Tag("Dumpster Diving"));
        put("Password Cracking", new Tag("Password Cracking"));
        put("Session Hijacking", new Tag("Session Hijacking"));
        put("Spoofing", new Tag("Spoofing"));
        put("Viruses", new Tag("Viruses"));
        put("Worms", new Tag("Worms"));
        put("Trojan Horses", new Tag("Trojan Horses"));
        put("Phishing", new Tag("Phishing"));
        put("Encryption", new Tag("Encryption"));
        put("Spyware", new Tag("Spyware"));
        put("Phishing", new Tag("Phishing"));
        put("Spyware", new Tag("Spyware"));
        put("Adware", new Tag("Adware"));
        put("Cryptography", new Tag("Cryptography"));
        put("Risk Analysis", new Tag("Risk Analysis"));
        put("Recht", new Tag("Recht"));
        put("Wirtschaft", new Tag("Wirtschaft"));
        put("Technik", new Tag("Technik"));
        put("Statistik", new Tag("Statistik"));
        put("Wahrscheinlichkeitstheorie",
                new Tag("Wahrscheinlichkeitstheorie"));
        put("Verteilung", new Tag("Verteilung"));
        put("Histogramm", new Tag("Histogramm"));
        put("Grundlagen der Bayes'schen",
                new Tag("Grundlagen der Bayes'schen"));
        put("Wahrscheinlichkeitsraeume", new Tag("Wahrscheinlichkeitsraeume"));
        put("Stochastik", new Tag("Stochastik"));
        put("Gesetz der gro??en Zahlen", new Tag("Gesetz der gro??en Zahlen"));
        put("K??nstliche Intelligenz", new Tag("K??nstliche Intelligenz"));
        put("Projektmanagement", new Tag("Projektmanagement"));
        put("Planning", new Tag("Planning"));
        put("Testing", new Tag("Testing"));
        put("Softwarequalit??tssicherung",
                new Tag("Softwarequalit??tssicherung"));
        put("Risikomanagement", new Tag("Risikomanagement"));
        put("Qualit??tsmanagement", new Tag("Qualit??tsmanagement"));
        put("Projektmarketing", new Tag("Projektmarketing"));
        put("Risikomanagement", new Tag("Risikomanagement"));
        put("Sprint", new Tag("Sprint"));
        put("SCRUM", new Tag("SCRUM"));
        put("PR", new Tag("PR"));
        put("Verteilte Systeme", new Tag("Verteilte Systeme"));
        put("Protokolle", new Tag("Protokolle"));
        put("Kommunikationsmechanismen", new Tag("Kommunikationsmechanismen"));
        put("Namenssystemen", new Tag("Namenssystemen"));
        put("Fehlertoleranz", new Tag("Fehlertoleranz"));
        put("Client-Server", new Tag("Client-Server"));
        put("Cloud Computing", new Tag("Cloud Computing"));
        put("Sichere Kan??le", new Tag("Sichere Kan??le"));
        put("Wissenschaft", new Tag("Wissenschaft"));
        put("Sozial", new Tag("Sozial"));
        put("UI Design", new Tag("UI Design"));
        put("Interaktionskonzepte", new Tag("Interaktionskonzepte"));
        put("Interface & Interaction Design",
                new Tag("Interface & Interaction Design"));
        put("Interaktive Systeme", new Tag("Interaktive Systeme"));
        put("Benutzerschnittstellen", new Tag("Benutzerschnittstellen"));
        put("Wissensbasierte Systeme", new Tag("Wissensbasierte Systeme"));
        put("Logik", new Tag("Logik"));
        put("Nichtmonotones Schlie??en", new Tag("Nichtmonotones Schlie??en"));
        put("Answer-Set Programmierung", new Tag("Answer-Set Programmierung"));
        put("Probabilistische Methoden", new Tag("Probabilistische Methoden"));
        put("Forschung", new Tag("Forschung"));
        put("Vorstellen", new Tag("Vorstellen"));
        put("Literatur", new Tag("Literatur"));
        put("Seminar", new Tag("Seminar"));
        put("Pr??sentation", new Tag("Pr??sentation"));
        put("Assembler", new Tag("Assembler"));
        put("TinyOS", new Tag("TinyOS"));
        put("Treiber", new Tag("Treiber"));
        put("??bersetzer", new Tag("??bersetzer"));
        put("Interpreter", new Tag("Interpreter"));
        put("Virtual Machine", new Tag("Virtual Machine"));
        put("Abstrakt Syntaxbaum", new Tag("Abstrakt Syntaxbaum"));
        put("C++", new Tag("C++"));
        put("#C", new Tag("#C"));
        put("JavaScript", new Tag("JavaScript"));
        put("Bootstrapping", new Tag("Bootstrapping"));
        put("Bison", new Tag("Bison"));
        put("Yacc", new Tag("Yacc"));
        put("Echtzeitsysteme", new Tag("Echtzeitsysteme"));
        put("Numerik", new Tag("Numerik"));
        put("Rundungsfehler", new Tag("Rundungsfehler"));
        put("Gleichungssysteme", new Tag("Gleichungssysteme"));
        put("numerische Differentiation",
                new Tag("numerische Differentiation"));
        put("numerische Integration", new Tag("numerische Integration"));
        put("Interpolation", new Tag("Interpolation"));
        put("Approximation", new Tag("Approximation"));
        put("numerische Differenzialgleichungen",
                new Tag("numerische Differenzialgleichungen"));
        put("Hauptkomponentenanalyse", new Tag("Hauptkomponentenanalyse"));
        put("Faktorenanalyse", new Tag("Faktorenanalyse"));
        put("Diskriminanzanalyse", new Tag("Diskriminanzanalyse"));
        put("Clusteranalyse", new Tag("Clusteranalyse"));
        put("Regressionsanalyse", new Tag("Regressionsanalyse"));
        put("Multivariate Methoden", new Tag("Multivariate Methoden"));
        put("Varianz", new Tag("Varianz"));
        put("Simulation", new Tag("Simulation"));
        put("Query Languages", new Tag("Query Languages"));
    }};
    private final Map<String, StudentEntity> studentMap =
            new HashMap<String, StudentEntity>() {
                {
                    put("Caroline Black",
                            new StudentEntity("s1123960", "Caroline Black",
                                    "caroline.black@uis.at",
                                    new UserAccountEntity("student", "pass",
                                            Role.STUDENT)));
                    put("Emma Dowd", new StudentEntity("s1127157", "Emma Dowd",
                            "emma.dowd@gmail.com",
                            new UserAccountEntity("emma", "pass",
                                    Role.STUDENT)));
                    put("John Terry",
                            new StudentEntity("s1126441", "John Terry",
                                    "john.terry@uis.at",
                                    new UserAccountEntity("john", "pass",
                                            Role.STUDENT)));
                    put("Joan Watson",
                            new StudentEntity("s0227157", "Joan Watson",
                                    "joan.watson@uit.at"));
                    put("James Bond",
                            new StudentEntity("s1527199", "James Bond",
                                    "jamesbond_007@yahoo.com"));
                    put("Trevor Bond",
                            new StudentEntity("s0445157", "Trevor Bond",
                                    "trevor@uis.at"));
                    put("Mathematician",
                            new StudentEntity("s0000001", "Diego Costa",
                                    "diego.cost@yahoo.com",
                                    new UserAccountEntity("diego", "pass",
                                            Role.STUDENT)));
                    put("SimilarToMathematician",
                            new StudentEntity("s0000002", "Cesc Fabregas",
                                    "cesc.fabregas@yahoo.com",
                                    new UserAccountEntity("cesc", "pass",
                                            Role.STUDENT)));
                    put("NewStudent",
                            new StudentEntity("s0000003", "Eden Hazard",
                                    "eden.hazard@yahoo.com",
                                    new UserAccountEntity("eden", "pass",
                                            Role.STUDENT)));
                }
            };
    private final Map<String, LecturerEntity> lecturerMap =
            new HashMap<String, LecturerEntity>() {
                {
                    put("Carol Sanderson",
                            new LecturerEntity("l0100010", "Carol Sanderson",
                                    "carol@uis.at"));
                    put("Una Walker",
                            new LecturerEntity("l0100011", "Una Walker",
                                    "una.walker@uis.at",
                                    new UserAccountEntity("lecturer", "pass",
                                            Role.LECTURER)));
                    put("Connor MacLeod",
                            new LecturerEntity("l0203019", "Connor MacLeod",
                                    "connor@gmail.com"));
                    put("Eric Wilkins",
                            new LecturerEntity("l1100010", "Eric Wilkins",
                                    "e1234567@tuwien.ac.at",
                                    new UserAccountEntity("eric", "pass",
                                            Role.LECTURER)));
                    put("Benjamin Piper",
                            new LecturerEntity("l9123410", "Benjamin Piper",
                                    "ben@uis.at",
                                    new UserAccountEntity("ben", "pass",
                                            Role.LECTURER)));
                }
            };
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UisUserRepository uisUserRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private SemestreRepository semesterRepository;
    @Autowired
    private StudyPlanRepository studyPlanRepository;
    @Autowired
    private SubjectForStudyPlanRepository subjectForStudyPlanRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private PendingAccountActivationRepository
            pendingAccountActivationRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StduentRepository stduentRepository;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private StudentSubjectPreferenceStore studentSubjectPreferenceStore;
    private List<StudyPlanEntity> studyplans;
    private List<Semester> semesters;
    private List<Subject> subjects;
    private List<Course> courses;
    private List<StudentEntity> students;
    private List<LecturerEntity> lecturers;
    private Map<String, Subject>
            subjectsBachelorSoftwareAndInformationEngineering;
    private Map<String, Course>
            coursesBachelorSoftwareAndInformationEngineering;

    /**
        Initialize.
     */
    @Transactional
    public void initialize() {
        userAccountRepository
                .save(new UserAccountEntity("admin", "pass", Role.ADMIN));

        createTags();

        createSubjects();

        createSemesters();

        createCourses();

        createStudyPlans();

        createUsers();

        registerStudentsToStudyPlans();

        registerSubjectsToLecturers();

        addTagsToCourses();

        addDescriptionToCourses();

        addSubjectsToStudyPlans();

        registerCoursesToStudents();

        giveGrades();

        giveFeedback();
    }

    private void createTags() {
        tagRepository.save(tags.values());
    }

    private void createSubjects() {
        createSubjectsBachelorSoftwareAndInformationEngineering();
        Iterable<Subject> subjectsTemp = subjectRepository
                .save(subjectsBachelorSoftwareAndInformationEngineering
                        .values());

        this.subjects = StreamSupport.stream(subjectsTemp.spliterator(), false)
                .collect(Collectors.toList());
    }

    private void createSubjectsBachelorSoftwareAndInformationEngineering() {
        subjectsBachelorSoftwareAndInformationEngineering = new HashMap<>();
        subjectsBachelorSoftwareAndInformationEngineering
                .putAll(subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester1);
        subjectsBachelorSoftwareAndInformationEngineering
                .putAll(subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester2);
        subjectsBachelorSoftwareAndInformationEngineering
                .putAll(subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester3);
        subjectsBachelorSoftwareAndInformationEngineering
                .putAll(subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester4);
        subjectsBachelorSoftwareAndInformationEngineering
                .putAll(subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester5);
        subjectsBachelorSoftwareAndInformationEngineering
                .putAll(subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester6);
        subjectsBachelorSoftwareAndInformationEngineering
                .putAll(subjectsOptionalBachelorSoftwareAndInformationEngineering);
        subjectsBachelorSoftwareAndInformationEngineering
                .putAll(subjectsFreeChoiceInformatics);
    }

    private void createSemesters() {
        Iterable<Semester> semestersTemp = semesterRepository.save(asList(
                new Semester(2016, SemesterTypeEnum.SummerSemester),
                new Semester(2016, SemesterTypeEnum.WinterSemester),
                new Semester(2017, SemesterTypeEnum.SummerSemester)));

        this.semesters = StreamSupport.stream(semestersTemp.spliterator(), false)
                .collect(Collectors.toList());
    }

    private void createCourses() {
        createCoursesBachelorSoftwareAndInformationEngineering();

        Collection<Course> coursesTemp =
                coursesBachelorSoftwareAndInformationEngineering.values();

        this.courses = StreamSupport
                .stream(courseRepository.save(coursesTemp).spliterator(), false)
                .collect(Collectors.toList());
    }

    private void createCoursesBachelorSoftwareAndInformationEngineering() {
        coursesBachelorSoftwareAndInformationEngineering = new HashMap<>();

        for (String subjectName :
                subjectsBachelorSoftwareAndInformationEngineering
                .keySet()) {
            Subject subject = subjectsBachelorSoftwareAndInformationEngineering
                    .get(subjectName);

            Course course =
                    new Course(subject, semesters.get(2));
            course.setStudentLimits(20);
            coursesBachelorSoftwareAndInformationEngineering
                    .put(subjectName, course);
        }
    }

    private void createStudyPlans() {
        Iterable<StudyPlanEntity> studyplansTemp = studyPlanRepository.save(asList(
                new StudyPlanEntity(
                        "Bachelor Software and Information Engineering",
                        new EctsDistributionEntity(new BigDecimal(136),
                                new BigDecimal(30), new BigDecimal(14))),
                new StudyPlanEntity("Master Business Informatics",
                        new EctsDistributionEntity(new BigDecimal(30),
                                new BigDecimal(70), new BigDecimal(20))),
                new StudyPlanEntity("Master Computational Intelligence",
                        new EctsDistributionEntity(new BigDecimal(60),
                                new BigDecimal(30), new BigDecimal(30))),
                new StudyPlanEntity("Master Visual Computing",
                        new EctsDistributionEntity(new BigDecimal(60),
                                new BigDecimal(30), new BigDecimal(30))),
                new StudyPlanEntity("Master Medical Informatics",
                        new EctsDistributionEntity(new BigDecimal(60),
                                new BigDecimal(30), new BigDecimal(30)), false),
                new StudyPlanEntity("Master Computer Science",
                        new EctsDistributionEntity(new BigDecimal(60),
                                new BigDecimal(30), new BigDecimal(30)))));

        this.studyplans = StreamSupport.stream(studyplansTemp.spliterator(), false)
                .collect(Collectors.toList());
    }

    private void createUsers() {
        pendingAccountActivationRepository
                .save(new PendingAcountActivation("test",
                        new StudentEntity("test", "Test User",
                                "test-user@uis.at")));
        List<UisUserEntity> usersList = new ArrayList<>(studentMap.values());
        usersList.addAll(lecturerMap.values());

        Iterable<UisUserEntity> users = uisUserRepository.save(usersList);

        students = StreamSupport.stream(users.spliterator(), false)
                .filter(it -> it instanceof StudentEntity)
                .map(it -> (StudentEntity) it).collect(Collectors.toList());

        lecturers = StreamSupport.stream(users.spliterator(), false)
                .filter(it -> it instanceof LecturerEntity)
                .map(it -> (LecturerEntity) it).collect(Collectors.toList());
    }

    private void registerStudentsToStudyPlans() {
        studentMap.get("John Terry").addStudyplans(
                new StudyPlanRegistration(studyplans.get(0), semesters.get(1)));
        studentMap.get("Caroline Black").addStudyplans(
                new StudyPlanRegistration(studyplans.get(1), semesters.get(1)));
        studentMap.get("Emma Dowd").addStudyplans(
                new StudyPlanRegistration(studyplans.get(2), semesters.get(0)));
        studentMap.get("Joan Watson").addStudyplans(
                new StudyPlanRegistration(studyplans.get(3), semesters.get(1)));
        studentMap.get("James Bond").addStudyplans(
                new StudyPlanRegistration(studyplans.get(4), semesters.get(1)));
        studentMap.get("James Bond").addStudyplans(
                new StudyPlanRegistration(studyplans.get(5), semesters.get(0)));
        studentMap.get("Trevor Bond").addStudyplans(
                new StudyPlanRegistration(studyplans.get(1), semesters.get(1)));
        studentMap.get("Mathematician").addStudyplans(
                new StudyPlanRegistration(studyplans.get(0), semesters.get(1)));
        studentMap.get("SimilarToMathematician").addStudyplans(
                new StudyPlanRegistration(studyplans.get(0), semesters.get(1)));
        studentMap.get("NewStudent").addStudyplans(
                new StudyPlanRegistration(studyplans.get(0), semesters.get(1)));
    }

    private void registerSubjectsToLecturers() {
        subjectsBachelorSoftwareAndInformationEngineering
                .get("UE Studieneingangsgespr??ch")
                .addLecturers(lecturerMap.get("Carol Sanderson"));
        subjectsBachelorSoftwareAndInformationEngineering
                .get("VU Programmkonstruktion")
                .addLecturers(lecturerMap.get("Eric Wilkins"));
        subjectsBachelorSoftwareAndInformationEngineering
                .get("VU Datenmodellierung")
                .addLecturers(lecturerMap.get("Eric Wilkins"));


        subjects.get(0).addLecturers(lecturers.get(3));
        subjects.get(1).addLecturers(lecturers.get(3));
        subjects.get(2).addLecturers(lecturers.get(3), lecturers.get(4));

        subjectRepository.save(subjects);

        Subject s = subjectsBachelorSoftwareAndInformationEngineering
                .get("VU Technische Grundlagen der Informatik");
        s.addLecturers(lecturers.get(3));
        subjectRepository.save(s);

        s = subjectsBachelorSoftwareAndInformationEngineering
                .get("VO Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik");
        s.addLecturers(lecturers.get(3));
        subjectRepository.save(s);
    }


    private void addTagsToCourses() {
        addTagsToBachelorSoftwareAndInformationEngineeringCourses();
        courseRepository.save(coursesBachelorSoftwareAndInformationEngineering
                .values());
    }

    private void addTagsToBachelorSoftwareAndInformationEngineeringCourses() {
        addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester1();
        addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester2();
        addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester3();
        addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester4();
        addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester5();
        addTagsToBachelorSoftwareAndInformationEngineeringOptionalCourses();
    }

    private void addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester1() {
        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Programmkonstruktion")
                .addTags(tags.get("VU"), tags.get("Programmieren"),
                        tags.get("Java"), tags.get("Debug"),
                        tags.get("Rekursion"), tags.get("Software"),
                        tags.get("Einfach"), tags.get("Grundlagen"),
                        tags.get("Objektorientiert"), tags.get("Generizit??t"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Technische Grundlagen der Informatik")
                .addTags(tags.get("VU"), tags.get("Boolsche Algebra"),
                        tags.get("RISC"), tags.get("CISC"),
                        tags.get("Pipelining"), tags.get("ROM"),
                        tags.get("PROM/EPROM"), tags.get("2 Complement"),
                        tags.get("1 Complement"), tags.get("Gatterschaltungen"),
                        tags.get("Befehlssatz"), tags.get("Digital"),
                        tags.get("Diffizil"), tags.get("Grundlagen"),
                        tags.get("Hardware"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik")
                .addTags(tags.get("VO"), tags.get("Zahlentheorie"),
                        tags.get("Aussagenlogik"), tags.get("Mengenlehre"),
                        tags.get("Mathe"), tags.get("Kombinatorik"),
                        tags.get("Differenzengleichungen"),
                        tags.get("Graphentheorie"),
                        tags.get("Algebraische Strukturen"),
                        tags.get("Lineare Algebra"),
                        tags.get("Codierungstheorie"), tags.get("Einfach"),
                        tags.get("Funktionen"), tags.get("Grundlagen"),
                        tags.get("Gleichungssysteme")

                );

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik")
                .addTags(tags.get("UE"), tags.get("Zahlentheorie"),
                        tags.get("Aussagenlogik"), tags.get("Mengenlehre"),
                        tags.get("Mathe"), tags.get("Kombinatorik"),
                        tags.get("Differenzengleichungen"),
                        tags.get("Graphentheorie"),
                        tags.get("Algebraische Strukturen"),
                        tags.get("Lineare Algebra"),
                        tags.get("Codierungstheorie"), tags.get("Einfach"),
                        tags.get("Funktionen"), tags.get("Grundlagen"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Formale Modellierung")
                .addTags(tags.get("VU"), tags.get("Automaten"),
                        tags.get("regul??re Ausdr??cke"),
                        tags.get("formale Grammatiken"),
                        tags.get("Aussagenlogik"), tags.get("Petri-Netze"),
                        tags.get("Pr??dikatenlogik"), tags.get("Einfach"),
                        tags.get("Grundlagen"), tags.get("Lambda-Kalkuel"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Datenmodellierung")
                .addTags(tags.get("VU"), tags.get("EER"),
                        tags.get("Relationenmodel"), tags.get("Dom??nenkalk??l"),
                        tags.get("Datenbanksprachen"), tags.get("Normalformen"),
                        tags.get("Relationale Entwurfstheorie"),
                        tags.get("Datenintegrit??t"), tags.get("Einfach"),
                        tags.get("SQL"), tags.get("Grundlagen"));
    }

    private void addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester2() {
        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Algorithmen und Datenstrukturen 1")
                .addTags(tags.get("VU"), tags.get("Datenstrukturen"),
                        tags.get("Algorithmen"), tags.get("Java"),
                        tags.get("Programmieren"), tags.get("Debug"),
                        tags.get("Rekursion"), tags.get("Software"),
                        tags.get("Graphentheorie"), tags.get("Einfach"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Algorithmen und Datenstrukturen 2")
                .addTags(tags.get("VU"), tags.get("Datenstrukturen"),
                        tags.get("Algorithmen"), tags.get("Java"),
                        tags.get("Programmieren"), tags.get("Debug"),
                        tags.get("Rekursion"), tags.get("Software"),
                        tags.get("Graphentheorie"), tags.get("Diffizil"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Einf??hrung in Visual Computing")
                .addTags(tags.get("VU"), tags.get("Visual Computing"),
                        tags.get("MATLAB"), tags.get("Mathe"),
                        tags.get("Fourier"), tags.get("Analysis"),
                        tags.get("Diffizil"), tags.get("Programmieren"),
                        tags.get("Grundlagen"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Gesellschaftliche Spannungsfelder der Informatik")
                .addTags(tags.get("VU"), tags.get("Langweilig"),
                        tags.get("Theorie"), tags.get("Einfach"),
                        tags.get("Gesellschaft"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Basics of Human Computer Interaction")
                .addTags(tags.get("VU"), tags.get("Human Computer Interaction"),
                        tags.get("Einfach"), tags.get("Grundlagen"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Analysis f??r Informatik und Wirtschaftsinformatik")
                .addTags(tags.get("VU"), tags.get("Mathe"),
                        tags.get("Analysis"), tags.get("Fourier"),
                        tags.get("Laplace"), tags.get("Diffizil"),
                        tags.get("Folgen"), tags.get("Reihen"),
                        tags.get("Stetigkeit"), tags.get("Grenzwerte"),
                        tags.get("Nullstellen"),
                        tags.get("Differentialrechnung"),
                        tags.get("Integralrechnung"), tags.get("Funktionen"),
                        tags.get("Gleichungssysteme"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Analysis f??r Informatik und Wirtschaftsinformatik")
                .addTags(tags.get("UE"), tags.get("Mathe"),
                        tags.get("Analysis"), tags.get("Fourier"),
                        tags.get("Laplace"), tags.get("Diffizil"),
                        tags.get("Folgen"), tags.get("Reihen"),
                        tags.get("Stetigkeit"), tags.get("Grenzwerte"),
                        tags.get("Nullstellen"),
                        tags.get("Differentialrechnung"),
                        tags.get("Integralrechnung"), tags.get("Funktionen"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Objektorientierte Modellierung")
                .addTags(tags.get("VU"), tags.get("Objektorientiert"),
                        tags.get("UML"), tags.get("Grundlagen"),
                        tags.get("Einfach"));
    }

    private void addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester3() {
        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Objektorientierte Programmiertechniken")
                .addTags(tags.get("VU"), tags.get("Objektorientiert"),
                        tags.get("Programmiersprachen"),
                        tags.get("Programmieren"), tags.get("Java"),
                        tags.get("Debug"), tags.get("Software"),
                        tags.get("Teamfaehigkeit"), tags.get("Gruppenarbeit"),
                        tags.get("Abstraktion"), tags.get("Diffizil"),
                        tags.get("Generizit??t"), tags.get("Theorie"),
                        tags.get("Praxis"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Funktionale Programmierung")
                .addTags(tags.get("VU"), tags.get("Programmiersprachen"),
                        tags.get("Programmieren"), tags.get("Diffizil"),
                        tags.get("Haskell"), tags.get("Debug"),
                        tags.get("Software"), tags.get("Teamfaehigkeit"),
                        tags.get("Gruppenarbeit"), tags.get("Haskell"),
                        tags.get("Diffizil"), tags.get("Funktional"),
                        tags.get("Theorie"), tags.get("Praxis"),
                        tags.get("Grundlagen"), tags.get("Rekursion"),
                        tags.get("Funktionen"), tags.get("Lambda-Kalkuel"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Betriebssysteme")
                .addTags(tags.get("V0"), tags.get("Theorie"),
                        tags.get("Thread"), tags.get("Prozess"),
                        tags.get("Synchronisation"), tags.get("Grundlagen"),
                        tags.get("Betriebssystem"), tags.get("Scheduling"),
                        tags.get("Multithreading"), tags.get("Deadlock"),
                        tags.get("Datenstrukturen"), tags.get("Semaphore"),
                        tags.get("Diffizil"), tags.get("Sequencer"),
                        tags.get("Eventcounts"), tags.get("Producer-Consumer"),
                        tags.get("Speicherverwaltung"), tags.get("Filesysteme"),
                        tags.get("Netzwerk"), tags.get("Security"),
                        tags.get("Software"), tags.get("C"),
                        tags.get("Client-Server"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Betriebssysteme")
                .addTags(tags.get("UE"), tags.get("Programmieren"),
                        tags.get("Thread"), tags.get("Prozess"),
                        tags.get("Synchronisation"), tags.get("Grundlagen"),
                        tags.get("Betriebssystem"), tags.get("Scheduling"),
                        tags.get("Multithreading"), tags.get("Deadlock"),
                        tags.get("Datenstrukturen"), tags.get("Semaphore"),
                        tags.get("Sequencer"), tags.get("Eventcounts"),
                        tags.get("Producer-Consumer"),
                        tags.get("Speicherverwaltung"), tags.get("Filesysteme"),
                        tags.get("Netzwerk"), tags.get("Security"),
                        tags.get("Software"), tags.get("Diffizil"),
                        tags.get("C"), tags.get("Client-Server"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Introduction to Security")
                .addTags(tags.get("VU"), tags.get("Programmieren"),
                        tags.get("Social engineering"),
                        tags.get("Physical Break-Ins"),
                        tags.get("Dumpster Diving"), tags.get("Grundlagen"),
                        tags.get("Password Cracking"),
                        tags.get("Session Hijacking"), tags.get("Network"),
                        tags.get("Spoofing"), tags.get("Viruses"),
                        tags.get("Worms"), tags.get("Trojan Horses"),
                        tags.get("Phishing"), tags.get("Encryption"),
                        tags.get("Netzwerk"), tags.get("Security"),
                        tags.get("Software"), tags.get("Diffizil"),
                        tags.get("Spyware"), tags.get("Adware"),
                        tags.get("Cryptography"), tags.get("Risk Analysis"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Daten- und Informatikrecht")
                .addTags(tags.get("VU"), tags.get("Theorie"), tags.get("Recht"),
                        tags.get("Einfach"), tags.get("Grundlagen"),
                        tags.get("Wirtschaft"), tags.get("Technik"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Datenbanksysteme")
                .addTags(tags.get("VU"), tags.get("EER"),
                        tags.get("Relationenmodel"), tags.get("Dom??nenkalk??l"),
                        tags.get("Datenbanksprachen"), tags.get("Normalformen"),
                        tags.get("SQL"), tags.get("Datenintegrit??t"),
                        tags.get("Diffizil"), tags.get("JDBC"),
                        tags.get("DBMS"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Statistik und Wahrscheinlichkeitstheorie")
                .addTags(tags.get("VO"), tags.get("Mathe"),
                        tags.get("Statistik"),
                        tags.get("Wahrscheinlichkeitstheorie"),
                        tags.get("Verteilung"), tags.get("Histogramm"),
                        tags.get("Wahrscheinlichkeitsr??ume"),
                        tags.get("Stochastik"), tags.get("Grundlagen"),
                        tags.get("Gesetz der gro??en Zahlen"),
                        tags.get("Grundlagen der Bayes'schen"),
                        tags.get("Diffizil"), tags.get("Zahlentheorie"),
                        tags.get("Mengenlehre"), tags.get("Kombinatorik"),
                        tags.get("Varianz")

                );

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Statistik und Wahrscheinlichkeitstheorie")
                .addTags(tags.get("UE"), tags.get("Mathe"),
                        tags.get("Statistik"),
                        tags.get("Wahrscheinlichkeitstheorie"),
                        tags.get("Verteilung"), tags.get("Histogramm"),
                        tags.get("Wahrscheinlichkeitsr??ume"),
                        tags.get("Stochastik"), tags.get("Grundlagen"),
                        tags.get("Gesetz der gro??en Zahlen"),
                        tags.get("Grundlagen der Bayes'schen"),
                        tags.get("Diffizil"), tags.get("Zahlentheorie"),
                        tags.get("Mengenlehre"), tags.get("Kombinatorik")

                );
    }

    private void addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester4() {
        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Einf??hrung in die K??nstliche Intelligenz")
                .addTags(tags.get("VU"), tags.get("K??nstliche Intelligenz"),
                        tags.get("Grundlagen"), tags.get("Programmieren"),
                        tags.get("Theorie"), tags.get("Diffizil"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Theoretische Informatik und Logik")
                .addTags(tags.get("VU"), tags.get("Automaten"),
                        tags.get("regul??re Ausdr??cke"),
                        tags.get("formale Grammatiken"),
                        tags.get("Aussagenlogik"), tags.get("Petri-Netze"),
                        tags.get("Pr??dikatenlogik"), tags.get("Diffizil"),
                        tags.get("Lambda-Kalkuel"), tags.get("Logik"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Software Engineering und Projektmanagement")
                .addTags(tags.get("VO"), tags.get("Theorie"),
                        tags.get("Software"), tags.get("Projektmanagement"),
                        tags.get("Planning"), tags.get("UML"),
                        tags.get("Objektorientiert"),
                        tags.get("Softwarequalit??tssicherung"),
                        tags.get("Risikomanagement"),
                        tags.get("Qualit??tsmanagement"),
                        tags.get("Projektmarketing"), tags.get("Einfach"),
                        tags.get("Sprint"), tags.get("SCRUM"), tags.get("SQL"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("PR Software Engineering und Projektmanagement")
                .addTags(tags.get("PR"), tags.get("Programmieren"),
                        tags.get("Testing"), tags.get("Software"),
                        tags.get("Projektmanagement"),
                        tags.get("Gruppenarbeit"), tags.get("Teamfaehigkeit"),
                        tags.get("Debug"), tags.get("Planning"),
                        tags.get("UML"), tags.get("Objektorientiert"),
                        tags.get("Softwarequalit??tssicherung"),
                        tags.get("Risikomanagement"),
                        tags.get("Qualit??tsmanagement"),
                        tags.get("Projektmarketing"), tags.get("Diffizil"),
                        tags.get("Praxis"), tags.get("Sprint"),
                        tags.get("SCRUM"), tags.get("CRUD"), tags.get("SQL"),
                        tags.get("UML"));
    }

    private void addTagsToBachelorSoftwareAndInformationEngineeringCoursesSemester5() {
        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Verteilte Systeme")
                .addTags(tags.get("VO"), tags.get("Theorie"),
                        tags.get("Software"), tags.get("Verteilte Systeme"),
                        tags.get("Grundlagen"), tags.get("Netzwerk"),
                        tags.get("Protokolle"), tags.get("Algorithmen"),
                        tags.get("Kommunikationsmechanismen"),
                        tags.get("Namenssystemen"), tags.get("Fehlertoleranz"),
                        tags.get("Diffizil"), tags.get("Client-Server"),
                        tags.get("Thread"), tags.get("Security"),
                        tags.get("Encryption"), tags.get("Scheduling"),
                        tags.get("Synchronisation"),
                        tags.get("Cloud Computing"),
                        tags.get("Sichere Kan??le"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Verteilte Systeme")
                .addTags(tags.get("UE"), tags.get("Programmieren"),
                        tags.get("Testing"), tags.get("Software"),
                        tags.get("Verteilte Systeme"),
                        tags.get("Gruppenarbeit"), tags.get("Teamfaehigkeit"),
                        tags.get("Debug"), tags.get("Netzwerk"),
                        tags.get("Objektorientiert"), tags.get("Encryption"),
                        tags.get("Scheduling"), tags.get("Algorithmen"),
                        tags.get("Synchronisation"), tags.get("Diffizil"),
                        tags.get("Praxis"), tags.get("Client-Server"),
                        tags.get("Thread"), tags.get("Fehlertoleranz"),
                        tags.get("Protokolle"),
                        tags.get("Kommunikationsmechanismen"),
                        tags.get("Cloud Computing"),
                        tags.get("Sichere Kan??le"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Gesellschaftswissenschaftliche Grundlagen der " +
                        "Informatik")
                .addTags(tags.get("VU"), tags.get("Wissenschaft"),
                        tags.get("Theorie"), tags.get("Technik"),
                        tags.get("Gesellschaft"), tags.get("Grundlagen"),
                        tags.get("Einfach"), tags.get("Sozial"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Interface and Interaction Design")
                .addTags(tags.get("VU"), tags.get("Programmieren"),
                        tags.get("UI Design"), tags.get("Software"),
                        tags.get("Interaktionskonzepte"),
                        tags.get("Interface & Interaction Design"),
                        tags.get("Interaktive Systeme"), tags.get("Debug"),
                        tags.get("Einfach"),
                        tags.get("Benutzerschnittstellen"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Einf??hrung in wissensbasierte Systeme")
                .addTags(tags.get("VU"), tags.get("Theorie"),
                        tags.get("Programmieren"),
                        tags.get("Wissensbasierte Systeme"),
                        tags.get("Grundlagen"), tags.get("Logik"),
                        tags.get("Nichtmonotones Schlie??en"),
                        tags.get("Answer-Set Programmierung"),
                        tags.get("Probabilistische Methoden"),
                        tags.get("Diffizil"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Einf??hrung in wissensbasierte Systeme")
                .addTags(tags.get("VU"), tags.get("Theorie"),
                        tags.get("Wissenschaft"), tags.get("Forschung"),
                        tags.get("Vorstellen"), tags.get("Einfach"),
                        tags.get("Literatur"), tags.get("Seminar"),
                        tags.get("Pr??sentation"));
    }

    private void addTagsToBachelorSoftwareAndInformationEngineeringOptionalCourses() {
        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Deduktive Datenbanken")
                .addTags(tags.get("VO"), tags.get("Datenbanksprachen"),
                        tags.get("SQL"), tags.get("Datenintegrit??t"),
                        tags.get("Diffizil"), tags.get("JDBC"),
                        tags.get("DBMS"), tags.get("Query Languages"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Programmierung von Betriebssystemen")
                .addTags(tags.get("UE"), tags.get("Theorie"),
                        tags.get("Thread"), tags.get("Prozess"),
                        tags.get("Synchronisation"), tags.get("Rekursion"),
                        tags.get("Hardware"), tags.get("Scheduling"),
                        tags.get("Multithreading"), tags.get("Deadlock"),
                        tags.get("Datenstrukturen"), tags.get("Semaphore"),
                        tags.get("Semaphore"), tags.get("Sequencer"),
                        tags.get("Eventcounts"), tags.get("Producer-Consumer"),
                        tags.get("Speicherverwaltung"), tags.get("Filesysteme"),
                        tags.get("Netzwerke"), tags.get("Security"),
                        tags.get("Software"), tags.get("Teamfaehigkeit"),
                        tags.get("Gruppenarbeit"), tags.get("Diffizil"),
                        tags.get("C"), tags.get("Debug"),
                        tags.get("Betriebssystem"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Microcontroller")
                .addTags(tags.get("VU"), tags.get("Theorie"),
                        tags.get("Thread"), tags.get("Prozess"),
                        tags.get("Assembler"), tags.get("Programmieren"),
                        tags.get("TinyOS"), tags.get("ROM"),
                        tags.get("2 Complement"), tags.get("PROM/EPROM"),
                        tags.get("Treiber"), tags.get("Praxis"),
                        tags.get("Speicherverwaltung"), tags.get("Netzwerke"),
                        tags.get("Software"), tags.get("Diffizil"),
                        tags.get("C"), tags.get("Debug"),
                        tags.get("Betriebssystem"));


        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Abstrakte Maschinen")
                .addTags(tags.get("VO"), tags.get("Theorie"),
                        tags.get("Assembler"), tags.get("Programmiersprachen"),
                        tags.get("??bersetzer"), tags.get("Interpreter"),
                        tags.get("Virtual Machine"), tags.get("Diffizil"),
                        tags.get("C"), tags.get("Abstrakt Syntaxbaum"),
                        tags.get("Grammatiken"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Abstrakte Maschinen")
                .addTags(tags.get("UE"), tags.get("Programmiersprachen"),
                        tags.get("??bersetzer"), tags.get("Interpreter"),
                        tags.get("Assembler"), tags.get("Programmieren"),
                        tags.get("Virtual Machine"), tags.get("Teamfaehigkeit"),
                        tags.get("Gruppenarbeit"), tags.get("C++"),
                        tags.get("Debug"), tags.get("Java"),
                        tags.get("Software"), tags.get("Diffizil"),
                        tags.get("C"), tags.get("#C"), tags.get("JavaScript"),
                        tags.get("Abstrakt Syntaxbaum"),
                        tags.get("Grammatiken"));

        coursesBachelorSoftwareAndInformationEngineering.get("VU ??bersetzerbau")
                .addTags(tags.get("VU"), tags.get("Theorie"),
                        tags.get("??bersetzer"), tags.get("Bootstrapping"),
                        tags.get("Assembler"), tags.get("Programmieren"),
                        tags.get("Programmiersprachen"),
                        tags.get("Abstrakt Syntaxbaum"), tags.get("Bison"),
                        tags.get("Yacc"), tags.get("Grammatiken"),
                        tags.get("Software"), tags.get("Diffizil"),
                        tags.get("C"), tags.get("Debug"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Echtzeitsysteme")
                .addTags(tags.get("VO"), tags.get("Theorie"),
                        tags.get("Echtzeitsysteme"), tags.get("Fehlertoleranz"),
                        tags.get("Synchronisation"), tags.get("Diffizil"));


        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Computernumerik")
                .addTags(tags.get("UE"), tags.get("Theorie"),
                        tags.get("Numerik"), tags.get("Mathe"),
                        tags.get("Rundungsfehler"),
                        tags.get("Gleichungssysteme"),
                        tags.get("numerische Differentiation"),
                        tags.get("numerische Integration"),
                        tags.get("Interpolation"), tags.get("Approximation"),
                        tags.get("numerische Differenzialgleichungen"),
                        tags.get("Diffizil"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Computernumerik")
                .addTags(tags.get("VU"), tags.get("Theorie"),
                        tags.get("Numerik"), tags.get("Mathe"),
                        tags.get("Rundungsfehler"),
                        tags.get("Gleichungssysteme"),
                        tags.get("numerische Differentiation"),
                        tags.get("numerische Integration"),
                        tags.get("Interpolation"), tags.get("Approximation"),
                        tags.get("numerische Differenzialgleichungen"),
                        tags.get("Diffizil"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Multivariate Statistik")
                .addTags(tags.get("VO"), tags.get("Theorie"),
                        tags.get("Statistik"),
                        tags.get("Hauptkomponentenanalyse"),
                        tags.get("Faktorenanalyse"),
                        tags.get("Diskriminanzanalyse"),
                        tags.get("Clusteranalyse"),
                        tags.get("Regressionsanalyse"), tags.get("Diffizil"),
                        tags.get("Mathe"), tags.get("Multivariate Methoden"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Multivariate Statistik")
                .addTags(tags.get("UE"), tags.get("Theorie"),
                        tags.get("Statistik"),
                        tags.get("Hauptkomponentenanalyse"),
                        tags.get("Faktorenanalyse"),
                        tags.get("Diskriminanzanalyse"),
                        tags.get("Clusteranalyse"),
                        tags.get("Regressionsanalyse"), tags.get("Diffizil"),
                        tags.get("Mathe"), tags.get("Multivariate Methoden"));

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Computerstatistik")
                .addTags(tags.get("VU"), tags.get("Theorie"),
                        tags.get("Statistik"), tags.get("Numerik"),
                        tags.get("Mathe"), tags.get("Regressionsanalyse"),
                        tags.get("Multivariate Methoden"), tags.get("Varianz"),
                        tags.get("Simulation"), tags.get("Diffizil"));
    }

    private void addDescriptionToBachelorSoftwareAndInformationEngineeringCoursesSemester1() {
        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Programmkonstruktion").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Fachliche und methodische Kenntnisse\n" +
                        "Verstehen systematischer, konstruktiver " +
                        "Vorgehensweisen beim Erstellen, Testen, Debuggen, " +
                        "Nachvollziehen und Modifizieren von Programmen\n" +
                        "Verstehen der wichtigsten Konzepte einer aktuellen " +
                        "alltagstauglichen Programmiersprache\n" +
                        "Verstehen einfacher Algorithmen und fundamentaler " +
                        "Datenstrukturen\n" +
                        "Kennen der h??ufigsten Fehlerquellen in der " +
                        "Programmierung\n" +
                        "Kognitive und praktische Fertigkeiten\n" +
                        "Anwenden fundamentaler Konzepte, Vorgehensweisen und" +
                        " Werkzeuge zum Erstellen, Testen, Debuggen, " +
                        "Nachvollziehen und Modifizieren von Programmen\n" +
                        "Anwenden abstrakter und systematischer Denkweisen in" +
                        " der Programmierung\n" +
                        "Dokumentation und Kommunikation von " +
                        "Programmeigenschaften\n" +
                        "Soziale Kompetenzen, Innovationskompetenz und " +
                        "Kreativit??t\n" +
                        "Freude am L??sen von Programmieraufgaben\n" +
                        "Neugierde an Themen im Zusammenhang mit der " +
                        "Programmierung\n" + "Inhalt der Lehrveranstaltung\n" +
                        "Fundamentale prozedurale Programmierkonzepte " +
                        "(einschlie??lich Rekursion und Ein- und Ausgabe)\n" +
                        "Fundamentale Entwurfskonzepte, " +
                        "Probleml??sungsstrategien, Algorithmen und " +
                        "Datenstrukturen (einschlie??lich abstrakter " +
                        "Datentypen)\n" +
                        "Fundamentale Entwicklungsmethoden (hinsichtlich " +
                        "Programmverstehen, Korrektheit, Debuggen, " +
                        "Dokumentation und Programmierstil) und " +
                        "Programmierwerkzeuge (einschlie??lich geeigneter " +
                        "Programmierumgebungen)\n" + " \n" +
                        "Weitere Informationen\n" +
                        "Vorbesprechung: 3. Oktober, 13:00 - 15:00, Audi.Max" +
                        ".\n" + "\n" +
                        "Das gesamte Modul Programmkonstruktion wird durch " +
                        "die gleichnamige, hier beschriebene " +
                        "Lehrveranstaltung (abgek??rzt PK) abgedeckt. PK " +
                        "bildet die erste Stufe der Programmierausbildung " +
                        "f??r alle Bachelorstudien der Informatik und " +
                        "Wirtschaftsinformatik an der TU Wien. Es wird " +
                        "dringend dazu geraten, PK im ersten Semester " +
                        "eines solchen Studiums zu absolvieren.\n" + "\n" +
                        "In ehemaligen Studienpl??nen waren statt PK die " +
                        "Lehrveranstaltungen Grundlagen der " +
                        "Programmkonstruktion (GPK) sowie Programmierpraxis" +
                        " (PP) vorgesehen. Studierende, die schon eine " +
                        "dieser beiden Lehrveranstaltungen positiv " +
                        "absolviert haben (oder aufgrund eines sehr " +
                        "alten Studienplans nicht ben??tigen) k??nnen " +
                        "nach wie vor die jeweils andere " +
                        "Lehrveranstaltung besuchen. Studierende, auf" +
                        " die diese Voraussetzungen nicht zutreffen," +
                        " k??nnen statt GPK und PP nur mehr PK " +
                        "besuchen.\n" + "\n" + "Ablauf\n" + "\n" +
                        "Die w??chentlich vier Vorlesungseinheiten im Audi.Max" +
                        ". haben jeweils einen Schwerpunkt in der Theorie " +
                        "oder in Anwendungsbeispielen:\n" + "\n" +
                        "Montag, 13:00-14:00 Uhr, Theorie \n" +
                        "Montag, 14:00-15:00 Uhr, Anwendung \n" +
                        "Donnerstag, 14:00-15:00 Uhr, Theorie \n" +
                        "Donnerstag, 15:00-16:00 Uhr, Anwendung  \n" + " \n" +
                        "\n" +
                        "Die Vorlesungen sind nicht alternativ, sondern bauen" +
                        " aufeinander auf und sollten daher alle besucht " +
                        "werden. Die erste Vorlesung am 3. Oktober 2016 " +
                        "bietet viel organisatorische Information (= " +
                        "Vorbesprechung). Beachten Sie die " +
                        "Vorlesungsunterlagen auf TUWEL.\n" + "\n" +
                        "Studierende nehmen wiederholt an zweist??ndigen " +
                        "geleiteten ??bungen in Gruppen zu ca. 25 Personen " +
                        "teil. Die Anmeldung zu einer dieser Gruppen ist " +
                        "erst nach der Anmeldung zur LV m??glich (Die " +
                        "Anmeldefrist f??r die Gruppenanmeldung wird " +
                        "separat angek??ndigt). Diese ??bungen mit " +
                        "Anwesenheitspflicht bilden einen wesentlichen " +
                        "Bestandteil der Beurteilung. Ausgew??hlte " +
                        "Teilnehmer(innen) pr??sentieren ihre L??sungen" +
                        " der Aufgabenbl??tter und beantworten Fragen" +
                        ". Einen Schwerpunkt bilden " +
                        "Programmieraufgaben, die vor Ort alleine " +
                        "am Computer zu l??sen sind. Alle ??bungen " +
                        "finden in den R??umen des Informatik " +
                        "Labors (InfLab) zu unterschiedlichen " +
                        "Zeiten statt, abh??ngig von den " +
                        "Anmeldungen zu Gruppen.\n" + "\n" +
                        "Drei regul??re Tests bilden den Schwerpunkt der " +
                        "Beurteilung. Sie finden am 16. November, 7.Dezember" +
                        " und 25. J??nner in mehreren Labors und zu mehreren" +
                        " Terminen an Computern (mit ??hnlicher Software " +
                        "wie bei den ??bungen) statt. Der Test im November" +
                        " dauert 45 Minuten, die beiden Tests im " +
                        "Dezember und J??nner je 90 Minuten.\n" + "\n" +
                        "Am 5.Oktober findet ein freiwilliger Einstufungstest" +
                        " statt. Er dauert 45 Minuten. Die Teilnahme daran " +
                        "ist freiwillig, wird aber allen Studierenden " +
                        "empfohlen, die schon Programmierkenntnisse haben." +
                        " Studierende, die beim Einstufungstest " +
                        "mindestens 70% der Punkte erreichen, " +
                        "absolvieren die geleiteten ??bungen in " +
                        "speziellen ??bungsgruppen f??r Fortgeschrittene" +
                        ". Es ist nicht m??glich an normalen " +
                        "??bungsgruppen teilzunehmen, wenn beim " +
                        "Einstufungstest mindestens 70% erreicht " +
                        "wurden. Das Ergebnis des Einstufungstests" +
                        " ist f??r den ersten regul??ren Test " +
                        "anrechenbar, wenn beim Einstufungstest " +
                        "mindestens 50% der Punkte erreicht " +
                        "wurden. Wenn Sie mindestens 50% der " +
                        "Punkte beim Einstufungstest erreicht" +
                        " haben und trotzdem beim ersten " +
                        "regul??ren Test mitmachen, " +
                        "verfallen die Punkte vom " + "Einstufungstest!\n" +
                        "\n" +
                        "Im Februar findet ein Nachtragstest statt. Die " +
                        "Teilnahme ist nur m??glich, wenn genau einer der " +
                        "drei regul??ren Tests negativ oder gar nicht " +
                        "beurteilt wurde. Die Beurteilung des " +
                        "Nachtragstests ersetzt jene dieses regul??ren " +
                        "Tests. Inhaltlich deckt der Nachtragstest das " +
                        "gesamte Stoffgebiet ab (das genaue Datum wird " +
                        "rechtzeitig ??ber TUWEL angek??ndigt).\n" + "\n" +
                        "Eine wesentliche Komponente ist das ??ben, also das " +
                        "L??sen von Aufgaben nach eigenem Gutd??nken zu " +
                        "??bungszwecken. Zur Unterst??tzung stehen im InfLab " +
                        "Tutor(inn)en bereit, die bei Fragen und Problemen" +
                        " weiterhelfen, das sogenannte Programmier-Cafe. " +
                        "Das Programmier-Cafe findet immer von " +
                        "10:00-11:00 an einem Montag statt.\n" + "\n" +
                        "Teilnahmevoraussetzungen\n" + "\n" +
                        "F??r die Teilnahme an den Komponenten der " +
                        "Lehrveranstaltung gelten folgende " +
                        "Voraussetzungen:\n" + "\n" +
                        "Rechtzeitige Anmeldung im TISS.\n" + "\n" +
                        "F??r jede Teilnahme an einem Test ist eine " +
                        "Terminreservierung in TUWEL n??tig.\n" +
                        "F??r die Teilnahme an den geleiteten ??bungen ist eine" +
                        " Anmeldung zu einer ??bungsgruppe in TUWEL n??tig.\n" +
                        "\n" + "??bungsgruppen\n" + "\n" +
                        "Die Anmeldung zu den ??bungsgruppen erfolgt im TUWEL" +
                        ".\n" + "\n" +
                        "Die ??bungsgruppen beginnen Mitte Oktober. Normale " +
                        "??bungsgruppen werden von ??bungsgruppen f??r " +
                        "Fortgeschrittene unterschieden. Studierende, die " +
                        "beim Einstufungstest mindestens 70% der Punkte " +
                        "erreicht haben, kommen in eine Gruppe f??r " +
                        "Fortgeschrittene. Alle anderen Studierenden " +
                        "(nicht am Einstufungstest teilgenommen oder " +
                        "weniger Punkte erreicht) kommen in eine " +
                        "normale ??bungsgruppe. Diese beiden Arten von" +
                        " ??bungsgruppen unterscheiden sich " +
                        "folgenderma??en voneinander:\n" + "\n" +
                        "Normale ??bungsgruppen haben insgesamt 9 " +
                        "??bungstermine und beginnen zu Semesterbeginn.\n" +
                        "\n" +
                        "??bungsgruppen f??r Fortgeschrittene haben insgesamt 5" +
                        " ??bungstermine. Der erste ??bungstermin ist erst " +
                        "nach dem ersten regul??ren Test.\n" + "\n" +
                        "??bungsaufgaben f??r Fortgeschrittene k??nnen sich von " +
                        "denen normaler ??bungsgruppen unterscheiden.\n" + "\n" +
                        "Einige der unten genannten ??bungstermine werden (je " +
                        "nach Teilnehmerzahl) m??glicherweise nicht angeboten" +
                        ".\n" + "\n" + "??bungsumgebung\n" + "\n" +
                        "Zu Beginn der geleiteten ??bungen erhalten Teilnehmer" +
                        "(innen) Zugangsdaten zu einem Account auf dem " +
                        "??bungsrechner. Dort ist die zum L??sen der Aufgaben" +
                        " n??tige Software installiert. Auch die Betreuer " +
                        "haben Zugang zu den Daten in der ??bungsumgebung." +
                        " In der ersten geleiteten ??bung erfolgt eine " +
                        "kurze Einweisung in die Verwendung der " +
                        "??bungsumgebung. W??hrend der geleiteten " +
                        "??bungen bleibt man in der Regel stets in " +
                        "diese ??bungsumgebung eingeloggt. " +
                        "Aufgabenbl??tter k??nnen ebenfalls in dieser" +
                        " ??bungsumgebung gel??st werden.\n" + "\n" +
                        "Kommunikation\n" + "\n" +
                        "Ein wichtiger Teil der Kommunikation erfolgt per " +
                        "Mail. Mail an Studierende wird an Adressen der Form" +
                        " eXXXXXXX@student.tuwien.ac.at verschickt, wobei " +
                        "XXXXXXX die Matrikelnummer ist. Teilnehmer(innen)" +
                        " werden gebeten, Mail an diese Adressen " +
                        "regelm????ig zu lesen, da manche Mails eine " +
                        "kurzfristige Aktion erfordern.\n" + "\n" +
                        "Durch die erste Anmeldung zu einer ??bungsgruppe " +
                        "erh??lt jede(r) Teilnehmer(in) die Kontaktadresse " +
                        "einer Tutorin oder eines Tutors, der oder die f??r " +
                        "die pers??nliche Betreuung zust??ndig ist. Dies(e) " +
                        "Tutor(in) dient als wichtigste Anlaufstelle bei " +
                        "Problemen und zur Kl??rung offener Fragen aller " +
                        "Art.\n" + "\n" +
                        "Fragen k??nnen auch an die Adresse pk@complang.tuwien" +
                        ".ac.at gerichtet werden.\n" + "\n" +
                        "Vor und nach Vorlesungen und ??bungen sowie in Pausen" +
                        " k??nnen Teilnehmer(innen) die Lehrenden gerne " +
                        "direkt ansprechen und Fragen stellen. Daneben " +
                        "bietet jeder Lehrende eine w??chentliche " +
                        "Sprechstunde an, zu der Sie auch ohne " +
                        "Voranmeldung kommen k??nnen. F??r genaue Termine " +
                        "klicken Sie bitte auf den Namen des jeweiligen" +
                        " Vortragenden.\n" + "\n" +
                        "DIE ANMELDUNG ZU DEN ??BUNGSGRUPPEN ERFOLGT ??BER " +
                        "TUWEL !\n" + "\n" + " \n" + "ECTS-Breakdown:\n" +
                        "3.0 ECTS (75 Stunden) f??r Teilnahme an Vorlesungen " +
                        "inklusive Vor- und Nachbereitung und L??sen der " +
                        "dabei gestellten Aufgaben\n" + "\n" +
                        "3.6 ECTS (90 Stunden) f??r L??sen der ??bungsaufgaben " +
                        "und Teilnahme an geleiteten ??bungen\n" + "\n" +
                        "2.2 ECTS (55 Stunden) f??r Testvorbereitung und " +
                        "Testteilnahme\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Technische Grundlagen der Informatik").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Verstehen und Beherrschen der technischen Grundlagen" +
                        " der Informatik.\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Die Lehrveranstaltung vermittelt:\n" + "\n" +
                        "Kenntnisse zur Darstellung von Zahlen in Computern\n" +
                        "Grundlagen der Kodierungstheorie\n" +
                        "Grundlagen der Booleschen Algebra und " +
                        "Minimierungsverfahren\n" +
                        "Grundlagen digitaler Schaltungstechnik\n" +
                        "Gatterschaltungen (Addierer, Codierer, Multiplexer, " +
                        "...)\n" +
                        "Schaltnetze mit programmierbaren Bausteinen (ROM, " +
                        "PROM/EPROM, PLA, PAL, ...)\n" +
                        "Speicherglieder (RS, JK, D) und Speicher (statisch, " +
                        "dynamisch)\n" +
                        "Synthese und Analyse von Schaltwerken\n" +
                        "Prozessorarchitekturen\n" +
                        "Adressierungsarten, Befehlssatz, RISC/CISC und " +
                        "Pipelining\n" + "Speicherverwaltung\n" +
                        "Ein-/Ausgabe und Peripherieger??te\n" +
                        "Systemsoftware (Kurz??berblick)\n" +
                        "Die Lernaktivit??ten umfassen Vorlesungseinheiten, " +
                        "??bungen in Kleingruppen (\"Tafel??bungen\"), sowie " +
                        "drei schriftliche Tests.\n" + "\n" +
                        "Weitere Informationen\n" +
                        "Vorbesprechung: Di., 4.10.2016, 10:00 Uhr, H??rsaal " +
                        "GM1 (Audi Max)\n" + "\n" +
                        "Die Vorlesungen finden im H??rsaal GM1 (Audi Max) " +
                        "statt; jeweils am Dienstag von 10:00-11:00, am " +
                        "Donnerstag von 10:00-12:00 und am Freitag von " +
                        "10:00-12:00. Erste regul??re Vorlesung am Do., 6" +
                        ".10.2016.\n" + "\n" +
                        "Aufwandsabsch??tzung (ECTS Breakdown): 150 Stunden = " +
                        "6 ECTS\n" + "\n" +
                        "Anwesenheit Vorlesung / Repetitorien (50 Stunden)\n" +
                        "Anwesenheit Tests (6 Stunden)\n" +
                        "Anwesenheit ??bung (12 Stunden)\n" +
                        "Vor-/Nachbereitung ??bung (64 Stunden)\n" +
                        "Nachbereitung Vorlesung und Testvorbereitung (18 " +
                        "Stunden)\n" +
                        "Alle Informationen, Lernunterlagen und die Angaben " +
                        "f??r die ??bungsbeispiele finden Sie im TUWEL-Kurs " +
                        "der Lehrveranstaltung.\n" + "\n" +
                        "Weitere Fragen richten Sie bitte an: tgi@auto.tuwien" +
                        ".ac.at\n" + "\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Erwerb grundlegender Kenntnisse und F??higkeiten aus " +
                        "Mathematik.\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Grundlagen, Zahlentheorie, Aussagenlogik, " +
                        "Mengenlehre, Kombinatorik, Differenzengleichungen, " +
                        "Graphentheorie, Algebraische Strukturen, Lineare " +
                        "Algebra, Grundlagen Algebraische " +
                        "Codierungstheorie.\n" + "\n" +
                        "Weitere Informationen\n" +
                        "Die Vorlesung beginnt am Dienstag, den 4.10. um 9h " +
                        "im AudiMax.\n" + "\n" + "Termine:\n" + "\n" +
                        "Mo-Fr: 9h-10h im AudiMax\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Beherrschung des Vorlesungsstoffs\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "??bungsaufgaben zum Stoff der gleichnamigen " +
                        "Vorlesung\n" + "\n" + "Weitere Informationen\n" +
                        "Achtung: Aktuelle Beginnzeiten f??r alle Gruppen und " +
                        "weitere Informationen auf der LVA-Homepage!\n" + "\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Formale Modellierung").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Ziel der Lehrveranstaltung ist es, den Studierenden " +
                        "die wichtigsten formalen Spezifikationsmethoden zu " +
                        "vermitteln und sie zu bef??higen, mit diesen " +
                        "Methoden mehrdeutige umgangssprachlich " +
                        "beschriebene Sachverhalte zu modellieren. " +
                        "Weiters lernen sie die formal-mathematischen " +
                        "Beschreibungen dieser Methoden zu lesen und zu" +
                        " verstehen, sodass sie selbst??ndig ihr Wissen" +
                        " an Hand von Fachliteratur vertiefen k??nnen" + ".\n" +
                        "\n" + "Inhalt der Lehrveranstaltung\n" +
                        "Aussagenlogik, endliche Automaten, regul??re " +
                        "Ausdr??cke, formale Grammatiken, Petri-Netze, " +
                        "Pr??dikatenlogik als Spezifikationssprache.\n" + "\n" +
                        "Die Vermittlung dieser Inhalte erfolgt durch einen " +
                        "Vorlesungsteil und durch selbst??ndig zu l??sende " +
                        "??bungsaufgaben. Die Beurteilung setzt sich aus der" +
                        " Bewertung dieser Aufgaben, einer schriftlichen " +
                        "Leistungs??berpr??fung und zwei Abgabegespr??chen " +
                        "zusammen.\n" + "\n" + "Weitere Informationen\n" +
                        "Aufwandsabsch??tzung\n\n" +
                        "17.0 h Vorlesungsbesuch (5 Tage x 3h, 1 Tag x 2h)\n" +
                        "10.5 h 1.??bungsblatt (15 Beispiele x 0.7h)\n" +
                        " 0.5 h 1.Tutorengespr??ch\n" +
                        " 8.0 h Vorbereitung auf 1.Abgabegespr??ch(1 Tag x 8h)" +
                        "\n" + " 1.0 h 1.Abgabegespr??ch\n" +
                        "10.5 h 2.??bungsblatt (15 Beispiele x 0.7h)\n" +
                        " 0.5 h 2.Tutorengespr??ch\n" +
                        " 8.0 h Vorbereitung auf 2.Abgabegespr??ch (1 Tag x " +
                        "8h)\n" + " 1.0 h 2.Abgabegespr??ch\n" +
                        "16.0 h Vorbereitung auf Abschlusstest (2 Tage x 8h)" +
                        "\n" + " 1.5 h Abschlusstest\n" +
                        "-------------------------------------------------\n" +
                        "74.5 h = ca. 3 Ects");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Datenmodellierung").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Einf??hrung in die Datenmodellierung\n" + "\n" +
                        "Emails bitte ausschlie??lich an dm@dbai.tuwien.ac" +
                        ".at\n" + "\n" + "Achtung:\n" +
                        "F??r alle Studienanf??ngerInnen der Bachelorstudien " +
                        "der Fakult??t f??r Informatik der TU Wien ab dem " +
                        "Sommersemester 2011 gilt: \n" +
                        "Voraussetzung f??r eine positive Absolvierung dieser " +
                        "LVA ist, dass bis zum Anmeldetermin das " +
                        "Studieneingangsgespr??ch (STEG) der Fakult??t f??r " +
                        "Informatik absolviert wurde. \n" +
                        "Details dazu siehe STEG.\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Semantische Datenmodellierung (EER)\n" +
                        "??berf??hrung in das Relationenmodel\n" +
                        "Datenbanksprachen (Relationale Algebra, Relationaler" +
                        " Tupel/Dom??nenkalk??l, SQL)\n" +
                        "Relationale Entwurfstheorie (Funktionale " +
                        "Abh??ngigkeiten, Normalformen)\n" +
                        "Datenintegrit??t\n" + "Weitere Informationen\n" +
                        "ECTS Breakdown:\n" +
                        "-----------------------------\n" +
                        "18h Vorlesungseinheiten\n" + "20h ??bungsbl??tter\n" +
                        "33h Vorbereitungszeit f??r Pr??fungen\n" +
                        "2h Abgabegespr??che (bzw. Test) zu den " +
                        "??bungsbl??ttern\n" + "1h SQL Test\n" +
                        "1h Abschlusspr??fung\n" +
                        "-----------------------------\n" + "75h (3 ECTS)\n" +
                        "-----------------------------");
    }

    private void addDescriptionToBachelorSoftwareAndInformationEngineeringCoursesSemester2() {
        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Analysis f??r Informatik und Wirtschaftsinformatik")
                .setDescription("Ziele der Lehrveranstaltung\n" +
                        "Erwerb grundlegender Kenntnisse und F??higkeiten aus " +
                        "Mathematik.\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Folgen, Reihen und Funktionen, Elementare " +
                        "Funktionen, Grenzwerte und Nullstellen von " +
                        "Funktionen, Stetigkeit, Differentialrechnung in " +
                        "einer Variablen, Integralrechnung in einer " +
                        "Variablen, Grundlagen Differential- und " +
                        "Integralrechnung in mehreren Variablen, " +
                        "Elementare Differentialgleichungen.\n" + "\n" +
                        "Weitere Informationen\n" +
                        "Weitere Informationen siehe Homepage der " +
                        "Lehrveranstaltung.\n" + "\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Analysis f??r Informatik und Wirtschaftsinformatik")
                .setDescription("Ziele der Lehrveranstaltung\n" +
                        "Beherrschung des Vorlesungsstoffs.\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "??bungsaufgaben zum Stoff der gleichnamigen Vorlesung" +
                        ".\n" + "\n" + "Weitere Informationen\n" +
                        "Erste ??bungsstunden: 15.03. (f??r UE am Dienstag), 16" +
                        ".03. (f??r UE am Mittwoch), 10.03. (f??r UE am " +
                        "Donnerstag)\n" + "\n");
    }

    private void addDescriptionToBachelorSoftwareAndInformationEngineeringCoursesSemester3() {
        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Objektorientierte Programmiertechniken")
                .setDescription("Ziele der Lehrveranstaltung\n" +
                        "Fachliche und methodische Kenntnisse\n" +
                        "Kenntnisse objektorientierter Programmiersprachen " +
                        "und der produktiven Programmierung aus der " +
                        "Sichtweise aktueller Methoden der " +
                        "Softwareentwicklung\n" +
                        "Kenntnisse zur effizienten Entwicklung " +
                        "objektorientierter Programme auf Basis eines guten " +
                        "Verst??ndnisses der Wiederverwendung von " +
                        "Programmteilen\n" +
                        "Kognitive und praktische Fertigkeiten\n" +
                        "Durch die praktische Auseinandersetzung mit einer " +
                        "objektorientierten Programmiersprache (Java), " +
                        "Programmiermethoden und Programmierwerkzeugen " +
                        "werden folgende Fertigkeiten vermittelt bzw. " +
                        "ausgebaut:\n" + "\n" +
                        "Modellbildung und Abstraktion in der " +
                        "objektorientierten Programmierung\n" +
                        "Einsatz bew??hrter Methoden zur Modellbildung, " +
                        "L??sungsfindung und Evaluation im Bereich der " +
                        "objektorientierten Programmierung\n" +
                        "Umgang mit unspezifizierten und unvollst??ndig " +
                        "spezifizierten Problemsituationen\n" +
                        "Kritische Bewertung und Reflexion von L??sungen\n" +
                        "Soziale Kompetenzen, Innovationskompetenz und " +
                        "Kreativit??t\n" +
                        "Der ??bungsbetrieb f??rdert die Selbstorganisation und" +
                        " Eigenverantwortlichkeit sowie das Finden kreativer" +
                        " Probleml??sungen und eigenst??ndiger " +
                        "L??sungsstrategien.\n" +
                        "Die Arbeit in Gruppen st??rkt die Teamf??higkeit.\n" +
                        "Die Vermittlung von Hintergrundwissen f??rdert die " +
                        "Neugierde an Themen im Bereich der " +
                        "objektorientierten Programmiersprachen und des " +
                        "objektorientierten Paradigmas.\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "??berblick ??ber das objektorientierte " +
                        "Programmierparadigma und entsprechende " +
                        "Programmiersprachen\n" +
                        "Fortgeschrittenere objektorientierte Sprachkonzepte " +
                        "in Java, gute Kenntnisse einfacher Sprachkonzepte " +
                        "werden vorausgesetzt\n" +
                        "Sprachkonzepte f??r Generizit??t, Nebenl??ufigkeit und " +
                        "Modularisierung in Java\n" +
                        "Produktive Verwendung dieser Konzepte in einer dem " +
                        "objektorientierten Paradigma entsprechenden Weise\n" +
                        "Genaue Betrachtung der Ersetzbarkeit und anderer f??r" +
                        " die Wiederverwendung in objektorientierten " +
                        "Programmen bedeutender Prinzipien\n" +
                        "Zusammenh??nge zwischen verschiedenen " +
                        "objektorientierten Konzepten und Prinzipien\n" +
                        "Ausgew??hlte Entwurfsmuster und objektorientierte " +
                        "Programmiertechniken\n" + "Weitere Informationen\n" +
                        "Webseite\n" + "Siehe http://www.complang.tuwien.ac" +
                        ".at/franz/objektorientiert.html f??r weitere " +
                        "Informationen.\n" + "\n" +
                        "Didaktische Vorgehensweise\n" +
                        "W??chentliche Vorlesungen vermitteln den eher " +
                        "theoretischen Stoff und geben " +
                        "Hintergrundinformationen. Ebenso beinahe " +
                        "w??chentlich sind Programmieraufgaben, die auf den" +
                        " Vorlesungsstoff Bezug nehmen, in Kleingruppen " +
                        "zu l??sen. Zur Erreichung der Ziele wird gro??er " +
                        "Wert auf das selbst??ndige Finden von " +
                        "L??sungswegen und individuelle L??sungen " +
                        "gelegt; der eigene Weg zur L??sung ist " +
                        "wichtiger als die L??sung selbst.\n" + "\n" +
                        "Aufwand\n" +
                        "F??r die L??sung der Programmieraufgaben und das " +
                        "entsprechende Abgabegespr??ch (??bungsteil) wird eine" +
                        " Aufwand im Umfang von 2 ECTS (50 Stunden) " +
                        "angenommen, f??r die Teilnahme an Vorlesungen, die" +
                        " Erarbeitung der Vorlesungsinhalte und die " +
                        "Pr??fung (Theorieteil) ist ein Aufwand von 1 " +
                        "ECTS (25 Stunden) vorgesehen.\n" + "\n" +
                        "Hinweise zur Anmeldung\n" +
                        "Sie m??ssen sich sowohl zur Lehrveranstaltung als " +
                        "auch zu einer der zahlreichen ??bungsgruppen mit " +
                        "Namen oopXn anmelden, wobei X ein Buchstabe im " +
                        "Namen der betreuenden Tutorin oder des Tutors  " +
                        "und n eine fortlaufende Nummer ist. Alle anderen" +
                        " hier sichtbaren Gruppen werden erst im J??nner " +
                        "zur Anmeldung zum Abgabegespr??ch dienen. Die " +
                        "genauen Termine f??r Abgabegespr??che sowie " +
                        "Pr??fungen werden sich bis zum J??nner noch " +
                        "??ndern, aber ungef??hr in den Zeitr??umen " +
                        "liegen, die aus den Titeln erkennbar sind" + ".\n" +
                        "\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Funktionale Programmierung").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Die Studierenden erhalten ein umfassendes " +
                        "theoretisches und\n" +
                        "praktisches Verst??ndnis der Grundlagen, Prinzipien " +
                        "und Konzepte der\n" +
                        "funktionalen Programmierung und lernen, diese auf " +
                        "ihre Eignung zur\n" +
                        "L??sung programmiertechnischer Aufgaben zu bewerten " +
                        "und zielorientiert\n" +
                        "und angemessen im funktionalen Programmierstil " +
                        "einzusetzen und\n" + "anzuwenden.\n" + "\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Die Vorlesung f??hrt in Theorie und Praxis in die " +
                        "Prinzipien und\n" +
                        "Konzepte des funktionalen Programmierstils ein und " +
                        "spannt dabei den\n" +
                        "Bogen von den Grundlagen bis zur Anwendung. In der\n" +
                        "vorlesungsbegleitenden Plenums??bung Haskell Live " +
                        "wird am Beispiel\n" +
                        "konkreter Aufgabenstellungen praktisch vorgef??hrt " +
                        "und diskutiert, wie\n" +
                        "sich diese zur L??sung einsetzen lassen. Dabei k??nnen" +
                        " insbesondere auch\n" +
                        "eigene L??sungsvorschl??ge vorgestellt und zur " +
                        "Diskussion gestellt\n" +
                        "werden. Im ??bungsteil wird der funktionale " +
                        "Programmierstil anhand von\n" +
                        "Beispielen eigenst??ndig angewendet und einge??bt und " +
                        "so ein vertieftes\n" +
                        "theoretisches und praktisches Verst??ndnis f??r die " +
                        "Konzepte und\n" +
                        "Prinzipien funktionaler Programmierung erarbeitet " +
                        "und erworben. Als\n" +
                        "Demonstrations- und Implementierungssprache wird in " +
                        "Vorlesung und\n" +
                        "??bung die funktionale state-of-the-art " +
                        "Programmiersprache Haskell\n" + "verwendet.\n" + "\n" +
                        "Teil I: Einf??hrung\n" + "\n" + "Motivation\n" +
                        "Grundlagen von Haskell\n" +
                        "Rekursion und Rekursionstypen\n" +
                        "Teil II: Applikative Programmierung       \n" + "\n" +
                        "Auswertung von Ausdr??cken\n" +
                        "Programmentwicklung und Programmverstehen\n" +
                        "Datentypdeklarationen\n" +
                        "Teil III: Funktionale Programmierung\n" + "\n" +
                        "Funktionen h??herer Ordnung\n" +
                        "Parametrische und ad hoc Polymorphie\n" +
                        "Teil IV: Fundierung funktionaler Programmierung\n" +
                        "\n" + "Auswertungsstrategien\n" +
                        "Formale Rechenmodelle\n" + "Lambda-Kalk??l\n" +
                        "Teil V: Erg??nzungen und weiterf??hrende Konzepte\n" +
                        "\n" + "Muster\n" + "Module\n" + "Ein- und Ausgabe\n" +
                        "Programmierprinzipien mit Str??men und Funktionalen\n" +
                        "Monomorphe und polymorphe Typinferenz\n" +
                        "Fehlerbehandlung\n" +
                        "Teil VI: Res??mee und Perspektiven\n" + "\n" +
                        "Zusammenfassung und Ausblick\n" + " \n" + "\n" +
                        "Weitere Informationen\n" +
                        "Aufteilung der ECTS-Punkte:\n" + "\n" +
                        "Der Lehrveranstaltung sind 3.0 ECTS-Punkte " +
                        "zugeordnet. Diese\n" +
                        "entsprechen einem durchschnittlichen Lernaufwand von" +
                        " 75\n" +
                        "Stunden. Dieser durchschnittliche Lernaufwand " +
                        "verteilt sich in\n" +
                        "folgender Weise auf die einzelnen Teile der " +
                        "Lehrveranstaltung:\n" + "\n" +
                        "Vorlesungsbesuch und -vor- und -nachbereitung: 30 " +
                        "Std.\n" +
                        "\"Haskell Live\"-Plenums??bungsbesuch und \"Haskell " +
                        "Private\"-Teilnahme, Vor- und -nachbereitung: 10 " +
                        "Std.\n" + "??bungsaufgaben: 30 Std.\n" +
                        "Pr??fungsvorbereitung und Klausur: 5 Std.\n" +
                        "Die Lehrveranstaltung beginnt am Di, 04.10.2016, " +
                        "08:15-09:45 Uhr, mit Vorbesprechung und erster " +
                        "Vorlesung.\n" + "\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Betriebssysteme").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Erwerb von fachlichen und methodischen Kenntnissen " +
                        "und Fertigkeiten:\n" + "\n" +
                        "Kenntnisse ??ber die Rolle und Aufgaben von " +
                        "Betriebssystemen\n" +
                        "Verstehen von Designentscheidungen f??r das " +
                        "Management von Systemressourcen\n" +
                        "Verst??ndnis der Mechanismen zur Koordination und " +
                        "Synchronisation paralleler Prozesse\n" +
                        "Grundkenntnisse der Netzwerkkommunikation und des " +
                        "Zugriffsschutzes\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Grundkonzepte Betriebssysteme\n" +
                        "Definition und Entwicklungsgeschichte\n" +
                        "zentrale Abstraktionen und Mechanismen\n" +
                        "Betriebssystemarchitekturen\n" +
                        "Prozesse, Threads und Scheduling\n" +
                        "Prozesse, Prozesszust??nde\n" +
                        "Datenstrukturen und Mechanismen zur " +
                        "Prozessverwaltung\n" + "Threads, Multithreading\n" +
                        "Scheduling und Dispatching (Ziele und Verfahren)\n" +
                        "Prozesssynchronisation und Deadlock\n" +
                        "Mutual Exclusion und Bedingungsynchronisation\n" +
                        "Synchronisationsmechanismen und Basiskonstrukte " +
                        "(Dekker Algorithmus, Test and Set, Spinlocks), " +
                        "Semaphore, Sequencer und Eventcounts, Monitor, " +
                        "Nachrichten und Synchronisation\n" +
                        "Synchronisationsaufgaben: Producer-Consumer, " +
                        "Reader-Writer, Dining Philosophers\n" +
                        "Deadlock: Voraussetzungen, Erkennung, Vermeidung\n" +
                        "Speicherverwaltung\n" +
                        "Speicheraufteilung, Relocation\n" +
                        "Segmentierung und Paging\n" +
                        "Virtual Memory Management (Prinzipien, Adressierung " +
                        "und Hardwareunterst??tzung, Seitenaustausch, " +
                        "Protection und Sharing)\n" +
                        "Ein-/Ausgabe und Disk Management\n" +
                        "Devices und deren Characteristika\n" +
                        "Ablauf von I/O\n" +
                        "Operationen, Treiber, Pufferung\n" +
                        "Festplatten: Zugriffe und Organisation\n" +
                        "Filesysteme: Operationen, Stuktur und Organisation\n" +
                        "Networking\n" +
                        "Einf??hrung in Netzwerke und Protokolle\n" +
                        "Betriebssystem und Netzwerkkommunikation\n" +
                        "Security und Protection\n" +
                        "Bedrohungsszenarien und Sicherheitsma??nahmen\n" +
                        "Sicheres Design\n" +
                        "Zugriffsschutz, Authentifizierung, Rechtesysteme\n" +
                        "Verschl??sselung (Einf??hrung)\n" +
                        "Didaktisches Vorgehen: Die genannten Inhalte werden " +
                        "in der Vorlesung pr??sentiert und mit Beispielen " +
                        "illustriert. Ausgew??hlte Problemstellungen werden " +
                        "in der parallel abgehaltenen ??bung in der " +
                        "Systemprogrammiersprache C unter UNIX (Linux) " +
                        "programmiert.\n" + "\n" + "Weitere Informationen\n" +
                        "ECTS Breakdown: 2 ECTS = 50 Stunden; 24 Std. Besuch " +
                        "der Vorlesung, 24 Std. Vorbereitung, Nachbereitung," +
                        " Pr??fungsvorbereitung, 2 Std. Pr??fung");

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Betriebssysteme").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Arbeiten mit Betriebssystemen und Programmierung " +
                        "unter Verwendung von Betriebssystemservices\n" +
                        "Programmieren in einer Systemprogrammiersprache (C)" +
                        "\n" +
                        "Programmierung paralleler Prozesse unter Verwendung " +
                        "gemeinsamer Ressourcen und Nutzung der " +
                        "Kommunikations- und Synchronisationsmechanismen " +
                        "eines Betriebssystems\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Programmierung in der Systemprogrammiersprache C, " +
                        "Programmierkonventionen und -richtlinien, " +
                        "Betriebssystemprogrammierung und " +
                        "Programmierumgebungen (GNU/Linux), " +
                        "Synchronisation paralleler Prozesse " +
                        "(Wechselseitiger Ausschluss und " +
                        "Bedingungssynchronisation mittels Semaphoren)," +
                        " Signale und Signalbehandlung, " +
                        "Interprozesskommunikation (mittels Shared " +
                        "Memory, Pipes, Sockets).\n" + "\n" +
                        "Didaktisches Vorgehen: Ausgew??hlte Problemstellungen" +
                        " aus der Betriebssystemprogrammierung werden in der" +
                        " Systemprogrammiersprache C unter UNIX (Linux) " +
                        "programmiert. Einf??hrungswissen zu den " +
                        "Aufgabestellungen und zur Systemprogrammierung " +
                        "wird in begleitenden Vortragsbl??cken angeboten" +
                        ".\n" + "\n" + "Weitere Informationen\n" +
                        "ECTS Breakdown: 4 ECTS = 100 Stunden; 12 Std. " +
                        "Einf??hrungsbl??cke, 84 Std. Programmierbeispiele, 4 " +
                        "Std. Tests\n" + "\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Introduction to Security").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Die Lehrveranstaltung vermittelt eine grundlegende " +
                        "Einf??hrung in unterschiedliche Aspekte der " +
                        "IT-Sicherheit. Dabei wird sowohl auf typische " +
                        "Sicherheitsprobleme und Angriffe als auch auf " +
                        "Sicherheitsmassnahmen zu deren Beseitigung " +
                        "eingegangen. Diese Lehrveranstaltung vermittelt" +
                        " Wissen, das f??r alle Studierenden der " +
                        "unterschiedlichen Informatik-Studien relevant" +
                        " ist und f??r Interessierte die Basis f??r " +
                        "weitergehende Lehrveranstaltungen im " +
                        "IT-Security Bereich bildet. Der Einsatz in" +
                        " der Praxis wird dabei u.a. durch " +
                        "Gast-Vorlesungen von externen " +
                        "Vortragenden aus der Wirtschaft " +
                        "dargestellt. Im ??bungsteil, der v.a. " +
                        "in Gruppenarbeiten erfolgt, werden " +
                        "die Inhalte der Vorlesung erweitert " +
                        "und vertieft.\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Die Vorlesung besch??ftigt sich u.a. mit " +
                        "grundlegenden Aspekten der Themen\n" + "\n" +
                        "Einf??hrung in die IT-Sicherheit/ Grundlagen der " +
                        "IT-Sicherheit \n" +
                        "Vorgehensweise und Sichtweise potenzieller " +
                        "AngreiferInnen \n" +
                        "Methoden von AngreiferInnen zur " +
                        "Informationsbeschaffung \n" + "Risikoanalyse \n" +
                        "Zugriffskontrolle (Authentifizierung, Autorisierung," +
                        "...) \n" + "Betriebsystemsicherheit \n" +
                        "Netzwerksicherheit \n" +
                        "Grundlagen der Kryptographie \n" +
                        "Sicherheit in der Software-Entwicklung \n" +
                        "Sicherheit von Anwendungsprogrammen \n" +
                        "Sicherheit von Web-Anwendungen \n" +
                        "Organisatorische Sicherheit \n" +
                        "Auffinden von Schwachstellen \n" +
                        "Sicherheitstests\n" + "Didaktische Vorgehensweise:\n" +
                        "\n" + "In der Vorlesung werden die erforderlichen " +
                        "Fachgrundlagen vermittelt, es erfolgt eine " +
                        "Anleitung zur weiterf??hrenden Recherche bei " +
                        "Vertiefungsbedarf und die Vorstellung von realen," +
                        " oft grossen Fallbeispielen. In den praktischen " +
                        "??bungsaufgaben werden neben den theoretischen " +
                        "Inhalten der Vorlesung in einer ??bungsumgebung" +
                        " unterschiedliche Aspekte der IT-Sicherheit " +
                        "in 3 Einzel- bzw. Gruppenaufgaben weiter " +
                        "vertieft bzw. selbst??ndig erweitert.\n" + "\n" +
                        "Weitere Informationen\n" +
                        "Aufwandsschaetzung (ECTS-Breakdown):\n" + "\n" +
                        "Vorbesprechung: 1 h\n" + "Vorlesungen: 13,5 h\n" +
                        "Self Study (Uebungen, Testvorbereitung): 59\n" +
                        "Schriftlicher Test: 1.5\n" +
                        "Summe: 75 Stunden (3 ECTS)");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Daten- und Informatikrecht").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Die Lehrveranstaltung zielt darauf ab, einen Zugang " +
                        "zu den f??r das Internet bzw. die " +
                        "Informationsgesellschaft relevanten rechtlichen " +
                        "Aspekten zu er??ffnen und f??r aktuelle " +
                        "rechtspolitische Problemstellungen zu " +
                        "sensibilisieren. Ferner soll ein Beitrag zur " +
                        "Reduktion der rechtlichen Risiken, denen " +
                        "(Wirtschafts-)Techniker/innen im Rahmen ihrer" +
                        " beruflichen Praxis ausgesetzt sind, " +
                        "geleistet werden. Im Hinblick auf eine " +
                        "Verdichtung des Praxisbezugs sind " +
                        "Gastvortr??ge externer Experten geplant. " +
                        "Eine aktive Auseinandersetzung mit den " +
                        "die Vorlesung unterst??tzenden " +
                        "Unterlagen bzw. Internet-Ressourcen " +
                        "sowie Diskussionsbereitschaft der " +
                        "Teilnehmer/innen wird erwartet!\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Diese Lehrveranstaltung widmet sich dem Schwerpunkt " +
                        "\"Rechtliche Problematik des Internet\" (zB.: " +
                        "Grundprobleme von Recht und Technik, " +
                        "Strukturfragen des Internetrechts, Grundrechte in" +
                        " der Informationsgesellschaft, " +
                        "Telekommunikationsrecht, Urheberrecht, " +
                        "E-Commerce-Recht, Strafrecht etc.). Die " +
                        "Teilnehmer/innen sind zur intensiven " +
                        "Auseinandersetzung mit den via TUWEL " +
                        "bereitgestellten Internetressourcen sowie " +
                        "zur aktiven Mitgestaltung der Vorlesung " +
                        "eingeladen. Aktuelle Entwicklungen werden" +
                        " laufend einbezogen und im Schnittfeld " +
                        "von Recht und Technik diskutiert.\n" + "\n" +
                        "Weitere Informationen\n" +
                        "Vorbesprechung: Di., 4.10.2016, 10.00-12.00 Uhr, " +
                        "Informatikh??rsaal\n" + "\n" +
                        "Erster Vorlesungstermin: Di., 11.10.2016!\n" + "\n" +
                        "Nachmeldetermine (Hier haben Sie die M??glichkeit, " +
                        "Ihre Unterschrift auf der TeilnehmerInnenliste zu " +
                        "leisten, falls Sie das nicht bereits in der " +
                        "Vorbesprechung erledigt haben):\n" + "\n" +
                        "Nachmeldetermin 1 - Do., 6.10.2016, 18.00-19.00 Uhr." +
                        " Ort: Vorraum zum Informatikh??rsaal.\n" +
                        "Nachmeldetermin 2 - Di., 11.10.2016, 12.00-13.00 Uhr" +
                        ". Ort: Vorraum zum Informatikh??rsaal.\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VU Datenbanksysteme").setDescription(
                "Ziele der Lehrveranstaltung\n" +
                        "Fachliche und methodische Kenntnisse:\n" +
                        "Grundlagen, Komponenten, und Funktionsweise von " +
                        "Datenbankmanagementsystemen (DBMS); " +
                        "Datenbankarchitektur und Datenunabh??ngigkeit\n" +
                        "Komplexe SQL Abfragen, Einbettung in prozedurale " +
                        "Abfragen (JDBC)\n" +
                        "Physische Datenorganisation, Datenbanktuning\n" +
                        "Transaktionen, Fehlerbehandlung, " +
                        "Mehrbenutzersynchronisation\n" +
                        "Verteilte Datenbanken\n" +
                        "Kognitive und praktische Fertigkeiten:\n" +
                        "Verwendung von DBMS und Benutzung deklarativer " +
                        "Abfragesprachen\n" +
                        "Programmierung von und Anbindung an " +
                        "Datenbanksysteme\n" +
                        "Soziale Kompetenzen, Innovationskompetenz und " +
                        "Kreativit??t:\n" +
                        "Funktionale Denkweise zum Verst??ndnis deklarativer " +
                        "Abfragesprachen\n" +
                        "Logisches Denken um Abl??ufe in einem DBMS " +
                        "nachzuvollziehen\n" +
                        "Mathematisch fundierte Vorgehensweise zur Analyse " +
                        "von Methoden in DBMS\n" +
                        "Kenntnisse der eigenen F??higkeiten und Grenzen, " +
                        "Kritikf??higkeit an der eigenen Arbeit\n" +
                        "Selbstorganisation und Eigenverantwortlichkeit zum " +
                        "eigenst??ndigen L??sen von Laboraufgaben\n" +
                        "Inhalt der Lehrveranstaltung\n" + "Schwerpunkte:\n" +
                        "Datenbank-Programmierung (mit PL/SQL und JDBC) und " +
                        "SQL-Erweiterungen\n" +
                        "Physische Datenorganisation und Anfragebearbeitung\n" +
                        "Transaktionen, Fehlerbehandlung/Recovery, " +
                        "Mehrbenutzersynchronisation\n" +
                        "Weiterf??hrende Themen (z.B.: verteilte Datenbanken)" +
                        "\n" + "Didaktisches Vorgehen:\n" + "Vorlesungsteil\n" +
                        "Es gibt 2 ??bungen die die Konzepte der Vorlesung " +
                        "vertiefen.\n" +
                        "Die ??bungsbeispiele werden, bevor sie starten, in " +
                        "der Vorlesung besprochen.\n" +
                        "Zur Betreuung der ??bung gibt es Fragestunden bei den" +
                        " Tutorinnen und Tutoren verteilt auf die Zeit vor " +
                        "den Abgabeterminen.\n" +
                        "Die ??bung besteht haupts??chlich aus " +
                        "Programmieraufgaben und flie??t zu 25% in die " +
                        "Gesamtnote ein.\n" +
                        "Um f??r alle Studierenden gleiche Voraussetzungen bei" +
                        " der Abgabe zu schaffen gibt es einen einheitlichen" +
                        " Abgabetermin f??r alle Studenten gefolgt von den " +
                        "Abgabegespr??chen.\n" +
                        "Bei den Abgabegespr??chen werden die Beispiele auf " +
                        "Korrektheit, aber besonders auf das Verst??ndnis " +
                        "gepr??ft, und entsprechend Feedback gegeben.\n" +
                        "Die Pr??fung besteht aus einem Theorieteil und " +
                        "praktischen Beispielen.\n" +
                        "Es gibt vier Pr??fungstermine (einen am Semesterende," +
                        " drei weitere im Folgesemester) die zu 75% in die " +
                        "Note einflie??en.\n" +
                        "Bitte beachten Sie f??r diese LVA unbedingt auch die " +
                        "lokale Homepage der Lehrveranstaltung.\n" + "\n");

        coursesBachelorSoftwareAndInformationEngineering
                .get("VO Statistik und Wahrscheinlichkeitstheorie")
                .setDescription("Ziele der Lehrveranstaltung\n" +
                        "Verst??ndnis von Wahrscheinlichkeiten zur " +
                        "Beschreibung realer Ph??nomene sowie F??higkeit zur " +
                        "Konstruktion stochastischer Modelle und deren " +
                        "statistischer Analyse.\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Diese Vorlesung ist eine Einf??hrung in die " +
                        "Stochastik f??r Studierende der Informatik. " +
                        "Ausgehend von empirischen Verteilungen und " +
                        "Histogrammen werden folgende Themenkreise der " +
                        "Beschreibung von Unsicherheit behandelt: " +
                        "Wahrscheinlichkeitsr??ume und stochastische " +
                        "Gr????en, stochastische Unabh??ngigkeit, Gesetz " +
                        "der gro??en Zahlen und zentraler " +
                        "Grenzverteilungssatz, Fundamentalsatz der " +
                        "Statistik, klassische schlie??ende " +
                        "Statistik, Grundlagen der Bayes'schen " +
                        "Statistik und Methoden der linearen " +
                        "Regressionsanalyse.\n" + "\n" +
                        "Weitere Informationen\n" + "f??r InformatikerInnen");

        coursesBachelorSoftwareAndInformationEngineering
                .get("UE Statistik und Wahrscheinlichkeitstheorie")
                .setDescription("Ziele der Lehrveranstaltung\n" +
                        "Verst??ndnis von Wahrscheinlichkeiten zur " +
                        "Beschreibung realer Ph??nomene sowie F??higkeit zur " +
                        "Konstruktion stochastischer Modelle und deren " +
                        "statistischer Analyse.\n" + "\n" +
                        "Inhalt der Lehrveranstaltung\n" +
                        "Diese Vorlesung ist eine Einf??hrung in die " +
                        "Stochastik f??r Studierende der Informatik. " +
                        "Ausgehend von empirischen Verteilungen und " +
                        "Histogrammen werden folgende Themenkreise der " +
                        "Beschreibung von Unsicherheit behandelt: " +
                        "Wahrscheinlichkeitsr??ume und stochastische " +
                        "Gr????en, stochastische Unabh??ngigkeit, Gesetz " +
                        "der gro??en Zahlen und zentraler " +
                        "Grenzverteilungssatz, Fundamentalsatz der " +
                        "Statistik, klassische schlie??ende " +
                        "Statistik, Grundlagen der Bayes'schen " +
                        "Statistik und Methoden der linearen " +
                        "Regressionsanalyse.\n" + "\n" +
                        "Weitere Informationen\n" + "f??r InformatikerInnen");
    }

    private void addDescriptionToCourses() {
        addDescriptionToBachelorSoftwareAndInformationEngineeringCoursesSemester1();
        addDescriptionToBachelorSoftwareAndInformationEngineeringCoursesSemester2();
        addDescriptionToBachelorSoftwareAndInformationEngineeringCoursesSemester3();
    }

    private void addSubjectsToStudyPlans() {
        addBachelorSoftwareAndInformationEngineeringSubjectsToStudyPlan();
    }

    private void addBachelorSoftwareAndInformationEngineeringSubjectsToStudyPlan() {
        for (String subjectName :
         subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester1
                .keySet()) {
            subjectForStudyPlanRepository.save(new SubjectForStudyPlanEntity(
                    subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester1
                            .get(subjectName), studyplans.get(0), true, 1));
        }

        for (String subjectName :
         subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester2
                .keySet()) {
            subjectForStudyPlanRepository.save(new SubjectForStudyPlanEntity(
                    subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester2
                            .get(subjectName), studyplans.get(0), true, 2));
        }

        for (String subjectName :
         subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester3
                .keySet()) {
            subjectForStudyPlanRepository.save(new SubjectForStudyPlanEntity(
                    subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester3
                            .get(subjectName), studyplans.get(0), true, 3));
        }

        for (String subjectName :
         subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester4
                .keySet()) {
            subjectForStudyPlanRepository.save(new SubjectForStudyPlanEntity(
                    subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester4
                            .get(subjectName), studyplans.get(0), true, 4));
        }

        for (String subjectName :
         subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester5
                .keySet()) {
            subjectForStudyPlanRepository.save(new SubjectForStudyPlanEntity(
                    subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester5
                            .get(subjectName), studyplans.get(0), true, 5));
        }

        for (String subjectName :
         subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester6
                .keySet()) {
            subjectForStudyPlanRepository.save(new SubjectForStudyPlanEntity(
                    subjectsMandatoryBachelorSoftwareAndInformationEngineeringSemester6
                            .get(subjectName), studyplans.get(0), true, 6));
        }

        for (String subjectName :
         subjectsOptionalBachelorSoftwareAndInformationEngineering
                .keySet()) {
            subjectForStudyPlanRepository.save(new SubjectForStudyPlanEntity(
                    subjectsOptionalBachelorSoftwareAndInformationEngineering
                            .get(subjectName), studyplans.get(0), false));
        }
    }

    private void registerCoursesToStudents() {
        // Joan Watson
        register(studentMap.get("Joan Watson"),
                coursesBachelorSoftwareAndInformationEngineering
                        .get("UE Studieneingangsgespr??ch"));
        register(studentMap.get("Joan Watson"),
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VU Technische Grundlagen der Informatik"));
        register(studentMap.get("Joan Watson"),
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VO Algebra und Diskrete Mathematik f??r " +
                                "Informatik und Wirtschaftsinformatik"));

        // Emma Dowd
        register(studentMap.get("Emma Dowd"),
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VU Technische Grundlagen der Informatik"));
        register(studentMap.get("Emma Dowd"),
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VO Algebra und Diskrete Mathematik f??r " +
                                "Informatik und Wirtschaftsinformatik"));

        // Caroline Black
        register(studentMap.get("Caroline Black"),
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VU Technische Grundlagen der Informatik"));

        // John Terry
        StudentEntity john = studentMap.get("John Terry");
        register(john, coursesBachelorSoftwareAndInformationEngineering
                .get("VU Datenmodellierung"));
        register(john, coursesBachelorSoftwareAndInformationEngineering
                .get("VO Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik"));
        register(john, coursesBachelorSoftwareAndInformationEngineering
                .get("VU Programmkonstruktion"));

        // Mathematician - Diego Costa
        StudentEntity mathematician = studentMap.get("Mathematician");
        register(mathematician, coursesBachelorSoftwareAndInformationEngineering
                .get("UE Studieneingangsgespr??ch"));
        register(mathematician, coursesBachelorSoftwareAndInformationEngineering
                .get("VO Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik"));
        register(mathematician, coursesBachelorSoftwareAndInformationEngineering
                .get("UE Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik"));
        register(mathematician, coursesBachelorSoftwareAndInformationEngineering
                .get("VO Statistik und Wahrscheinlichkeitstheorie"));
        register(mathematician, coursesBachelorSoftwareAndInformationEngineering
                .get("UE Statistik und Wahrscheinlichkeitstheorie"));
        //register(mathematician,
        // coursesBachelorSoftwareAndInformationEngineering.get("VU
        // Techniksoziologie und Technikpsychologie"));

        // SimilarToMathematician - Cesc Fabregas
        StudentEntity similarToMathematician =
                studentMap.get("SimilarToMathematician");
        register(similarToMathematician,
                coursesBachelorSoftwareAndInformationEngineering
                        .get("UE Studieneingangsgespr??ch"));
        register(similarToMathematician,
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VO Algebra und Diskrete Mathematik f??r " +
                                "Informatik und Wirtschaftsinformatik"));
        register(similarToMathematician,
                coursesBachelorSoftwareAndInformationEngineering
                        .get("UE Algebra und Diskrete Mathematik f??r " +
                                "Informatik und Wirtschaftsinformatik"));
        register(similarToMathematician,
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VO Statistik und Wahrscheinlichkeitstheorie"));
        register(similarToMathematician,
                coursesBachelorSoftwareAndInformationEngineering
                        .get("UE Statistik und Wahrscheinlichkeitstheorie"));

        StudentEntity newStudent = studentMap.get("NewStudent");
        register(newStudent, coursesBachelorSoftwareAndInformationEngineering
                .get("UE Studieneingangsgespr??ch"));
    }

    private void register(StudentEntity student, Course course) {
        course.addStudents(student);
        studentSubjectPreferenceStore.studentRegisteredCourse(student, course);
    }

    private void giveGrades() {
        Course course =
                coursesBachelorSoftwareAndInformationEngineering
                        .get("UE Studieneingangsgespr??ch");
        StudentEntity student = course.getStudents().get(0);
        LecturerEntity lecturer = course.getSubject().getLecturers().get(0);

        Grade grade =
                new Grade(course, lecturer, student, MarkEntity.EXCELLENT);
        gradeRepository.save(grade);

        course = coursesBachelorSoftwareAndInformationEngineering
                .get("VU Technische Grundlagen der Informatik");
        student = studentMap.get("Emma Dowd");
        lecturer = course.getSubject().getLecturers().get(0);

        grade = new Grade(course, lecturer, student, MarkEntity.EXCELLENT);
        gradeRepository.save(grade);

        course = coursesBachelorSoftwareAndInformationEngineering
                .get("VO Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik");
        student = studentMap.get("Emma Dowd");
        lecturer = course.getSubject().getLecturers().get(0);

        grade = new Grade(course, lecturer, student, MarkEntity.FAILED);
        gradeRepository.save(grade);

        grade = new Grade(coursesBachelorSoftwareAndInformationEngineering
                .get("VU Datenmodellierung"), lecturer,
                studentMap.get("John Terry"), MarkEntity.EXCELLENT);
        gradeRepository.save(grade);

        // Mathematician - Diego Costa
        grade = new Grade(coursesBachelorSoftwareAndInformationEngineering
                .get("VO Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik"), lecturer,
                studentMap.get("Mathematician"), MarkEntity.EXCELLENT);
        gradeRepository.save(grade);

        grade = new Grade(coursesBachelorSoftwareAndInformationEngineering
                .get("UE Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik"), lecturer,
                studentMap.get("Mathematician"), MarkEntity.EXCELLENT);
        gradeRepository.save(grade);

        // SimilarToMathematician - Cesc Fabregas
        grade = new Grade(coursesBachelorSoftwareAndInformationEngineering
                .get("VO Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik"), lecturer,
                studentMap.get("SimilarToMathematician"), MarkEntity.EXCELLENT);
        gradeRepository.save(grade);

        grade = new Grade(coursesBachelorSoftwareAndInformationEngineering
                .get("UE Algebra und Diskrete Mathematik f??r Informatik und " +
                        "Wirtschaftsinformatik"), lecturer,
                studentMap.get("SimilarToMathematician"), MarkEntity.EXCELLENT);
        gradeRepository.save(grade);
    }

    private void giveFeedback() {
        Course course =
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VU Datenmodellierung");
        StudentEntity johnTerry = studentMap.get("John Terry");
        Feedback feedback = new Feedback(johnTerry, course);

        course = coursesBachelorSoftwareAndInformationEngineering
                .get("VU Technische Grundlagen der Informatik");
        StudentEntity joanWatson = studentMap.get("Joan Watson");
        StudentEntity emmaDowd = studentMap.get("Emma Dowd");
        StudentEntity carolineBlack = studentMap.get("Caroline Black");
        StudentEntity mathematician = studentMap.get("Mathematician");
        StudentEntity similarToMathematician =
                studentMap.get("SimilarToMathematician");

        Feedback feedback1 =
                new Feedback(joanWatson, course, Feedback.Type.LIKE,
                        "Lorem ipsum dolor sit amet, consectetur adipiscing " +
                                "elit. Nullam nec enim ligula. " +
                                "Sed eget posuere tellus. Aenean fermentum " +
                                "maximus tempor. Ut ultricies dapibus nulla " +
                                "vitae mollis. " +
                                "Suspendisse a nunc nisi. Sed ut sapien eu " +
                                "odio sodales laoreet eu ac turpis. " +
                                "In id sapien id ante sollicitudin " +
                                "consectetur at laoreet mi. Lorem ipsum " +
                                "dolor sit amet, consectetur adipiscing " +
                                "elit. " +
                                "Suspendisse quam sem, ornare eget " +
                                "pellentesque sit amet, tincidunt id metus. " +
                                "Sed scelerisque neque sed laoreet " +
                                "elementum. " +
                                "Integer eros neque, vulputate a hendrerit " +
                                "at, ullamcorper in orci. Donec sit amet " +
                                "risus hendrerit, hendrerit magna non, " +
                                "dapibus nibh. " +
                                "Suspendisse sed est feugiat, dapibus ante " +
                                "non, aliquet neque. Cras magna sapien, " +
                                "pharetra ut ante ut, malesuada hendrerit " +
                                "erat. " +
                                "Mauris fringilla mattis dapibus. Nullam " +
                                "iaculis nunc in tortor gravida, id tempor " +
                                "justo elementum.");
        Feedback feedback2 =
                new Feedback(emmaDowd, course, Feedback.Type.DISLIKE,
                        "Lorem ipsum dolor sit amet, consectetur adipiscing " +
                                "elit. Nullam nec enim ligula. " +
                                "Sed eget posuere tellus. Aenean fermentum " +
                                "maximus tempor. Ut ultricies dapibus nulla " +
                                "vitae mollis. " +
                                "Suspendisse a nunc nisi. Sed ut sapien eu " +
                                "odio sodales laoreet eu ac turpis. " +
                                "In id sapien id ante sollicitudin " +
                                "consectetur at laoreet mi. Lorem ipsum " +
                                "dolor sit amet, consectetur adipiscing " +
                                "elit. " +
                                "Suspendisse quam sem, ornare eget " +
                                "pellentesque sit amet, tincidunt id metus. " +
                                "Sed scelerisque neque sed laoreet " +
                                "elementum. " +
                                "Integer eros neque, vulputate a hendrerit " +
                                "at, ullamcorper in orci. Donec sit amet " +
                                "risus hendrerit, hendrerit magna non, " +
                                "dapibus nibh. " +
                                "Suspendisse sed est feugiat, dapibus ante " +
                                "non, aliquet neque. Cras magna sapien, " +
                                "pharetra ut ante ut, malesuada hendrerit " +
                                "erat. " +
                                "Mauris fringilla mattis dapibus. Nullam " +
                                "iaculis nunc in tortor gravida, id tempor " +
                                "justo elementum.");
        Feedback feedback3 =
                new Feedback(carolineBlack, course, Feedback.Type.LIKE,
                        "Lorem ipsum dolor sit amet, consectetur adipiscing " +
                                "elit. Nullam nec enim ligula. " +
                                "Sed eget posuere tellus. Aenean fermentum " +
                                "maximus tempor. Ut ultricies dapibus nulla " +
                                "vitae mollis. " +
                                "Suspendisse a nunc nisi. Sed ut sapien eu " +
                                "odio sodales laoreet eu ac turpis. " +
                                "In id sapien id ante sollicitudin " +
                                "consectetur at laoreet mi. Lorem ipsum " +
                                "dolor sit amet, consectetur adipiscing " +
                                "elit. " +
                                "Suspendisse quam sem, ornare eget " +
                                "pellentesque sit amet, tincidunt id metus. " +
                                "Sed scelerisque neque sed laoreet " +
                                "elementum. " +
                                "Integer eros neque, vulputate a hendrerit " +
                                "at, ullamcorper in orci. Donec sit amet " +
                                "risus hendrerit, hendrerit magna non, " +
                                "dapibus nibh. " +
                                "Suspendisse sed est feugiat, dapibus ante " +
                                "non, aliquet neque. Cras magna sapien, " +
                                "pharetra ut ante ut, malesuada hendrerit " +
                                "erat. " +
                                "Mauris fringilla mattis dapibus. Nullam " +
                                "iaculis nunc in tortor gravida, id tempor " +
                                "justo elementum.");

        Feedback feedback4 = new Feedback(mathematician,
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VO Algebra und Diskrete Mathematik f??r " +
                                "Informatik und Wirtschaftsinformatik"));

        Feedback feedback5 = new Feedback(mathematician,
                coursesBachelorSoftwareAndInformationEngineering
                        .get("UE Algebra und Diskrete Mathematik f??r " +
                                "Informatik und Wirtschaftsinformatik"));

        Feedback feedback6 = new Feedback(similarToMathematician,
                coursesBachelorSoftwareAndInformationEngineering
                        .get("UE Algebra und Diskrete Mathematik f??r " +
                                "Informatik und Wirtschaftsinformatik"));

        Feedback feedback7 = new Feedback(similarToMathematician,
                coursesBachelorSoftwareAndInformationEngineering
                        .get("VO Algebra und Diskrete Mathematik f??r " +
                                "Informatik und Wirtschaftsinformatik"));

        giveFeedback(feedback, feedback1, feedback2, feedback3, feedback4,
                feedback5, feedback6, feedback7);
    }

    private void giveFeedback(Feedback... feedbacks) {
        for (Feedback feedback : feedbacks) {
            feedbackRepository.save(feedback);
            studentSubjectPreferenceStore
                    .studentGaveCourseFeedback(feedback.getStudent(), feedback);
        }
    }
}
