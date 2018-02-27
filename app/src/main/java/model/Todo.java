package model;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * @author Lord_ on 25.02.2018.
 */
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class Todo extends RealmObject {
    private String name;
    private String time;
    private int userId;

    public Todo(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public Todo(String name, String time, int userId) {
        this.name = name;
        this.time = time;
        this.userId = userId;
    }

    public Todo() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return name + '\n' + time;
    }

}
