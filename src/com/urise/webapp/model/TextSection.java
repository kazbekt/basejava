package com.urise.webapp.model;

import java.util.Objects;

public class TextSection extends Section {
    protected String name;
    protected String content;

    public TextSection(SectionType sectionType, String content) {
        super(sectionType);
        name = sectionType.getSectionName();
        this.content = content;
    }

    @Override
    protected boolean isValidSectionType(SectionType sectionType) {
        return sectionType == SectionType.OBJECTIVE ||
                sectionType == SectionType.PERSONAL;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return name + '{' +
                "content='" + content + '\'' +
                '}';
    }
}
