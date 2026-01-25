package edu.depaul.wberthou.graph;

public enum CampusBuilding {
    CLASSROOM_A("Class Room Building A"),
    CLASSROOM_B("Class Room Building B"),
    STUDENT_COMMONS("Student Commons"),
    ADMINISTRATION_BUILDING("Administration Building"),
    TRANSPORTATION_HUB("Transportation Hub"),
    FACULTY_OFFICE("Faculty Office Building"),
    CONFERENCE_CENTER("Conference Center");

    private final String id;
    CampusBuilding(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

}
