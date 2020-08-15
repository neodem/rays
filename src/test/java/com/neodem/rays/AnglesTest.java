package com.neodem.rays;

import org.assertj.core.data.Offset;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Vincent Fumo (neodem@gmail.com)
 * Created on 8/12/20
 */
public class AnglesTest {

    @Test
    public void convertLeftAngleShouldWork() {
        float result = Angles.convertLeftAngle(22, 40);
        assertThat(result).isEqualTo(18f);
        result = Angles.convertLeftAngle(22, 180);
        assertThat(result).isEqualTo(158f);
        result = Angles.convertLeftAngle(260, 270);
        assertThat(result).isEqualTo(10f);
        result = Angles.convertLeftAngle(22, 40);
        assertThat(result).isEqualTo(18f);
        result = Angles.convertLeftAngle(0, 10);
        assertThat(result).isEqualTo(10f);
        result = Angles.convertLeftAngle(0, 0);
        assertThat(result).isEqualTo(0f);
        result = Angles.convertLeftAngle(22, 10);
        assertThat(result).isEqualTo(348f);
        result = Angles.convertLeftAngle(360, 10);
        assertThat(result).isEqualTo(10f);
    }

    @Test
    public void convertRightAngleShouldWork() {
        float result = Angles.convertRightAngle(22, 40);
        assertThat(result).isEqualTo(62f);
        result = Angles.convertRightAngle(22, 180);
        assertThat(result).isEqualTo(202f);
        result = Angles.convertRightAngle(260, 270);
        assertThat(result).isEqualTo(170f);
        result = Angles.convertRightAngle(22, 40);
        assertThat(result).isEqualTo(62f);
        result = Angles.convertRightAngle(0, 10);
        assertThat(result).isEqualTo(10f);
        result = Angles.convertRightAngle(0, 0);
        assertThat(result).isEqualTo(0f);
        result = Angles.convertRightAngle(22, 348);
        assertThat(result).isEqualTo(10f);
        result = Angles.convertRightAngle(360, 10);
        assertThat(result).isEqualTo(10f);
    }

    @Test
    public void correctAngleShouldWork() {
        float result = Angles.correctAngle(0);
        assertThat(result).isEqualTo(0f);
        result = Angles.correctAngle(10);
        assertThat(result).isEqualTo(10f);
        result = Angles.correctAngle(359.999f);
        assertThat(result).isEqualTo(359.999f);
        result = Angles.correctAngle(360.23f);
        assertThat(result).isCloseTo(.23f, Offset.offset(.0001f));
        result = Angles.correctAngle(1081f);
        assertThat(result).isEqualTo(1f);

        result = Angles.correctAngle(-5f);
        assertThat(result).isEqualTo(355f);
    }

    @Test
    public void intersectVerticalShouldComputeCorrectly() {
        List<Float> yValues = Angles.intersectVertical(.2f, 22, 4);
        assertThat(yValues).hasSize(4);
        assertThat(yValues.get(0)).isCloseTo(.323221f, Offset.offset(.00001f));
        assertThat(yValues.get(1)).isCloseTo(.72724724f, Offset.offset(.00001f));
        assertThat(yValues.get(2)).isCloseTo(1.1312735f, Offset.offset(.00001f));
        assertThat(yValues.get(3)).isCloseTo(1.5352998f, Offset.offset(.00001f));
    }

    @Test
    public void intersectVerticalShouldComputeCorrectlyWith0x() {
        List<Float> yValues = Angles.intersectVertical(0, 22, 4);
        assertThat(yValues).hasSize(4);
        assertThat(yValues.get(0)).isCloseTo(.40402624f, Offset.offset(.00001f));
        assertThat(yValues.get(1)).isCloseTo(.8080525f, Offset.offset(.00001f));
        assertThat(yValues.get(2)).isCloseTo(1.2120787f, Offset.offset(.00001f));
        assertThat(yValues.get(3)).isCloseTo(1.616105f, Offset.offset(.00001f));
    }

    @Test
    public void intersectHorizontalShouldComputeCorrectly() {
        List<Float> xValues = Angles.intersectHorizontal(.5f, 22, 4);
        assertThat(xValues).hasSize(4);
        assertThat(xValues.get(0)).isCloseTo(1.2375435f, Offset.offset(.00001f));
        assertThat(xValues.get(1)).isCloseTo(3.7126303f, Offset.offset(.00001f));
        assertThat(xValues.get(2)).isCloseTo(6.187717f, Offset.offset(.00001f));
        assertThat(xValues.get(3)).isCloseTo(8.662804f, Offset.offset(.00001f));
    }
}
