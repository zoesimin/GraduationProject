package application.model;

import org.neo4j.ogm.annotation.*;

import java.util.HashSet;
import java.util.Set;

//将常规类转换为实体类
@NodeEntity(label = "Teacher") //对应于teacher的节点标签
public class Teacher {
    @Id  // 标注用于声明一个实体类的属性映射为数据库的主键列
    @GeneratedValue //标注主键的生成策略，通过strategy 属性指定（默认为auto）
    private Long id;  //id是neo4j自动分配的
    private String name;
    private String password;

    @Relationship(type = "TEACH_IN", direction = Relationship.UNDIRECTED)//neo4j中的无向关系
    private Set<Course> courses;

    public Set<Course> getCourses() {
        return courses;
    }

    public void teachIn(Course course) {
        if (courses == null) {
            courses = new HashSet<>();
        }
        courses.add(course);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
