package com.neodem.graphics.rays;

import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/12/20
 */
public class AngleHelpersTest {

    @Test
    public void intersectHorizontalShouldComputePointsCorrectly() {
        List<FloatingPoint> floatingPoints = AngleHelpers.intersectHorizontal(new FloatingPoint(.2f, .2f), 22, 2);
        assertThat(floatingPoints).hasSize(3);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(.2f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(.2f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(1f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(.523221f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(2f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(.9272472f, Offset.offset(.00001f));
    }

    @Test
    public void intersectHorizontalPureShouldComputePointsCorrectly() {
        List<FloatingPoint> floatingPoints = AngleHelpers.intersectHorizontalPure(32, 2);
        assertThat(floatingPoints).hasSize(3);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(0f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(1f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(.62486935f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(2f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(1.2497387f, Offset.offset(.00001f));
    }

    @Test
    public void intersectVerticalShouldComputePointsCorrectly() {
        List<FloatingPoint> floatingPoints = AngleHelpers.intersectVertical(new FloatingPoint(.2f, .2f), 22, 2);
        assertThat(floatingPoints).hasSize(3);
        assertThat(floatingPoints.get(0).getX()).isCloseTo(.2f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(0).getY()).isCloseTo(.2f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getX()).isCloseTo(2.1800694f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(1).getY()).isCloseTo(1f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getX()).isCloseTo(4.655156f, Offset.offset(.00001f));
        assertThat(floatingPoints.get(2).getY()).isCloseTo(2f, Offset.offset(.00001f));
    }
}
