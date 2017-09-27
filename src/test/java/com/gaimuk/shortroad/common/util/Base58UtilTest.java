package com.gaimuk.shortroad.common.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class Base58UtilTest {

    @Spy
    private Base58Util base58Util;

    @Test
    public void testEncodeNormal() {
        assertThat(base58Util.encode(123456l)).isEqualTo("dhZ");
        assertThat(base58Util.encode(995522014569l)).isEqualTo("T9jsYTz");
    }

    @Test
    public void testEncodeEdgeCases() {
        assertThat(base58Util.encode(0l)).isNull();
        assertThat(base58Util.encode(Long.MAX_VALUE)).isEqualTo("NQm6nKp8qDC");
    }

    @Test
    public void testDecodeNormal() {
        System.out.println(base58Util.decode("dhZ"));
        assertThat(base58Util.decode("dhZ")).isEqualTo(123456l);
        assertThat(base58Util.decode("T9jsYTz")).isEqualTo(995522014569l);
    }

    @Test
    public void testDeocdeEmptyOrNull() {
        assertThat(base58Util.decode("")).isNull();
        assertThat(base58Util.decode(null)).isNull();
    }

}
