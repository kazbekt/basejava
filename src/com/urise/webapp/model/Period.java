package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {

    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Period(String title, LocalDate startDate, LocalDate endDate, String description) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return Objects.equals(title, period.title) && Objects.equals(startDate, period.startDate) && Objects.equals(endDate, period.endDate) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, startDate, endDate, description);
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