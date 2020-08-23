package com.neodem.rays;

import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/12/20
 */
public class RayTest {

    private static final Logger logger = LoggerFactory.getLogger(RayTest.class);

    @Test
    public void constructorSetsCorrectQuadrant() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 338, 0);
        assertThat(ray.getQuadrant()).isEqualTo(Quadrant.UP_LEFT);
    }

    @Test
    public void intersectHorizontalShouldComputeCorrectPointsCorrectlyUp() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 0, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(7.5f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(13f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(7.5f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(12f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(7.5f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(11f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(7.5f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(10f, Offset.offset(.00001f));
    }

    @Test
    public void intersectHorizontalShouldComputeCorrectPointsCorrectlyUpLeft() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 338, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(7.176779f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(13f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(6.772753f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(12f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(6.3687267f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(11f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(5.9647f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(10f, Offset.offset(.00001f));
    }

    @Test
    public void intersectHorizontalShouldComputeCorrectPointsCorrectlyUpRight() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 22, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(7.823221f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(13f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(8.227247f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(12f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(8.631273f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(11f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(9.035299f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(10f, Offset.offset(.00001f));
    }

    @Test
    public void intersectHorizontalShouldComputeCorrectPointsCorrectlyDown() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 180, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(7.5f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(14f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(7.5f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(15, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(7.5f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(16f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(7.5f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(17f, Offset.offset(.00001f));
    }

    @Test
    public void intersectHorizontalShouldComputeCorrectPointsCorrectlyDownLeft() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 202, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(7.4191947f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(14f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(7.0151687f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(15f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(6.611142f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(16f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(6.207116f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(17f, Offset.offset(.00001f));
    }

    @Test
    public void intersectHorizontalShouldComputeCorrectPointsCorrectlyDownRight() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 158, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(7.5808053f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(14f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(7.9848313f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(15f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(8.388858f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(16f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(8.792884f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(17f, Offset.offset(.00001f));
    }


    ///// intersectWorldVertical

    @Test
    public void intersectWorldVerticalShouldComputeCorrectPointsCorrectlyRight() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 90, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldVertical(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(8f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(13.8f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(9f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(13.8f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(10f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(13.8f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(11f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(13.8f, Offset.offset(.00001f));
    }

    @Test
    public void intersectWorldVerticalShouldComputeCorrectPointsCorrectlyUpRight() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 68, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldVertical(4);
        assertThat(floatingPoints).hasSize(4);
        print(floatingPoints);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(8.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(13.597987f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(9.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(13.193961f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(10.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(12.789934f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(11.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(12.385908f, Offset.offset(.00001f));
    }

    @Test
    public void intersectWorldVerticalShouldComputeCorrectPointsCorrectlyDownRight() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 112, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldVertical(4);
        assertThat(floatingPoints).hasSize(4);
        print(floatingPoints);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(8.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(14.002013f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(9.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(14.406039f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(10.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(14.810066f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(11.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(15.214092f, Offset.offset(.00001f));
    }

    @Test
    public void intersectWorldVerticalShouldComputeCorrectPointsCorrectlyLeft() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 270, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldVertical(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(7f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(13.8f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(6f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(13.8f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(5f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(13.8f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(4f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(13.8f, Offset.offset(.00001f));
    }

    @Test
    public void intersectWorldVerticalShouldComputeCorrectPointsCorrectlyUpLeft() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 292, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldVertical(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(7.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(13.597987f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(6.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(13.193961f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(5.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(12.789934f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(4.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(12.385908f, Offset.offset(.00001f));
    }

    @Test
    public void intersectWorldVerticalShouldComputeCorrectPointsCorrectlyDownLeft() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 248, 0);

        List<FloatingPoint> floatingPoints = ray.intersectWorldVertical(4);
        assertThat(floatingPoints).hasSize(4);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(7.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(14.002013f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(6.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(14.406039f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(5.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(14.810066f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getX()).isCloseTo(4.0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(3).getY()).isCloseTo(15.214092f, Offset.offset(.00001f));
    }

    @Test
    public void intersectHorizontalShouldComputePointsUp() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 0, 0);
        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(4);
    }

    @Test
    public void intersectHorizontalShouldComputePointsDown() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 180, 0);
        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(4);
    }

    @Test
    public void intersectHorizontalShouldComputeNoPointsRight() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 90, 0);
        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(0);
    }

    @Test
    public void intersectHorizontalShouldComputeNoPointsLeft() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 270, 0);
        List<FloatingPoint> floatingPoints = ray.intersectWorldHorizontal(4);
        assertThat(floatingPoints).hasSize(0);
    }

    @Test
    public void getDistanceToClosestHorizontalShouldBCorrectWhenUp() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 338, 0);

        float distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.5f, Orientation.UP);
        assertThat(distanceToFirstHorizontal).isEqualTo(0.5f);

        distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.2f, Orientation.UP);
        assertThat(distanceToFirstHorizontal).isCloseTo(0.2f, Offset.offset(.001f));

        distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.8f, Orientation.UP);
        assertThat(distanceToFirstHorizontal).isCloseTo(0.8f, Offset.offset(.001f));

    }

    @Test
    public void getDistanceToClosestHorizontalShouldBCorrectWhenDown() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 338, 0);

        float distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.5f, Orientation.DOWN);
        assertThat(distanceToFirstHorizontal).isEqualTo(0.5f);

        distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.2f, Orientation.DOWN);
        assertThat(distanceToFirstHorizontal).isCloseTo(0.8f, Offset.offset(.001f));

        distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.8f, Orientation.DOWN);
        assertThat(distanceToFirstHorizontal).isCloseTo(0.2f, Offset.offset(.001f));
    }

    @Test
    public void getDistanceToClosestVerticalShouldBCorrectWhenRight() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 338, 0);

        float distanceToFirstVertical = ray.getDistanceToClosestVertical(7.5f, Orientation.RIGHT);
        assertThat(distanceToFirstVertical).isEqualTo(0.5f);

        distanceToFirstVertical = ray.getDistanceToClosestVertical(7.2f, Orientation.RIGHT);
        assertThat(distanceToFirstVertical).isCloseTo(0.8f, Offset.offset(.001f));

        distanceToFirstVertical = ray.getDistanceToClosestVertical(7.8f, Orientation.RIGHT);
        assertThat(distanceToFirstVertical).isCloseTo(0.2f, Offset.offset(.001f));
    }

    @Test
    public void getDistanceToClosestVerticalShouldBCorrectWhenLeft() {
        FloatingPoint rayOrigin = new FloatingPoint(7.5f, 13.8f);
        Ray ray = new Ray(rayOrigin, 338, 0);

        float distanceToFirstVertical = ray.getDistanceToClosestVertical(7.5f, Orientation.LEFT);
        assertThat(distanceToFirstVertical).isEqualTo(0.5f);

        distanceToFirstVertical = ray.getDistanceToClosestVertical(7.2f, Orientation.LEFT);
        assertThat(distanceToFirstVertical).isCloseTo(0.2f, Offset.offset(.001f));

        distanceToFirstVertical = ray.getDistanceToClosestVertical(7.8f, Orientation.LEFT);
        assertThat(distanceToFirstVertical).isCloseTo(0.8f, Offset.offset(.001f));
    }

    private void print(Collection<FloatingPoint> points) {
        for(FloatingPoint p : points) {
            logger.debug("{}", p);
        }
    }
}
