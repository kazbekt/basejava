package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    protected String name;
    protected List<Company> companies;

    public CompanySection(SectionType sectionType, List<Company> companies) {
        super(sectionType);
        name = sectionType.getSectionName();
    }

    @Override
    protected boolean isValidSectionType(SectionType sectionType) {
        return sectionType == SectionType.EXPERIENCE ||
                sectionType == SectionType.EDUCATION;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void addCompany(Company company) {
        companies.add(company);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(name, that.name) && Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, companies);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "sectionName='" + name + '\'' +
                ", content=" + companies +
                '}';
    }
}
