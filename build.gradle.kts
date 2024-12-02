// "PROJECT LEVEL" build.gradle.kts file
// false means -> do not apply this for all modules
// true means -> apply all modules to this plugin
plugins {
   alias(libs.plugins.android.library) apply false
   alias(libs.plugins.android.application) apply false
   alias(libs.plugins.kotlin.android) apply false
   alias(libs.plugins.kotlin.jvm)
   alias(libs.plugins.kotlin.serialization)
   alias(libs.plugins.hilt) apply false
   alias(libs.plugins.google.services) apply false
}
