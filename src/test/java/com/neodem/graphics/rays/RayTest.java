package com.neodem.graphics.rays;

import org.assertj.core.data.Offset;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/12/20
 */
public class RayTest {

    private Ray ray;

    @Before
    public void setUp() throws Exception {
        ray = new Ray(new FloatingPoint(7.5f, 13.5f), 32);
    }

    @After
    public void tearDown() throws Exception {
        ray = null;
    }

    @Test
    public void getDistanceToClosestHorizontalShouldBCorrectWhenUp() {
        float distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.5f, Ray.Orientation.UP);
        assertThat(distanceToFirstHorizontal).isEqualTo(0.5f);

        distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.2f, Ray.Orientation.UP);
        assertThat(distanceToFirstHorizontal).isCloseTo(0.2f, Offset.offset(.001f));

        distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.8f, Ray.Orientation.UP);
        assertThat(distanceToFirstHorizontal).isCloseTo(0.8f, Offset.offset(.001f));

    }

    @Test
    public void getDistanceToClosestHorizontalShouldBCorrectWhenDown() {
        float distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.5f, Ray.Orientation.DOWN);
        assertThat(distanceToFirstHorizontal).isEqualTo(0.5f);

        distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.2f, Ray.Orientation.DOWN);
        assertThat(distanceToFirstHorizontal).isCloseTo(0.8f, Offset.offset(.001f));

        distanceToFirstHorizontal = ray.getDistanceToClosestHorizontal(13.8f, Ray.Orientation.DOWN);
        assertThat(distanceToFirstHorizontal).isCloseTo(0.2f, Offset.offset(.001f));
    }

    @Test
    public void getDistanceToClosestVerticalShouldBCorrectWhenRight() {
        float distanceToFirstVertical = ray.getDistanceToClosestVertical(7.5f, Ray.Orientation.RIGHT);
        assertThat(distanceToFirstVertical).isEqualTo(0.5f);

        distanceToFirstVertical = ray.getDistanceToClosestVertical(7.2f, Ray.Orientation.RIGHT);
        assertThat(distanceToFirstVertical).isCloseTo(0.8f, Offset.offset(.001f));

        distanceToFirstVertical = ray.getDistanceToClosestVertical(7.8f, Ray.Orientation.RIGHT);
        assertThat(distanceToFirstVertical).isCloseTo(0.2f, Offset.offset(.001f));
    }

    @Test
    public void getDistanceToClosestVerticalShouldBCorrectWhenLeft() {
        float distanceToFirstVertical = ray.getDistanceToClosestVertical(7.5f, Ray.Orientation.LEFT);
        assertThat(distanceToFirstVertical).isEqualTo(0.5f);

        distanceToFirstVertical = ray.getDistanceToClosestVertical(7.2f,  Ray.Orientation.LEFT);
        assertThat(distanceToFirstVertical).isCloseTo(0.2f, Offset.offset(.001f));

        distanceToFirstVertical = ray.getDistanceToClosestVertical(7.8f,  Ray.Orientation.LEFT);
        assertThat(distanceToFirstVertical).isCloseTo(0.8f, Offset.offset(.001f));
    }
}
