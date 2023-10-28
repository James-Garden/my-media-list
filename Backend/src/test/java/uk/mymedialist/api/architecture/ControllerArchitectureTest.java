package uk.mymedialist.api.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import jakarta.persistence.Entity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@AnalyzeClasses(packages = "uk.mymedialist.api")
class ControllerArchitectureTest {

  @ArchTest
  static void ControllerMethodsShouldReturnResponseEntities(JavaClasses classes) {
    var rule = methods()
        .that().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
        .should().haveRawReturnType(ResponseEntity.class);

    rule.check(classes);
  }

  @ArchTest
  static void ControllerMethodsShouldNotReturnEntities(JavaClasses classes) {
    var rule = noClasses()
        .that().areAnnotatedWith(RestController.class)
        .should().dependOnClassesThat().areAnnotatedWith(Entity.class);

    rule.check(classes);
  }
}
