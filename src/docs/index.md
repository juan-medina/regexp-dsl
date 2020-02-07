# RegExp DSL

Expressive Regular Expressions with a Domain Specific Language written in Kotlin

[![License: Apache2](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://github.com/juan-medina/regexp-dsl/blob/master/LICENSE)
[![Docs](https://img.shields.io/badge/docs-latest-brightgreen.svg)](https://juan-medina.github.io/regexp-dsl/)
[![Build Status](https://travis-ci.com/juan-medina/regexp-dsl.svg?branch=master)](https://travis-ci.com/juan-medina/regexp-dsl)
[![Jitpack](https://jitpack.io/v/juan-medina/regexp-dsl.svg)](https://jitpack.io/#juan-medina/regexp-dsl)

## Info

RegExp DSL is a library that allow the creation an usage of regular expressions, written in Kotlin, via a Domain Specif Language tha aims to be expressive. The overall idea is to make expressions easy to understand, modify and update. 

## Installation

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.juan-medina</groupId>
        <artifactId>regexp-dsl</artifactId>
        <version>0.1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

### Gradle Groovy

```groovy
    repositories {
		maven { url 'https://jitpack.io' }
    }

	dependencies {
        implementation 'com.github.juan-medina:regexp-dsl:0.1.0-SNAPSHOT'
    }
```

### Gradle Kotlin

```kotlin
    repositories {
		maven {
            setUrl("https://jitpack.io")
        }
    }

    dependencies {
        implementation("com.github.juan-medina:regexp-dsl:0.1.0-SNAPSHOT")
    }
```

## Usage

### Generating a RegExp

```kotlin
import com.medina.juan.regexp.dsl.regexp

fun main() {
    val reg = regexp {        
        literal("foo")
        maybe("bar")    
        zeroOrMore{
            character()
        }        
    }

    println(reg.matches("foo"))         // true
    println(reg.matches("fooooo"))      // true
    println(reg.matches("foobar"))      // true
    println(reg.matches("foobarrrr"))   // true
    println(reg.matches("bar"))         // false
} 
```

### Generating a pattern

```kotlin
import com.medina.juan.regexp.dsl.pattern

fun main() {
    val ptn = pattern {        
        literal("foo")
        maybe("bar")    
        zeroOrMore{
            character()
        }        
    }

    println(ptn) // foo(?:bar)?.*     
} 
```

### JSR 380 Validations

```kotlin
class ValidateName : DslValidator({
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
```

## License

```text
    Copyright (C) 2020 Juan Medina

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```
