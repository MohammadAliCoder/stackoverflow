package app_Users.Model;

import java.sql.Date;

public class Solutions {
    private int id;
    private String solution;
    private Date date_make;
    private boolean isChecked;
    private String username;
    private int idquestion;

    public Solutions(){}

    public Solutions(int id, String solution, Date date_make, boolean isChecked, String username, int idquestion) {
        this.id = id;
        this.solution = solution;
        this.date_make = date_make;
        this.isChecked = isChecked;
        this.username = username;
        this.idquestion = idquestion;
    }

    public Solutions(String solution, Date date_make, boolean isChecked, String username, int idquestion) {
        this.solution = solution;
        this.date_make = date_make;
        this.isChecked = isChecked;
        this.username = username;
        this.idquestion = idquestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Date getDate_make() {
        return date_make;
    }

    public void setDate_make(Date date_make) {
        this.date_make = date_make;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdquestion() {
        return idquestion;
    }

    public void setIdquestion(int idquestion) {
        this.idquestion = idquestion;
    }
}
