package models;

import play.data.validation.Constraints.Required;


public class TaskForm {

    @Required
    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
