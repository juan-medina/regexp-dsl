import com.medina.juan.regexp.dsl.RegExValidator
import org.junit.jupiter.api.Test
import javax.validation.Constraint
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory
import kotlin.reflect.KClass
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class RegExValidatorTests {

    class ValidateName : RegExValidator({
        literal("foo")
        maybe("bar")
        zeroOrMore {
            character()
        }
    })

    @Constraint(validatedBy = [ValidateName::class])
    annotation class ValidName(
        val message: String = "name is not valid",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<*>> = []
    )

    data class Person(@field:ValidName val name: String)

    @Test
    fun `validations should work`() {
        val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory();
        val validator: Validator = factory.validator

        open class Case(open val name: String, val valid: Boolean)
        data class Invalid(override val name : String) : Case(name, false)
        data class Valid(override val name : String) : Case(name, true)

        val cases = arrayOf(
            Valid("foo"),
            Valid("fooooo"),
            Valid("foobar"),
            Valid("foobarrrr"),
            Invalid("bar")
        )

        for (case in cases) {
            val person = Person(case.name)
            val constrains = validator.validate(person)

            assertNotNull(constrains, "constrains null in $case")
            if (case.valid) {
                assertEquals(0, constrains.size, "has constrains when valid on $case")
            } else {
                assertEquals(1, constrains.size, "has not constrains when invalid on $case")
                assertEquals("name is not valid", constrains.first().message)
            }
        }
    }
}
