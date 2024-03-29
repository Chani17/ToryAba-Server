package inu.thebite.toryaba.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "tb_todo")
public class Todo extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;
    
    @Column(name = "todo_date")
    private LocalDate date;

    @ElementCollection
    @Column(name = "sto_list", nullable = false)
    private List<Long> stoList = new ArrayList<>();

    @Column(name = "teacher", nullable = false)
    private String teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_seq")
    private Student student;


    public static Todo createTodo(Student student, String teacher) {
        Todo todo = new Todo();
        todo.date = LocalDate.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        todo.teacher = teacher;
        todo.student = student;
        return todo;
    }

    public void addTodoList(Long stoId) {
        this.stoList.add(stoId);
    }

    public void updateTodoList(List<Long> stoList, String teacher) {
        this.stoList = stoList;
        this.teacher = teacher;
    }

}
