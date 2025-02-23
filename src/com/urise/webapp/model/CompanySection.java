package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class CompanySection extends Section {
    protected String sectionName;
    protected List<Company> content;

    public CompanySection(SectionType sectionType, List<Company> content) {
        super(sectionType);
        sectionName = sectionType.getSectionName();
    }

    @Override
    protected boolean isValidSectionType(SectionType sectionType) {
        return sectionType == SectionType.EXPERIENCE ||
                sectionType == SectionType.EDUCATION;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public List<Company> getContent() {
        return content;
    }

    public void addCompany(Company company) {
        content.add(company);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(sectionName, that.sectionName) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionName, content);
    }

    @Override
    public String toString() {
        return "CompanySection{" +
                "sectionName='" + sectionName + '\'' +
                ", content=" + content +
                '}';
    }
}
