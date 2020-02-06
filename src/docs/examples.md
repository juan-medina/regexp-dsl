#Examples

###Check an Url and get the domain
```kotlin
import com.medina.juan.regexp.dsl.regexp

fun main() {
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
    
    if(urlCheck.matches("http://google.co.uk?q=foo")){
        val domain = urlCheck.find(case.url)?.groups?.get(1)?.value
        println(domain) //"google.co.uk"
    }
}
```
