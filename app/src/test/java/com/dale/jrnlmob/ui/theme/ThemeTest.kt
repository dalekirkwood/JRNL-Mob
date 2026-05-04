package com.dale.jrnlmob.ui.theme

import org.junit.Assert.assertEquals
import org.junit.Test

class ThemeModeTest {

    @Test
    fun `ThemeMode has three values`() {
        assertEquals(3, ThemeMode.entries.size)
    }

    @Test
    fun `ThemeMode values are LIGHT, DARK, SYSTEM`() {
        assertEquals(ThemeMode.LIGHT, ThemeMode.valueOf("LIGHT"))
        assertEquals(ThemeMode.DARK, ThemeMode.valueOf("DARK"))
        assertEquals(ThemeMode.SYSTEM, ThemeMode.valueOf("SYSTEM"))
    }
}

class DarkFlavorTest {

    @Test
    fun `DarkFlavor has three values`() {
        assertEquals(3, DarkFlavor.entries.size)
    }

    @Test
    fun `DarkFlavor values are FRAPPE, MACCHIATO, MOCHA`() {
        assertEquals(DarkFlavor.FRAPPE, DarkFlavor.valueOf("FRAPPE"))
        assertEquals(DarkFlavor.MACCHIATO, DarkFlavor.valueOf("MACCHIATO"))
        assertEquals(DarkFlavor.MOCHA, DarkFlavor.valueOf("MOCHA"))
    }
}

class CatppuccinColorsTest {

    @Test
    fun `Latte base is light`() {
        val expected = 0xFFEFF1F5L
        assertEquals(expected, CatppuccinLatte.Base.value.toLong())
    }

    @Test
    fun `Mocha base is dark`() {
        val expected = 0xFF1E1E2EL
        assertEquals(expected, CatppuccinMocha.Base.value.toLong())
    }

    @Test
    fun `Frappe mauve is correct`() {
        val expected = 0xFFCA9EE6L
        assertEquals(expected, CatppuccinFrappe.Mauve.value.toLong())
    }

    @Test
    fun `Macchiato blue is correct`() {
        val expected = 0xFF8AADF4L
        assertEquals(expected, CatppuccinMacchiato.Blue.value.toLong())
    }

    @Test
    fun `Latte has all 26 colors`() {
        val fields = CatppuccinLatte::class.java.declaredFields
        assertEquals(26, fields.size)
    }

    @Test
    fun `Mocha has all 26 colors`() {
        val fields = CatppuccinMocha::class.java.declaredFields
        assertEquals(26, fields.size)
    }
}
