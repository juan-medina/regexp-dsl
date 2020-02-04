import com.medina.juan.regexp.dsl.regexp
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class DslTests {
    @Test
    fun `we should have literals`() {
        val reg = regexp {
            literal("foo")
            literal("bar")
        }
        assertEquals("foobar", reg.pattern)
    }

    @Test
    fun `we could create capturing groups`() {
        val reg = regexp {
            group {
                literal("foo")
            }
            group {
                literal("bar")
            }
        }
        assertEquals("(foo)(bar)", reg.pattern)
    }

    @Test
    fun `we could use line`() {
        val reg = regexp {
            line {
                literal("foo")
            }
        }
        assertEquals("^foo$", reg.pattern)
    }

    @Test
    fun `we could match characters`() {
        val reg = regexp {
            character()
            character('@')
        }
        assertEquals(".\\@", reg.pattern)
    }

    @Test
    fun `we could choose one of characters`() {
        val reg = regexp {
            oneCharacterOf {
                character('@')
                character('$')
            }
        }
        assertEquals("[\\@\\$]", reg.pattern)
    }

    @Test
    fun `we could use ranges`() {
        val reg = regexp {
            oneCharacterOf {
                range('a', 'z')
            }
        }
        assertEquals("[a-z]", reg.pattern)
    }

    @Test
    fun `we could match alpha`() {
        val reg = regexp {
            oneCharacterOf {
                alpha()
            }
        }
        assertEquals("[a-zA-Z]", reg.pattern)
    }

    @Test
    fun `we could match alpha numeric`() {
        val reg = regexp {
            oneCharacterOf {
                alphaNumeric()
            }
        }
        assertEquals("[a-zA-Z0-9]", reg.pattern)
    }

    @Test
    fun `we could repeat children`() {
        val reg = regexp {
            repeat(1, 2) {
                character('*')
            }
            repeat(5) {
                character('@')
            }
        }
        assertEquals("\\*{1,2}\\@{5,}", reg.pattern)
    }

    @Test
    fun `we could use maybe`() {
        val reg = regexp {
            literal("child")
            maybe("ren")
        }
        assertEquals("child(?:ren)?", reg.pattern)
    }

    @Test
    fun `we could use maybe with children`() {
        val reg = regexp {
            literal("child")
            maybe {
                literal("ren")
            }
        }
        assertEquals("child(?:ren)?", reg.pattern)
    }

    @Test
    fun `we could get one or more`() {
        val reg = regexp {
            oneOrMore {
                character('a')
            }
        }
        assertEquals("\\a+", reg.pattern)
    }

    @Test
    fun `we could get zero or more`() {
        val reg = regexp {
            zeroOrMore() {
                character('a')
            }
        }
        assertEquals("\\a*", reg.pattern)
    }
}