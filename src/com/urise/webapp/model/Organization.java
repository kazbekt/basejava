package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.urise.webapp.util.DateUtil.NOW;
import static com.urise.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    private Link homePage;
    private List<Period> periods = new ArrayList<>();

    public Organization(){}

    public Organization(String name, String url, Period...periods){
        this(new Link(name, url), Arrays.asList(periods));
    }

    public Organization(Link homePage, List<Period> periods) {
        this.homePage = homePage;
        this.periods = periods;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPeriod(Period period) {
        this.periods.add(period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        if (!homePage.equals(that.homePage)) return false;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + periods.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", periods=" + periods +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable{
        @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateXmlAdapter.class)
        private LocalDate endDate;
        private String title;
        private  String description;


        public Period(){}
        public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(title, "title must not be null");
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(endDate, "description must not be null");
            this.title = title;
            this.startDate = startDate;
            this.endDate = endDate;
            this.description = description;
        }

        public Period(int startYear, Month startMonth, String title, String description) {
            this(of(startYear, startMonth), NOW, title, description);
        }

        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period position = (Period) o;
            return Objects.equals(startDate, position.startDate) &&
                    Objects.equals(endDate, position.endDate) &&
                    Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return "Period{" +
                    "name='" + title + '\'' + "\n" +
                    "startDate=" + startDate + "\n" +
                    "endDate=" + endDate + "\n" +
                    "description='" + description + '\'' +
                    '}';
        }
    }
}