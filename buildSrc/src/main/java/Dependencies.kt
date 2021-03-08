import org.gradle.api.JavaVersion

object Config {
    const val application_id = "com.nick_sib.refactoringdevelop"
    const val compile_sdk = 30
    const val compile_version ="30.0.3"
    const val min_sdk = 26
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"
//    //Features
    const val descriptionScreen = ":descriptionScreen"
//    const val historyScreen = ":historyScreen"
}

object Versions {
    //Tools
    const val core = "1.3.2"//ok

    //Design
    const val appcompat = "1.2.0"//ok
    const val material = "1.3.0"//ok
    const val multidex = "1.0.3"//ok
    const val swiperefresh = "1.1.0"
    const val constraint = "2.0.4"

    //Kotlin
    const val stdlib = "1.4.30"//ok

    //Coroutines
    const val coroutines = "1.4.1"//ok

    //Retrofit
    const val retrofit = "2.9.0"//ok
    const val interceptor = "4.9.0"//ok
    const val retrofitAdapterCoroutines = "0.9.2"//ok

    //Koin
    const val koin = "2.0.1"//ok

    //Glide
    const val glide = "4.12.0"//ok

    //Room
    const val room = "2.3.0-beta02"//ok

    //Test
    const val jUnit = "4.13.2"//ok
    const val jTest  = "1.1.2"//ok
    const val runner = "1.2.0"
    const val espressoCore = "3.3.0"//ok
}

object Tools {
    const val core = "androidx.core:core-ktx:${Versions.core}"//ok
}

object Design {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"//ok
    const val material = "com.google.android.material:material:${Versions.material}"//ok
    const val multidex = "com.android.support:multidex:${Versions.multidex}"//ok
    const val swiperefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefresh}"
    const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
}

object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.stdlib}"//ok
}

object Coroutines {
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"//ok
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"//ok
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val logging_interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
    const val adapter_coroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.retrofitAdapterCoroutines}"
}

object Koin {
    const val koin_android = "org.koin:koin-android:${Versions.koin}"//ok
    const val koin_view_model = "org.koin:koin-android-viewmodel:${Versions.koin}"//ok
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val j_test = "androidx.test.ext:junit:${Versions.jTest}"
//    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}