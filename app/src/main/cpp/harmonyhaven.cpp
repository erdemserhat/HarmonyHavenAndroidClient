#include <jni.h>
#include <string>

// Define a JNI function that returns the API key
extern "C" JNIEXPORT jstring JNICALL
Java_com_erdemserhat_harmonyhaven_MainActivity_getApiKey(JNIEnv *env, jobject /* this */) {
    std::string apiKey = "your_native_api_key_here";  // Your actual API key goes here
    return env->NewStringUTF(apiKey.c_str());
}