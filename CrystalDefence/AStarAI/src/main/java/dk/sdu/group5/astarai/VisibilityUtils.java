package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dk.sdu.group5.astarai.FU.flatMap;
import static dk.sdu.group5.astarai.FU.lift2;

public class VisibilityUtils {
    static List<LineSegment> getVisibilityLines(Vec start, List<Entity> barriers, Vec goal){
        List<Vec> ps = new ArrayList<>();
        ps.add(start);
        ps.add(goal);
        ps.addAll(flatMap(VisibilityUtils::getPoints, barriers));
        List<LineSegment> rays = lift2(LineSegment::new).apply(ps, ps); // rays
        List<LineSegment> segments = flatMap(VisibilityUtils::getLines, barriers);
        List<LineSegment> lineSegmentStream = rays.stream().filter(a -> !a.intersects(segments)).collect(Collectors.toList());
        return lineSegmentStream;
    }
    static List<LineSegment> getLines(Entity e) {
        List<LineSegment> lines = new ArrayList<>(4);
        float a = 0.2f;
        float b = 0.1f;
        Vec vec11 = new Vec(maxX(e) + 0.1, maxY(e));
        Vec vec01 = new Vec(minX(e) + 0.1, maxY(e));
        Vec vec00 = new Vec(minX(e), minY(e));
        Vec vec10 = new Vec(maxX(e), minY(e));
        lines.add(new LineSegment(vec11.plus(-a, -b), vec10.plus(-a, b)));
        lines.add(new LineSegment(vec10.plus(-b, a), vec00.plus(b, a)));
        lines.add(new LineSegment(vec00.plus(a, b), vec01.plus(a, -b)));
        lines.add(new LineSegment(vec01.plus(b, -a), vec11.plus(-b, -a)));
        return lines;
    }

    static List<Vec> getPoints(Entity e) {
        List<Vec> points = new ArrayList<>(4);
        points.add(new Vec(maxX(e) + 0.1, maxY(e)));
        points.add(new Vec(minX(e) + 0.1, maxY(e)));
        points.add(new Vec(minX(e), minY(e)));
        points.add(new Vec(maxX(e), minY(e)));
        return points;
    }

    private static float maxY(Entity e) {
        return e.getCollider().getAABB().getMaxY();
    }

    private static float minY(Entity e) {
        return e.getCollider().getAABB().getMinY();
    }

    private static float minX(Entity e) {
        return e.getCollider().getAABB().getMinX();
    }

    private static float maxX(Entity e) {
        return e.getCollider().getAABB().getMaxX();
    }
}
