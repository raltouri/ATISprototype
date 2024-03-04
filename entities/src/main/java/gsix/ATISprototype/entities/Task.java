package gsix.ATISprototype.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name="tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int task_id;
    @Column(name = "requester_id")
    private int requester_id;
    @Column(name = "volunteer_id")
    private int volunteer_id;
    @Column(name = "requested_operation")
    private String requested_operation;

    @Column(name = "time")
    private LocalDateTime time;
    @Column(name = "status")
    private String status; // CHANGE THIS TO ENUM

    private TaskStatus taskStatus;

    public Task() {
    }

    public Task( int requester_id, int volunteer_id, String requested_operation, LocalDateTime time, TaskStatus taskStatus) {
        this.requester_id = requester_id;
        this.volunteer_id = volunteer_id;
        this.requested_operation = requested_operation;
        this.time = time;
        if (taskStatus == TaskStatus.Request){
            this.status = "Request";
        } else if (taskStatus == TaskStatus.Pending) {
            this.status = "Pending";
        }else if(taskStatus == TaskStatus.Done){
            this.status = "Done";
        }
    }

    public int getTask_id() {
        return task_id;
    }


    public int getRequester_id() {
        return requester_id;
    }

    public void setRequester_id(int requester_id) {
        this.requester_id = requester_id;
    }

    public int getVolunteer_id() {
        return volunteer_id;
    }

    public void setVolunteer_id(int volunteer_id) {
        this.volunteer_id = volunteer_id;
    }

    public String getRequested_operation() {
        return requested_operation;
    }

    public void setRequested_operation(String requested_operation) {
        this.requested_operation = requested_operation;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getStatus() {
        /*if (this.status.equals("Request") ){
            return TaskStatus.Request;
        } else if (this.status.equals("Pending") ){
            return TaskStatus.Pending;
        }else{
            return TaskStatus.Done;
        }*/
        return this.status;
    }

    /*public void setStatus(String taskStatus) {
        this.status=taskStatus;
    }*/
    public void setStatus(TaskStatus taskStatus) {
        if (taskStatus == TaskStatus.Request){
            this.status = "Request";
        } else if (taskStatus == TaskStatus.Pending) {
            this.status = "Pending";
        }else if(taskStatus == TaskStatus.Done){
            this.status = "Done";
        }
    }
    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", requester_id=" + requester_id +
                ", volunteer_id=" + volunteer_id +
                ", requested_operation='" + requested_operation + '\'' +
                ", time=" + time +
                ", status=" + status + // Use the String representation for consistency
                '}';
    }

}
