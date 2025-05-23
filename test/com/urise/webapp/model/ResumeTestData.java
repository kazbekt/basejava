package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {

    public static final String UUID_0 = "uuid0";
    public static final String RESUME_0 = "Name0";

    public static final String UUID_1 = "uuid1";
    public static final String RESUME_1 = "Name1";

    public static final String UUID_2 = "uuid2";
    public static final String RESUME_2 = "Name2";

    public static final String UUID_3 = "uuid3";
    public static final String RESUME_3 = "Name3";

    public static final String DUMMY = "dummy";

    private static final String UUID = "uuid";
    private static final String FULLNAME = "Григорий Кислин";
    private static final String PHONE = "+7(921) 855-0482";
    private static final String SKYPE = "grigory.kislin";
    private static final String EMAIL = "gkislin@yandex.ru";
    private static final String LINKEDIN = "https://www.linkedin.com/in/gkislin";
    private static final String GITHUB = "https://github.com/gkislin";
    private static final String STACKOVERFLOW = "https://stackoverflow.com/users/548473";
    private static final String HOMEPAGE = "http://gkislin.ru/";

    private static final String OBJECTIVE = "Ведущий стажировок и корпоративного" +
            " обучения по Java Web и Enterprise технологиям";
    public static final String PERSONAL = "Аналитический склад ума, сильная логика, " +
            "креативность, инициативность. Пурист кода и архитектуры.";

    public static final String ACHIEVEMENT1 = "Организация команды и успешная реализация" +
            " Java проектов для сторонних заказчиков";
    public static final String ACHIEVEMENT2 = "Разработка Web приложения";
    public static final String QUALIFICATION1 = "JEE AS: GlassFish (v2.1, v3)";
    public static final String QUALIFICATION2 = "Languages: Java, Scala, Python/Jython/PL-Python";

    public static final String WORKPLACE1_NAME = "Java Online Projects";
    public static final String WORKPLACE1_WEBSITE = "http://javaops.ru/";
    public static final String WORKPLACE1_INTERVAL_NAME = "Автор проекта";
    public static final LocalDate WORKPLACE1_INTERVAL_START_DATE = LocalDate.of(2013, 10, 1);
    public static final LocalDate WORKPLACE1_INTERVAL_END_DATE = LocalDate.of(2025, 1, 1);
    public static final String WORKPLACE1_INTERVAL_DESCRIPTION = "Создание, организация и проведение Java онлайн проектов и стажировок.";

    public static final String EDUCATION1_NAME = "Coursera";
    public static final String EDUCATION1_WEBSITE = "https://www.coursera.org/course/progfun";
    public static final String EDUCATION1_INTERVAL_NAME = "Functional Programming Principles in Scala' by Martin Odersky";
    public static final LocalDate EDUCATION1_INTERVAL_START_DATE = LocalDate.of(2013, 3, 1);
    public static final LocalDate EDUCATION1_INTERVAL_END_DATE = LocalDate.of(2025, 5, 1);

    //Из-за того, что этот блок стоял раньше остальных, происходила ошибка при инициализации
    public static final Resume r0 = ResumeTestData.filledResume(UUID_0, RESUME_0);
    public static final Resume r1 = ResumeTestData.filledResume(UUID_1, RESUME_1);
    public static final Resume r2 = ResumeTestData.filledResume(UUID_2, RESUME_2);
    public static final Resume r3 = ResumeTestData.filledResume(UUID_3, RESUME_3);

    public static Resume filledResume(String uuid, String fullname) {
        Resume resume = new Resume(uuid, fullname);
        resume.addContact(Resume.ContactType.PHONE, PHONE);
        resume.addContact(Resume.ContactType.SKYPE, SKYPE);
        resume.addContact(Resume.ContactType.EMAIL, EMAIL);
        resume.addContact(Resume.ContactType.LINKEDIN, LINKEDIN);
        resume.addContact(Resume.ContactType.GITHUB, GITHUB);
        resume.addContact(Resume.ContactType.STACKOVERFLOW, STACKOVERFLOW);
        resume.addContact(Resume.ContactType.HOMEPAGE, HOMEPAGE);

        Section objective = new TextSection(OBJECTIVE);
        resume.addSection(SectionType.OBJECTIVE, objective);

        Section personal = new TextSection(PERSONAL);
        resume.addSection(SectionType.PERSONAL, personal);

        Section achievements = new ListSection(
                new ArrayList<>(Arrays.asList(ACHIEVEMENT1, ACHIEVEMENT2)));
        resume.addSection(SectionType.ACHIEVEMENT, achievements);

        Section qualifications = new ListSection(
                new ArrayList<>(Arrays.asList(QUALIFICATION1, QUALIFICATION2)));
        resume.addSection(SectionType.QUALIFICATIONS, qualifications);

        Organization.Period period = new Organization.Period(
                WORKPLACE1_INTERVAL_START_DATE,
                WORKPLACE1_INTERVAL_END_DATE,
                WORKPLACE1_INTERVAL_NAME,
                WORKPLACE1_INTERVAL_DESCRIPTION
        );

        Organization organization = new Organization(
                WORKPLACE1_NAME,
                WORKPLACE1_WEBSITE,
                period
        );

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);

        Section experience = new OrganizationSection(organizations);
        resume.addSection(SectionType.EXPERIENCE, experience);

        Organization.Period period1 = new Organization.Period(
                EDUCATION1_INTERVAL_START_DATE,
                EDUCATION1_INTERVAL_END_DATE,
                EDUCATION1_INTERVAL_NAME,
                ""
        );

        Organization organization1 = new Organization(
                EDUCATION1_NAME,
                EDUCATION1_WEBSITE,
                period1
        );

        List<Organization> educationList = new ArrayList<>();
        educationList.add(organization1);

        Section education = new OrganizationSection(educationList);
        resume.addSection(SectionType.EDUCATION, education);

        return resume;
    }

    public static void main(String[] args) {
        ResumeTestData test = new ResumeTestData();
        Resume resume = test.filledResume(UUID, FULLNAME);
        System.out.println(resume);
    }
}