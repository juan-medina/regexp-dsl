import com.medina.juan.regexp.dsl.pattern
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DslTests {
    @Test
    fun `we should have literals`() {
        val ptn = pattern {
            literal("foo")
            literal("bar")
        }
        assertEquals("foobar", ptn)
    }

    @Test
    fun `we could create capturing groups`() {
        val ptn = pattern {
            group {
                literal("foo")
            }
            group {
                literal("bar")
            }
        }
        assertEquals("(foo)(bar)", ptn)
    }

    @Test
    fun `we could use line`() {
        val ptn = pattern {
            line {
                literal("foo")
            }
        }
        assertEquals("^foo$", ptn)
    }

    @Test
    fun `we could match characters`() {
        val ptn = pattern {
            character()
            character('@')
        }
        assertEquals(".\\@", ptn)
    }

    @Test
    fun `we could choose one of characters`() {
        val ptn = pattern {
            oneCharacterOf {
                character('@')
                character('$')
            }
        }
        assertEquals("[\\@\\$]", ptn)
    }

    @Test
    fun `we could use ranges`() {
        val ptn = pattern {
            oneCharacterOf {
                range('a', 'z')
            }
        }
        assertEquals("[a-z]", ptn)
    }

    @Test
    fun `we could match alpha`() {
        val ptn = pattern {
            oneCharacterOf {
                alpha()
            }
        }
        assertEquals("[a-zA-Z]", ptn)
    }

    @Test
    fun `we could match alpha numeric`() {
        val ptn = pattern {
            oneCharacterOf {
                alphaNumeric()
            }
        }
        assertEquals("[a-zA-Z0-9]", ptn)
    }

    @Test
    fun `we could repeat children`() {
        val ptn = pattern {
            repeat(1, 2) {
                character('*')
            }
            repeat(5) {
                character('@')
            }
        }
        assertEquals("\\*{1,2}\\@{5,}", ptn)
    }

    @Test
    fun `we could use maybe`() {
        val ptn = pattern {
            literal("child")
            maybe("ren")
        }
        assertEquals("child(?:ren)?", ptn)
    }

    @Test
    fun `we could use maybe with children`() {
        val ptn = pattern {
            literal("child")
            maybe {
                literal("ren")
            }
        }
        assertEquals("child(?:ren)?", ptn)
    }

    @Test
    fun `we could get one or more`() {
        val ptn = pattern {
            oneOrMore {
                character('a')
            }
        }
        assertEquals("\\a+", ptn)
    }

    @Test
    fun `we could get zero or more`() {
        val ptn = pattern {
            zeroOrMore() {
                character('a')
            }
        }
        assertEquals("\\a*", ptn)
    }
}
