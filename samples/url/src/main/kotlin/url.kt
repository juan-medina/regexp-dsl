package com.medina.juan.samples.url

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
            zeroOrMore {
                character()
            }
        }
    }

    val url = "http://google.co.uk?q=foo"
    if (urlCheck.matches(url)) {
        val domain = urlCheck.find(url)?.groups?.get(1)?.value
        println(domain) //"google.co.uk"
    }
}
