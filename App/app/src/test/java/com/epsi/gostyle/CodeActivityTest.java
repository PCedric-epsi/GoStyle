package com.epsi.gostyle;

import com.epsi.gostyle.bean.CodeBean;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CodeActivityTest {

    private static List<CodeBean> codeBeanList;

    @BeforeAll
    @DisplayName("Initialize list before all tests")
    public static void initCodedBeanList(){

        CodeBean codeBean = new CodeBean("TEST_CODE", 10);
        CodeBean codeBean2 = new CodeBean("TEST_CODE_2", 20);
        CodeBean codeBean3 = new CodeBean("TEST_CODE_3", 30);
        CodeBean codeBean4 = new CodeBean("TEST_CODE_4", 40);
        CodeBean codeBean5 = new CodeBean("TEST_CODE_5", 15);
        CodeBean codeBean6 = new CodeBean("TEST_CODE_6", 50);
        CodeBean codeBean7 = new CodeBean("TEST_CODE_7", 80);
        CodeBean codeBean8 = new CodeBean("TEST_CODE_8", 70);
        CodeBean codeBean9 = new CodeBean("TEST_CODE_9", 5);
        CodeBean codeBean10 = new CodeBean("TEST_CODE_10", 8);

        codeBeanList = Arrays.asList(codeBean, codeBean2, codeBean3, codeBean4, codeBean5, codeBean6, codeBean7, codeBean8, codeBean9, codeBean10);
    }

    @Test
    @DisplayName("Check size of list")
    public void getSizeOfList() {
        assertEquals(10, codeBeanList.size());
    }

    @ParameterizedTest(name="{0} is an correct code")
    @ValueSource(strings = { "TEST_CODE", "TEST_CODE_2", "TEST_CODE_3", "TEST_CODE_4", "TEST_CODE_5", "TEST_CODE_6" })
    @DisplayName("Check if code is correct")
    public void getValueOfCode(String code){
        assertTrue(codeBeanList.stream().anyMatch(codeBean -> codeBean.getName().equals(code)));
    }
}