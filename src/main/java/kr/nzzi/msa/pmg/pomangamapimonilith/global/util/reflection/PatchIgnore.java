package kr.nzzi.msa.pmg.pomangamapimonilith.global.util.reflection;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PatchIgnore {
}