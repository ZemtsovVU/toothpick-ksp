/*
 * Copyright 2022 Baptiste Candellier
 * Copyright 2019 Stephane Nicolas
 * Copyright 2019 Daniel Molinero Reguera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package toothpick.compiler.memberinjector

import org.junit.Test
import toothpick.compiler.common.ToothpickOptions.Companion.VerboseLogging
import toothpick.compiler.compilationAssert
import toothpick.compiler.compilesWithoutError
import toothpick.compiler.javaSource
import toothpick.compiler.ktSource
import toothpick.compiler.processedWith
import toothpick.compiler.that
import toothpick.compiler.withLogContaining
import toothpick.compiler.withOptions

class MemberInjectorOriginatingElementTest {

    @Test
    fun testOriginatingElement_java() {
        val source = javaSource(
            "TestOriginatingElement",
            """
            package test;
            import javax.inject.Inject;
            public class TestOriginatingElement {
              @Inject Foo foo;
              public TestOriginatingElement() {}
            }
            class Foo {}
            """
        )

        compilationAssert()
            .that(source)
            .processedWith(MemberInjectorProcessorProvider())
            .withOptions(VerboseLogging to "true")
            .compilesWithoutError()
            .withLogContaining(
                "test.TestOriginatingElement generated class test.TestOriginatingElement__MemberInjector"
            )
    }

    @Test
    fun testOriginatingElement_kt() {
        val source = ktSource(
            "TestOriginatingElement",
            """
            package test
            import javax.inject.Inject
            class TestOriginatingElement {
              @Inject lateinit var foo: Foo
            }
            class Foo
            """
        )

        compilationAssert()
            .that(source)
            .processedWith(MemberInjectorProcessorProvider())
            .withOptions(VerboseLogging to "true")
            .compilesWithoutError()
            .withLogContaining(
                "test.TestOriginatingElement generated class test.TestOriginatingElement__MemberInjector"
            )
    }
}
