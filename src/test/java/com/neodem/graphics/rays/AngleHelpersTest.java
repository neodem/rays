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
    public void intersectVerticalShouldComputeCorrectly() {
        List<Float> yValues = AngleHelpers.intersectVertical(.2f, 22, 2);
        assertThat(yValues).hasSize(2);
        assertThat(yValues.get(0)).isCloseTo(.323221f, Offset.offset(.00001f));
        assertThat(yValues.get(1)).isCloseTo(.72724724f, Offset.offset(.00001f));
    }
}
