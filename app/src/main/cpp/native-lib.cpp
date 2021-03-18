#include <jni.h>
#include <string>

using namespace std;

const int ENV_PRODUCTION = 1;
const int ENV_STAGING = 2;

extern "C" {
JNIEXPORT jstring JNICALL
Java_id_andreasdimas_traveloka_main_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

JNIEXPORT jstring JNICALL
Java_id_andreasdimas_traveloka_utils_Const_appUrl(
        JNIEnv *env, jobject, jint envtype) {
    string appProUr;
//    if (envtype == ENV_PRODUCTION) {
//        appProUr = "https://www.thesportsdb.com/";
//    } else if (envtype == ENV_STAGING) {
//        appProUr = "https://www.thesportsdb.com/";
//    } else {
//        appProUr = "https://www.thesportsdb.com/";
//    }


    if (envtype == ENV_PRODUCTION) {
        appProUr = "https://andreasdimas.com/";
    } else if (envtype == ENV_STAGING) {
        appProUr = "https://andreasdimas.com/";
    } else {
        appProUr = "https://andreasdimas.com/";
    }
    return env->NewStringUTF(appProUr.c_str());
}



}

