package de.hhu.backend.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "de.hhu.backend")
public class ArchTests {
    @ArchTest
    public static final ArchRule classesInRepoShouldEndWithRepo = classes()
            .that().resideInAPackage("..repositories..")
            .should().haveSimpleNameEndingWith("Repository");

    @ArchTest
    public static final ArchRule classesInServiceShouldEndWithService = classes()
            .that().resideInAPackage("..services..")
            .should().haveSimpleNameEndingWith("Service");

    @ArchTest
    public static final ArchRule classesInControllerShouldEndWithController = classes()
            .that().resideInAPackage("..restcontrollers..")
            .should().haveSimpleNameEndingWith("Controller");

    /**
     * Test Layer Structure
     */
    @ArchTest
    public static final ArchRule layerArchitecture = layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller").definedBy("..restcontrollers..")
            .layer("Service").definedBy("..services..")
            .layer("Persistence").definedBy("..repositories..")

            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");

}
