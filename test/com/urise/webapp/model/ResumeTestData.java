package com.urise.webapp.model;

import com.urise.webapp.storage.Storage;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.ls.LSOutput;

import java.nio.channels.SelectionKey;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ResumeTestData {

    protected final Resume resume = new Resume(UUID, FULLNAME);
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
    public static final LocalDate EDUCATION1_INTERVAL_START_DATE = LocalDate.of(2013, 03, 1);
    public static final LocalDate EDUCATION1_INTERVAL_END_DATE = LocalDate.of(2025, 05, 1);


    @Before
    public void setUp() {
        resume.setContact(Resume.ContactType.PHONE, PHONE);
        resume.setContact(Resume.ContactType.SKYPE, SKYPE);
        resume.setContact(Resume.ContactType.EMAIL, EMAIL);
        resume.setContact(Resume.ContactType.LINKEDIN, LINKEDIN);
        resume.setContact(Resume.ContactType.GITHUB, GITHUB);
        resume.setContact(Resume.ContactType.STACKOVERFLOW, STACKOVERFLOW);
        resume.setContact(Resume.ContactType.HOMEPAGE, HOMEPAGE);

        resume.setSection(SectionType.OBJECTIVE,
                new TextSection(SectionType.OBJECTIVE, OBJECTIVE));
        resume.setSection(SectionType.PERSONAL,
                new TextSection(SectionType.PERSONAL, PERSONAL));
        resume.setSection(SectionType.ACHIEVEMENT,
                new ListSection(SectionType.ACHIEVEMENT,
                        new ArrayList<>(Arrays.asList(ACHIEVEMENT1, ACHIEVEMENT2))));
        resume.setSection(SectionType.QUALIFICATIONS,
                new ListSection(SectionType.QUALIFICATIONS,
                        new ArrayList<>(Arrays.asList(QUALIFICATION1, QUALIFICATION2))));

        Company workplace = new Company(WORKPLACE1_NAME, WORKPLACE1_WEBSITE);
        Company.Interval work_interval = workplace.new Interval(WORKPLACE1_INTERVAL_NAME,
                WORKPLACE1_INTERVAL_START_DATE, WORKPLACE1_INTERVAL_END_DATE);
        work_interval.setDescription(WORKPLACE1_INTERVAL_DESCRIPTION);
        workplace.addInterval(work_interval);

        resume.setSection(SectionType.EXPERIENCE,
                new CompanySection(SectionType.EXPERIENCE,
                        new ArrayList<>(Arrays.asList(workplace))));

        Company education = new Company(EDUCATION1_NAME, EDUCATION1_WEBSITE);
        Company.Interval edc_interval = education.new Interval(EDUCATION1_INTERVAL_NAME,
                EDUCATION1_INTERVAL_START_DATE, EDUCATION1_INTERVAL_END_DATE);
        education.addInterval(edc_interval);

        resume.setSection(SectionType.EXPERIENCE,
                new CompanySection(SectionType.EXPERIENCE,
                        new ArrayList<>(Arrays.asList(education))));

    }

    @Test
    public void printTest() {
        System.out.println(resume);
    }

    public static void main(String[] args) {
        ResumeTestData test = new ResumeTestData();
        test.printTest();
    }
}