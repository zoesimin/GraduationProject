package application.repository;

import application.model.*;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component //标注一个类为Spring容器的Bean，（把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
public interface TeacherRepository extends Neo4jRepository<Teacher, Long> { //pom中导入neo4j依赖，才能使用Neo4jRepository
    @Query("MATCH (n:Teacher) WHERE n.name = ({name}) RETURN n")
    Teacher findByName(@Param("name") String name);

    @Query("start teacher = node({teacher_id}) match (teacher)-[:TEACH_IN]->(courses) return courses")
    Course[] findCourses(@Param("teacher_id") long teacher_id);

}
