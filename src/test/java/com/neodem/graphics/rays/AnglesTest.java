package com.neodem.graphics.rays;

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
}
