package application.service;

import application.controller.json_model.NodeValue;
import application.model.*;
import application.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NodeService {
    @Autowired
    private NodeRepository nodeRepository;
    @Autowired
    private AssignmentJudgmentRepository assignmentJudgmentRepository;
    @Autowired
    private AssignmentMultipleRepository assignmentMultipleRepository;
    @Autowired
    private AssignmentShortRepository assignmentShortRepository;

    public Node findByNodeId(String course_mindmap, String nodeId) {
        return nodeRepository.findByNodeId(course_mindmap, nodeId);
    }

    public Courseware[] findCoursewares(long id) {
        return nodeRepository.findCoursewares(id);
    }

    public Material[] findMaterials(long id) {
        return nodeRepository.findMaterials(id);
    }

    public Link[] findLinks(long id) {
        return nodeRepository.findLinks(id);
    }

    public AssignmentMultiple[] findAssignmentMultiple(long id) {
        return nodeRepository.findAssignmentMultiple(id);
    }

    public AssignmentJudgment[] findAssignmentJudgements(long id) {
        return nodeRepository.findAssignmentJudgments(id);
    }

    public AssignmentShort[] findAssignmentShort(long id) {
        return nodeRepository.findAssignmentShort(id);
    }

    public void delete(Node node) {
        nodeRepository.delete(node);
    }

    public void save(Node node) {
        nodeRepository.save(node);
    }

    public Node[] findChildren(long id ){
        return nodeRepository.findChildren(id);
    }

    public Note[] getNotes(long id){
        return nodeRepository.findNotes(id);
    }

    public Node getNodeByLongId(long id) {
        return nodeRepository.findByNodeLongId(id);
    }

    /**
     * 获得单个节点的w1值
     * @param nid 节点id
     * @param sid 学生id
     * @return 单个节点的w1值
     */
    public NodeValue getSingleNodeValue(long nid, long sid) {
        return null;
    }

    public void updateNetwork() {
        // 1. 获取所有节点
        List<Node> nodeList = (List<Node>) nodeRepository.findAll();

        // 2. 遍历所有节点
        for (Node node: nodeList) {

            // 3. 生成id
            String assignmentId = node.getCourse_mindmap() + " " + node.getId();

            // 4. 根据id找到三种题目
            List<AssignmentMultiple> assignmentMultipleList = assignmentMultipleRepository.findByMultiId(assignmentId);
            List<AssignmentJudgment> assignmentJudgmentList = assignmentJudgmentRepository.findByJudge_id(assignmentId);
            List<AssignmentShort> assignmentShortList = assignmentShortRepository.findByShortId(assignmentId);

            if (node.getId().equals("7a6440a66b671c1e")) {
                System.out.println(node.getTopic());
                System.out.println(assignmentMultipleList.size() + "  " +  assignmentJudgmentList.size() +
                "  "  +  assignmentShortList.size());
            }

            // 5.添加关系
            for (AssignmentMultiple multiple: assignmentMultipleList) {
                node.setAssignmentMultiple(multiple);
            }
            for (AssignmentJudgment judgment: assignmentJudgmentList) {
                node.setAssignmentJudgments(judgment);
            }
            for (AssignmentShort assignmentShort: assignmentShortList) {
                node.setAssignmentShorts(assignmentShort);
            }
            nodeRepository.save(node);
        }
    }
    //    public  List<NodeValue> getNodeValue(String mindmap_id) {
//        List<NodeValue> nodeValueList = new ArrayList<>();
//        //获得mindmap
//        Mindmap tempMindmap = mindmapService.findByMindmapId(mindmap_id);
//
//        Node root_node = mindmapService.findRootNode(tempMindmap.getId());
//        if (root_node == null)
//            return nodeValueList;
//
//        //深度遍历
//        Queue<Node> nodes = new LinkedList<>();
//        Queue<Node> nodesChildren = new LinkedList<>();
//        nodes.add(root_node);
//
//        while (!nodes.isEmpty() || !nodesChildren.isEmpty()) {
//
//            if (nodes.isEmpty()) {
//                nodes = nodesChildren;
//                nodesChildren = new LinkedList<>();
//            }
//            Node thisNode = nodes.peek();
//
//            //获得答题人数
//            int number = 0;
//            int correctNumber = 0;
//
//            int tmpScore = 0;
//            int tmpStudentScore = 0;
//
//            // 每个node创建一个nodeValue
//            NodeValue nodeValue = new NodeValue();
//            nodeValue.setNode_id(thisNode.getId());
//            nodeValue.setNid(thisNode.getLong_id());
//            nodeValue.setNode_topic(thisNode.getTopic());
//
//
//            // 选择题回答情况统计
//            AssignmentMultiple[] multiples = nodeService.findAssignmentMultiple(thisNode.getLong_id());
//            for (AssignmentMultiple mul : multiples) {
//                number = Integer.parseInt(mul.getNumber());
//                correctNumber = Integer.parseInt(mul.getCorrect_number());
//
//                tmpScore += number * mul.getValue();
//                tmpStudentScore += correctNumber * mul.getValue();
//            }
//
//            // 判断题回答情况统计
//            AssignmentJudgment[] judgments = nodeService.findAssignmentJudgements(thisNode.getLong_id());
//            for (AssignmentJudgment judgment : judgments) {
//                number = Integer.parseInt(judgment.getNumber());
//                correctNumber = Integer.parseInt(judgment.getCorrect_number());
//
//                tmpScore += number * judgment.getValue();
//                tmpStudentScore += correctNumber * judgment.getValue();
//            }
//
//            nodeValue.setScore(tmpScore);
//            nodeValue.setStudentScore(tmpStudentScore);
//            nodeValue.setValue(tmpScore == 0 ? 0.0 : (double)tmpStudentScore/tmpScore);
//
//            //加入到nodesValueList中
//            nodeValueList.add(nodeValue);
//
//            Collections.addAll(nodesChildren, nodeService.findChildren(thisNode.getLong_id()));
////
////            for (Node child : nodeService.findChildren(thisNode.getLong_id())) {
////                nodesChildren.add(child);
////            }
////
//            //移除
//            nodes.remove();
//        }
//
//        return nodeValueList;
//    }

    //改:删除发布的选择题
    public boolean deleteMultiple(String course_id,String mindmap_id,String node_id,Long assignmentLongId) {
        //找到node
        String course_mindmap = course_id + " " + mindmap_id;
        Node node = nodeRepository.findByNodeId(course_mindmap,node_id);

        if(node == null)
            return false;
        if(assignmentLongId == null)
            return false;
//        测试的时候打印相应信息
//        System.out.println(node.toString());

        //看该节点所有选择题中是否有该选择题
        AssignmentMultiple[] assignmentMultiples = nodeRepository.findAssignmentMultiple(node.getLong_id());
        System.out.println(assignmentMultiples.length);//有些节点和选择题之间没有成功插入关系,所以没插入成功的会length为0
        if(assignmentMultiples.length!=0){ //跟它有关系的节点数
            for(AssignmentMultiple a:assignmentMultiples){
                if(a.getId().equals(assignmentLongId)){
                    System.out.println("删除的选择题id:"+a.getId());
                    //通过assignmentLongId删除HAS_ASSIGNMENT_MULTI关系
                    nodeRepository.deleteNodeToMultiple(node.getLong_id(),assignmentLongId);
                    //删除该选择题
                    assignmentMultipleRepository.deleteAssignmentMultipleById(assignmentLongId);
                    return true;
                }
            }
        }else{
            AssignmentMultiple assignmentMultiple =  assignmentMultipleRepository.getAssignmentMultipleById(assignmentLongId);
            if(assignmentMultiple == null)
                return false;
            //删除该选择题
            assignmentMultipleRepository.deleteAssignmentMultipleById(assignmentLongId);
            System.out.println("删除的选择题id:"+assignmentLongId);
            return true;
        }
        return false;
    }

    //改:删除发布的判断题
    public boolean deleteJudgment(String course_id, String mindmap_id, String node_id, Long assignmentLongId) {
        //找到node
        String course_mindmap = course_id + " " + mindmap_id;
        Node node = nodeRepository.findByNodeId(course_mindmap,node_id);

        if(node == null)
            return false;
        if(assignmentLongId == null)
            return false;

//        测试的时候打印相应信息
//        System.out.println(node.toString());

        //看该节点所有判断题中是否有该判断题
        AssignmentJudgment[] assignmentJudgments = nodeRepository.findAssignmentJudgments(node.getLong_id());
        System.out.println(assignmentJudgments.length);
        if(assignmentJudgments.length != 0){
            for(AssignmentJudgment a:assignmentJudgments){
                if(a.getId().equals(assignmentLongId)){
                    System.out.println("删除的判断题id:"+a.getId());
                    //通过assignmentLongId删除HAS_ASSIGNMENT_JUDGMENT关系
                    nodeRepository.deleteNodeToJudgement(node.getLong_id(),assignmentLongId);
                    //再删除该判断题
                    assignmentJudgmentRepository.deleteAssignmentJudgementById(assignmentLongId);
                    return true;
                }
            }
        }else {
            AssignmentJudgment assignmentJudgment = assignmentJudgmentRepository.getAssignmentJudgmentById(assignmentLongId);
            if(assignmentJudgment == null)
                return false;
            //再删除该判断题
            assignmentJudgmentRepository.deleteAssignmentJudgementById(assignmentLongId);
            System.out.println("删除的判断题id:"+assignmentLongId);
            return true;
        }
        return false;
    }

    //改:删除发布的简答题
    public boolean deleteShort(String course_id, String mindmap_id, String node_id, Long assignmentLongId) {
        //找到node
        String course_mindmap = course_id + " " + mindmap_id;
        Node node = nodeRepository.findByNodeId(course_mindmap,node_id);

        if(node == null)
            return false;
        if(assignmentLongId == null)
            return false;
//        测试的时候打印相应信息
//        System.out.println(node.toString());

        //看该节点所有简答题中是否有该简答题
        AssignmentShort[] assignmentShorts = nodeRepository.findAssignmentShort(node.getLong_id());
        System.out.println(assignmentShorts.length);
        if(assignmentShorts.length != 0){
            for(AssignmentShort a:assignmentShorts){
                if(a.getId().equals(assignmentLongId)){
                    System.out.println("删除的简答题id:"+a.getId());
                    //通过assignmentLongId删除HAS_ASSIGNMENT_SHORT关系
                    nodeRepository.deleteNodeToShort(node.getLong_id(),assignmentLongId);
                    //删除节点
                    assignmentShortRepository.deleteAssignmentShortById(assignmentLongId);
                    return true;
                }
            }
        }else {
            AssignmentShort assignmentShort = assignmentShortRepository.getAssignmentShortById(assignmentLongId);
            if(assignmentShort == null)
                return false;
            //再删除该简答题
            assignmentShortRepository.deleteAssignmentShortById(assignmentLongId);
            System.out.println("删除的简答题id:"+assignmentLongId);
            return true;
        }
        return false;
    }
}
