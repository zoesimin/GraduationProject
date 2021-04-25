package application.repository;

import application.model.*;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component //标注一个类为Spring容器的Bean，（把普通pojo实例化到spring容器中，相当于配置文件中的<bean id="" class=""/>）
public interface TeacherRepository extends Neo4jRepository<Teacher, Long> { //pom中导入neo4j依赖，才能使用Neo4jRepository
    //通过名字来找老师节点
    @Query("MATCH (n:Teacher) WHERE n.name = ({name}) RETURN n")
    Teacher findByName(@Param("name") String name);

    //通过teacher_id来找此老师所教的全部课程
    @Query("start teacher = node({teacher_id}) match (teacher)-[:TEACH_IN]->(courses) return courses")
    Course[] findCourses(@Param("teacher_id") long teacher_id);

}
//查询语言包含以下几个明显的部分：
//START：在图中的开始点，通过元素的ID或所以查找获得。
//MATCH：图形的匹配模式，束缚于开始点。
//WHERE：过滤条件。
//RETURN：返回所需要的。