package com.urise.webapp.model;

public abstract class Section {
    private SectionType sectionType;

    public Section(SectionType sectionType) {
        if (!isValidSectionType(sectionType)) {
            throw new IllegalArgumentException("Invalid Section Type: " + sectionType);
        }
        this.sectionType = sectionType;
    }

    protected abstract boolean isValidSectionType(SectionType sectionType);
}
