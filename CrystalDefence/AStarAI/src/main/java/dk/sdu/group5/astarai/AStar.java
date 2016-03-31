package dk.sdu.group5.astarai;

import java.util.*;
import java.util.stream.Collectors;

class AStar {
    static Node aStar(Vec start, Vec goal, List<LineSegment> lineSegments) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        openList.add(new Node(start, null, 0, dist(start, goal)));
        Set<Node> closedList = new HashSet<>();

        while (openList.size() != 0) {
            Node q = openList.poll();
            List<Vec> successors = connections(q, lineSegments);
            for (Vec successor : successors) {
                double g = q.g + dist(successor, q.item);
                double h = dist(successor, goal);
                Node node = new Node(successor, q, g, h);
                if (node.item == goal)
                    return node;
                if (containsCheaper(openList, node))
                    continue;
                if (containsCheaper(closedList, node))
                    continue;
                openList.add(node);
            }
            closedList.add(q);
        }
        return null;
    }

    private static boolean containsCheaper(Collection<Node> nodes, Node node) {
        return nodes.stream().filter(n -> n.item.equals(node.item)).anyMatch(n -> n.f < node.f);
    }

    static private List<Vec> connections(Node q, List<LineSegment> lineSegments) {
        return lineSegments.stream().map(ls -> j(q, ls)).filter(x -> x != null).collect(Collectors.toList());
    }

    static private Vec j(Node q, LineSegment ls) {
        Vec p1 = ls.p;
        Vec p2 = ls.p.plus(ls.d);
        if (q.item.equals(p1)) {
            return p2;
        }
        if (q.item.equals(p2)) {
            return p1;
        }
        return null;
    }

    static private double dist(Vec start, Vec goal) {
        return goal.minus(start).length();
    }
}

class Node {
    final Vec item;
    final Node parent;
    final double g;
    final double h;
    final double f;

    Node(Vec item, Node parent, double g, double h) {
        this.item = item;
        this.parent = parent;
        this.g = g;
        this.h = h;
        f = g+h;
    }
}