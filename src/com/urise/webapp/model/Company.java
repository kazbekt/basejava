package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {
    private String name = "";
    private String website = "";
    protected List<Interval> intervals = new ArrayList<>();

    public Company(String name, String website) {
        this.name = name;
        this.website = website;
    }

    public void addInterval(Interval interval) {
        intervals.add(interval);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Interval> getIntervals() {
        return intervals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(website, company.website) && Objects.equals(intervals, company.intervals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, intervals);
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", website='" + website + '\'' +
                ", intervals=" + intervals +
                '}';
    }

    protected class Interval {
        private String name = "";
        private LocalDate startDate;
        private LocalDate endDate;
        private String description = "";

        public Interval(String name, LocalDate startDate, LocalDate endDate) {
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Interval interval = (Interval) o;
            return Objects.equals(name, interval.name) && Objects.equals(startDate, interval.startDate) && Objects.equals(endDate, interval.endDate) && Objects.equals(description, interval.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, startDate, endDate, description);
        }

        @Override
        public String toString() {
            return "Interval{" +
                    "name='" + name + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

}
