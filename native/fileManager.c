#include <jni.h>
#include <windows.h>
#include <wchar.h>
#include <string.h>
#include "FileManager.h"

JNIEXPORT jstring JNICALL Java_org_chris_jangguLang_FileManager_selectFile(JNIEnv *env, jobject obj) {
    OPENFILENAMEW ofn;
    wchar_t szFile[260] = {0};

    ZeroMemory(&ofn, sizeof(ofn));
    ofn.lStructSize = sizeof(ofn);
    ofn.hwndOwner = NULL;
    ofn.lpstrFile = szFile;
    ofn.nMaxFile = sizeof(szFile) / sizeof(wchar_t);
    ofn.lpstrFilter = L"장구 코드\0*.jangguLang\0";
    ofn.nFilterIndex = 1;
    ofn.Flags = OFN_PATHMUSTEXIST | OFN_FILEMUSTEXIST;

    if (GetOpenFileNameW(&ofn) == TRUE) {
        // wchar_t 배열을 UTF-8로 변환하여 반환
        int len = WideCharToMultiByte(CP_UTF8, 0, szFile, -1, NULL, 0, NULL, NULL);
        char *utf8File = (char *)malloc(len);
        WideCharToMultiByte(CP_UTF8, 0, szFile, -1, utf8File, len, NULL, NULL);
        jstring result = (*env)->NewStringUTF(env, utf8File);
        free(utf8File);
        return result;
    } else {
        return NULL;
    }
}

JNIEXPORT jstring JNICALL Java_org_chris_jangguLang_FileManager_saveFile(JNIEnv *env, jobject obj, jstring content, jstring defaultFileName) {
    OPENFILENAMEW ofn;
    wchar_t szFile[260] = {0};

    ZeroMemory(&ofn, sizeof(ofn));
    ofn.lStructSize = sizeof(ofn);
    ofn.hwndOwner = NULL;
    ofn.lpstrFile = szFile;
    ofn.nMaxFile = sizeof(szFile) / sizeof(wchar_t);
    ofn.lpstrFilter = L"장구 코드\0*.jangguLang\0";
    ofn.nFilterIndex = 1;
    ofn.Flags = OFN_OVERWRITEPROMPT;

    // 기본 파일 이름이 null이 아닐 경우 설정
    if (defaultFileName != NULL) {
        const char *utf8DefaultFileName = (*env)->GetStringUTFChars(env, defaultFileName, NULL);
        MultiByteToWideChar(CP_UTF8, 0, utf8DefaultFileName, -1, szFile, sizeof(szFile) / sizeof(wchar_t));
        (*env)->ReleaseStringUTFChars(env, defaultFileName, utf8DefaultFileName);
    }

    // 파일 저장 대화상자 열기
    if (GetSaveFileNameW(&ofn) == TRUE) {
        // UTF-8로 변환된 내용 받기
        const char *utf8Content = (*env)->GetStringUTFChars(env, content, NULL);

        // 파일 이름이 .jangguLang으로 끝나지 않으면 추가
        wchar_t *ext = L".jangguLang";
        size_t fileNameLen = wcslen(szFile); // 파일 이름 길이
        size_t extLen = wcslen(ext); // 확장자 길이
        if (fileNameLen < extLen || wcsncmp(&szFile[fileNameLen - extLen], ext, extLen) != 0) {
            wcscat(szFile, ext);
        }

        // 파일을 유니코드로 변환하여 저장
        HANDLE hFile = CreateFileW(ofn.lpstrFile, GENERIC_WRITE, 0, NULL, CREATE_ALWAYS, FILE_ATTRIBUTE_NORMAL, NULL);
        if (hFile == INVALID_HANDLE_VALUE) {
            (*env)->ReleaseStringUTFChars(env, content, utf8Content);
            return NULL; // 파일 열기 실패
        }

        // 파일에 내용 쓰기
        DWORD written;
        WriteFile(hFile, utf8Content, strlen(utf8Content), &written, NULL);

        // 파일 핸들 닫기
        CloseHandle(hFile);
        (*env)->ReleaseStringUTFChars(env, content, utf8Content);

        // 저장된 파일의 전체 경로를 UTF-8로 변환하여 반환
        int byteCount = WideCharToMultiByte(CP_UTF8, 0, ofn.lpstrFile, -1, NULL, 0, NULL, NULL);
        char *utf8FileName = (char *)malloc(byteCount);
        WideCharToMultiByte(CP_UTF8, 0, ofn.lpstrFile, -1, utf8FileName, byteCount, NULL, NULL);
        jstring result = (*env)->NewStringUTF(env, utf8FileName);
        free(utf8FileName);

        return result; // 저장된 파일의 전체 경로 반환
    } else {
        return NULL; // 대화상자가 취소됨
    }
}

