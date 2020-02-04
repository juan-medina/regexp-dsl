import com.medina.juan.regexp.dsl.regexp
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ComplexTests {
    @Test
    fun `we should find urls`() {
        val urlCheck = regexp {
            line {
                literal("http")
                maybe("s")
                literal("://")
                group {
                    oneOrMore {
                        oneCharacterOf {
                            alphaNumeric()
                            character('-')
                            character('.')
                        }
                    }
                    character('.')
                    repeat(2, 3) {
                        oneCharacterOf {
                            alpha()
                        }
                    }
                }
                zeroOrMore{
                    character()
                }
            }
        }

        data class Case(val url : String, val matches : Boolean, val find : String?)
        val cases = arrayOf(
            Case(url = "http://www.google.com",         matches = true,     find = "www.google.com"),
            Case(url = "https://www.google.com",        matches = true,     find = "www.google.com"),
            Case(url = "http://google.com",             matches = true,     find = "google.com"),
            Case(url = "https://google.com",            matches = true,     find = "google.com"),
            Case(url = "http://google.co.uk",           matches = true,     find = "google.co.uk"),
            Case(url = "https://google.co.uk",          matches = true,     find = "google.co.uk"),
            Case(url = "https://google.co.uk?q=foo",    matches = true,     find = "google.co.uk"),
            Case(url = "https://google.co.uk/foo/bar",  matches = true,     find = "google.co.uk"),
            Case(url = "https://my-123domain.com",      matches = true,     find = "my-123domain.com"),
            Case(url = "https://my-123domain.co.uk/",   matches = true,     find = "my-123domain.co.uk"),
            Case(url = "abcde",                         matches = false,    find = null)
        )

        for (case in cases){
            assertEquals(case.matches, urlCheck.matches(case.url), "not match in $case")
            assertEquals(case.find, urlCheck.find(case.url)?.groups?.get(1)?.value, "not find in $case")
        }
    }
}