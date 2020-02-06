package com.medina.juan.regexp.dsl

import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

class DslElement(
    private val before: String = "",
    private val after: String = "",
    private val children: DslElement.() -> Unit = empty
) {
    companion object {
        private val empty = fun DslElement.(): Unit {}
    }

    private var siblings: Array<DslElement> = arrayOf()

    private fun add(
        before: String = "",
        after: String = "",
        children: DslElement.() -> Unit = empty
    ) {
        siblings += DslElement(before, after, children)
    }

    fun build(): String {
        var value = ""
        value += before
        this.apply(children)
        for (element in siblings) {
            value += element.build()
        }
        value += after
        return value
    }

    fun literal(value: String) = add(value)

    fun group(children: DslElement.() -> Unit) = add("(", ")", children)

    fun line(children: DslElement.() -> Unit) = add("^", "$", children)

    fun character() = add(".")

    fun character(value: Char) = add("\\$value")

    fun oneCharacterOf(children: DslElement.() -> Unit) = add("[", "]", children)

    fun range(from: Char, to: Char) = add("$from-", to.toString())

    fun alphaNumeric() {
        range('a', 'z')
        range('A', 'Z')
        range('0', '9')
    }

    fun alpha() {
        range('a', 'z')
        range('A', 'Z')
    }

    fun repeat(from: Int, to: Int = 0, children: DslElement.() -> Unit = empty) {
        if (to == 0) {
            add("", "{$from,}", children)
        } else {
            add("", "{$from,$to}", children)
        }
    }

    fun maybe(literal: String = "") = add("(?:$literal", ")?")

    fun maybe(children: DslElement.() -> Unit = empty) {
        add("(?:", ")?", children)
    }

    fun oneOrMore(children: DslElement.() -> Unit = empty) = add("", "+", children)

    fun zeroOrMore(children: DslElement.() -> Unit = empty) = add("", "*", children)
}

fun pattern(init: DslElement.() -> Unit): String {
    val root = DslElement()
    root.init()
    return root.build()
}

fun regexp(init: DslElement.() -> Unit): Regex {
    return Regex(pattern(init))
}

open class RegExValidator(init: DslElement.() -> Unit) : ConstraintValidator<Annotation, Any> {
    private var pastern = DslElement().apply(init).build()
    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        return Regex(pastern).matches(value.toString())
    }
}