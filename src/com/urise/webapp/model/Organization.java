package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private final Link homePage;
    private final List<Period> periods;

    public Organization(String name, String url) {
        this.homePage = new Link(name, url);
        this.periods = new ArrayList<>();
    }

    public void addPeriod(Period period) {
        this.periods.add(period);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

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
}