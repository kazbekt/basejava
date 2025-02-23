package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends Section {
    protected String sectionName;
    protected List<String> content;

    public ListSection(SectionType sectionType, List<String> content) {
        super(sectionType);
        sectionName = sectionType.getSectionName();
    }

    @Override
    protected boolean isValidSectionType(SectionType sectionType) {
        return sectionType == SectionType.ACHIEVEMENT ||
                sectionType == SectionType.QUALIFICATIONS;
    }

    public String getSectionName() {
        return sectionName;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "content=" + content +
                '}';
    }
}
