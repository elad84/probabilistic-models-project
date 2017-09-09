package com.idc.computersience.pm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representation of an edge
 *
 * @author eladcohen
 */
@NoArgsConstructor
@Getter
public class Edge {

    private String nodeId1;
    private String nodeId2;
    private String nodeArrowHead;

    Edge(String node1, String node2, String nodeArrowHead){
        this.nodeId1 = node1;
        this.nodeId2 = node2;
        this.nodeArrowHead = nodeArrowHead;
    }

    public static EdgeBuilder newBuilder(){
        return new EdgeBuilder();
    }


    public static class EdgeBuilder{

        private String nodeId1;
        private String nodeId2;
        private String nodeArrowHead;

        public EdgeBuilder setNode1(String nodeId1){
            this.nodeId1 = nodeId1;
            return this;
        }

        public EdgeBuilder setNode2(String nodeId2){
            this.nodeId2 = nodeId2;
            return this;
        }

        public EdgeBuilder setNodeArrowHead(String nodeArrowHead){
            this.nodeArrowHead = nodeArrowHead;
            return this;
        }

        public Edge build(){
//            if(nodeId1 < 1 || nodeId2 < 1){
//                throw new IllegalArgumentException("cannot create edge without 2 nodes, node id must be greater than 0");
//            }
            return new Edge(nodeId1, nodeId2, nodeArrowHead);
        }

    }
}
