package com.ikue.japanesedictionary;

import com.ikue.japanesedictionary.utils.SearchUtils;

import org.junit.Assert;
import org.junit.Test;

import static com.ikue.japanesedictionary.utils.SearchTypes.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SearchUtilsUnitTest {
    @Test
    public void testGetSearchType_english() {
        assertEquals(SearchUtils.getSearchType("blast"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("shot"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("parents"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("wasp"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("plastic"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("book"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("library"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("computer"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("speaker"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("lamp"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("deodorant"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("emulator"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("monitor"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("mug"), ENGLISH);
        assertEquals(SearchUtils.getSearchType("calender"), ENGLISH);

        // The following entries will be converted to Romaji
        assertFalse(SearchUtils.getSearchType("home") == ENGLISH);
        assertFalse(SearchUtils.getSearchType("date") == ENGLISH);
        assertFalse(SearchUtils.getSearchType("banana") == ENGLISH);
        assertFalse(SearchUtils.getSearchType("woman") == ENGLISH);
        assertFalse(SearchUtils.getSearchType("man") == ENGLISH);
        assertFalse(SearchUtils.getSearchType("penguin") == ENGLISH);
        assertFalse(SearchUtils.getSearchType("tea") == ENGLISH);
    }

    @Test
    public void testGetSearchType_romaji() {
        assertEquals(SearchUtils.getSearchType("tabemono"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("honmono"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("date"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("home"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("toshokan"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("iku"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("hontou"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("ru-ku"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("ikue"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("enryo"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("watashi"), ROMAJI);
        assertEquals(SearchUtils.getSearchType("kyanseru"), ROMAJI);
    }

    @Test
    public void testGetSearchType_kana() {
        assertEquals(SearchUtils.getSearchType("たべもの"), KANA);
        assertEquals(SearchUtils.getSearchType("ほんもの"), KANA);
        assertEquals(SearchUtils.getSearchType("だて"), KANA);
        assertEquals(SearchUtils.getSearchType("ほめ"), KANA);
        assertEquals(SearchUtils.getSearchType("としょかん"), KANA);
        assertEquals(SearchUtils.getSearchType("いく"), KANA);
        assertEquals(SearchUtils.getSearchType("ほんとう"), KANA);
        assertEquals(SearchUtils.getSearchType("るーく"), KANA);
        assertEquals(SearchUtils.getSearchType("いくえ"), KANA);
        assertEquals(SearchUtils.getSearchType("えんりょ"), KANA);
        assertEquals(SearchUtils.getSearchType("わたし"), KANA);
        assertEquals(SearchUtils.getSearchType("きゃんせる"), KANA);
        assertEquals(SearchUtils.getSearchType("ケーキ"), KANA);
        assertEquals(SearchUtils.getSearchType("ダンス"), KANA);
        assertEquals(SearchUtils.getSearchType("ポケモン"), KANA);
        assertEquals(SearchUtils.getSearchType("バナナ"), KANA);
        assertEquals(SearchUtils.getSearchType("モテる"), KANA);
        assertEquals(SearchUtils.getSearchType("サボる"), KANA);
        assertEquals(SearchUtils.getSearchType("モルモット"), KANA);

        assertFalse(SearchUtils.getSearchType("食べ物") == KANA);
        assertFalse(SearchUtils.getSearchType("本物") == KANA);
        assertFalse(SearchUtils.getSearchType("行く") == KANA);
        assertFalse(SearchUtils.getSearchType("あああああああ行") == KANA);
        assertFalse(SearchUtils.getSearchType("ああああ食べるあああ") == KANA);
    }

    @Test
    public void testGetSearchType_kanji() {
        assertEquals(SearchUtils.getSearchType("食べ物"), KANJI);
        assertEquals(SearchUtils.getSearchType("本当"), KANJI);
        assertEquals(SearchUtils.getSearchType("伊達"), KANJI);
        assertEquals(SearchUtils.getSearchType("褒め"), KANJI);
        assertEquals(SearchUtils.getSearchType("図書館"), KANJI);
        assertEquals(SearchUtils.getSearchType("行く"), KANJI);
        assertEquals(SearchUtils.getSearchType("本物"), KANJI);
        assertEquals(SearchUtils.getSearchType("幾重"), KANJI);
        assertEquals(SearchUtils.getSearchType("育英"), KANJI);
        assertEquals(SearchUtils.getSearchType("遠慮"), KANJI);
        assertEquals(SearchUtils.getSearchType("私"), KANJI);
        assertEquals(SearchUtils.getSearchType("来る"), KANJI);
    }

    @Test
    public void testIsStringAllUppercase_lowercase() {
        assertFalse(SearchUtils.isStringAllUppercase("lowercase"));
        assertFalse(SearchUtils.isStringAllUppercase("this is a test string"));
        assertFalse(SearchUtils.isStringAllUppercase("tabemono"));
    }

    @Test
    public void testIsStringAllUppercase_mixedcase() {
        assertFalse(SearchUtils.isStringAllUppercase("MiXeDCaSe"));
        assertFalse(SearchUtils.isStringAllUppercase("this is A TEST string"));
        assertFalse(SearchUtils.isStringAllUppercase("tabeMONO"));
        assertFalse(SearchUtils.isStringAllUppercase("MOTEru"));
    }

    @Test
    public void testIsStringAllUppercase_uppercase() {
        assertTrue(SearchUtils.isStringAllUppercase("UPPERCASE"));
        assertTrue(SearchUtils.isStringAllUppercase("THIS IS A TEST STRING"));
        assertTrue(SearchUtils.isStringAllUppercase("TABEMONO"));
        assertTrue(SearchUtils.isStringAllUppercase("MOTERU"));
        assertTrue(SearchUtils.isStringAllUppercase("LOUD NOISES"));
    }

    @Test
    public void testContainsWildcards_withWildcards() {
        Assert.assertTrue(SearchUtils.containsWildcards("*test"));
        Assert.assertTrue(SearchUtils.containsWildcards("*test*"));
        Assert.assertTrue(SearchUtils.containsWildcards("t*est"));
        Assert.assertTrue(SearchUtils.containsWildcards("test*"));
        Assert.assertTrue(SearchUtils.containsWildcards("test?"));
        Assert.assertTrue(SearchUtils.containsWildcards("?test?"));
        Assert.assertTrue(SearchUtils.containsWildcards("?test"));
        Assert.assertTrue(SearchUtils.containsWildcards("te?st"));
        Assert.assertTrue(SearchUtils.containsWildcards("*te?st*"));
        Assert.assertTrue(SearchUtils.containsWildcards("*te?st"));
    }

    @Test
    public void testContainsWildcards_withoutWildcards() {
        Assert.assertFalse(SearchUtils.containsWildcards("test"));
        Assert.assertFalse(SearchUtils.containsWildcards("testing for wildcards"));
        Assert.assertFalse(SearchUtils.containsWildcards("library"));
        Assert.assertFalse(SearchUtils.containsWildcards("commas, are, cool"));
        Assert.assertFalse(SearchUtils.containsWildcards("%"));
        Assert.assertFalse(SearchUtils.containsWildcards("_"));
        Assert.assertFalse(SearchUtils.containsWildcards("home"));
        Assert.assertFalse(SearchUtils.containsWildcards("manchester!"));
    }

    @Test
    public void testRemoveWildcards() {
        Assert.assertEquals(SearchUtils.removeWildcards("*test"), "test");
        Assert.assertEquals(SearchUtils.removeWildcards("*test*"), "test");
        Assert.assertEquals(SearchUtils.removeWildcards("t*est"), "test");
        Assert.assertEquals(SearchUtils.removeWildcards("test*"), "test");
        Assert.assertEquals(SearchUtils.removeWildcards("test?"), "test");
        Assert.assertEquals(SearchUtils.removeWildcards("?test?"), "test");
        Assert.assertEquals(SearchUtils.removeWildcards("?test"), "test");
        Assert.assertEquals(SearchUtils.removeWildcards("te?st"), "test");
        Assert.assertEquals(SearchUtils.removeWildcards("*te?st*"), "test");
        Assert.assertEquals(SearchUtils.removeWildcards("*te?st"), "test");
    }

    @Test
    public void testGetTrueWildcardString() {
        Assert.assertEquals(SearchUtils.getTrueWildcardString("*test"), "%test");
        Assert.assertEquals(SearchUtils.getTrueWildcardString("*test*"), "%test%");
        Assert.assertEquals(SearchUtils.getTrueWildcardString("t*est"), "t%est");
        Assert.assertEquals(SearchUtils.getTrueWildcardString("test*"), "test%");
        Assert.assertEquals(SearchUtils.getTrueWildcardString("test?"), "test_");
        Assert.assertEquals(SearchUtils.getTrueWildcardString("?test?"), "_test_");
        Assert.assertEquals(SearchUtils.getTrueWildcardString("?test"), "_test");
        Assert.assertEquals(SearchUtils.getTrueWildcardString("te?st"), "te_st");
        Assert.assertEquals(SearchUtils.getTrueWildcardString("*te?st*"), "%te_st%");
        Assert.assertEquals(SearchUtils.getTrueWildcardString("*te?st"), "%te_st");
    }
}
