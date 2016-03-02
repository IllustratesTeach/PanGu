PROJECT(hall-image4j)
CMAKE_MINIMUM_REQUIRED(VERSION 2.8)

SET(NIRVANA_VERSION_MAJOR 8)
SET(NIRVANA_VERSION_MINOR 15)
SET(NIRVANA_VERSION_PATCH 3)
SET(NIRVANA_VERSION_PATCH_2 17)

INCLUDE(CheckIncludeFiles)


# java jni support
FIND_PACKAGE(Java REQUIRED)
FIND_PACKAGE(JNI REQUIRED)
#INCLUDE(UseJava)
INCLUDE_DIRECTORIES(${JAVA_INCLUDE_PATH})
INCLUDE_DIRECTORIES(${JAVA_INCLUDE_PATH2})

#needed by wsq compress
ADD_DEFINITIONS("-D__NBISLE__")


#define some variable
IF(WIN32)
    IF(NOT DEFINED ARCH)
        MESSAGE(FATAL_ERROR "ARCH not defined,pls use -DARCH=(w32|w64) to define")
    ENDIF()
    IF(NOT DEFINED KERNEL_DIR)
        MESSAGE(FATAL_ERROR "KERNEL_DIR not defined,pls use -DKERNEL_DIR=(w32|w64) to define")
    ENDIF()
ENDIF()
IF(${CMAKE_SYSTEM_NAME} MATCHES "Linux")
    ADD_DEFINITIONS(-DLINUX)
ENDIF()
INCLUDE_DIRECTORIES(${KERNEL_DIR}/include)


FILE(GLOB_RECURSE HALL_IMAGE_JNI_SOURCES ${PROJECT_SOURCE_DIR}/src/*)
ADD_LIBRARY(hall-image4j SHARED ${HALL_IMAGE_JNI_SOURCES})


IF(WIN32)

    IF(${ARCH} MATCHES "w64")
        FIND_LIBRARY(GAFISTOOLS
                NAME gafistools64
                PATHS "${KERNEL_DIR}\\build-w64\\src"
                DOC "gafistools64 release library")
        FIND_LIBRARY(TCPRDLL
                NAME tcprdll64
                PATHS "${KERNEL_DIR}\\build-w64\\src"
                DOC "tcprdll  release library")
    TARGET_LINK_LIBRARIES(hall-image4j ${TCPRDLL} ${GAFISTOOLS})

    ELSE()
        ADD_DEFINITIONS(-DDISABLE_GFS)
        FIND_LIBRARY(GAFISTOOLS
                NAME gafistools
                PATHS "${KERNEL_DIR}\\build-w32\\src"
                DOC "gafistools release library")
        FIND_LIBRARY(TCPRDLL
                NAME tcprdll
                PATHS "${KERNEL_DIR}\\build-w32\\src"
                DOC "tcprdll release library")
#    TARGET_LINK_LIBRARIES(hall-image4j ${TCPRDLL} ${GAFISTOOLS})
    ENDIF()
ELSE()
    TARGET_LINK_LIBRARIES(hall-image4j nirvana-kernel)
ENDIF()




# build unit test
# you must config gtest directory using GTEST_ROOT variable.
OPTION(ENABLE_TEST "enable test" OFF)
if(ENABLE_TEST)
    ENABLE_TESTING()
    FIND_PACKAGE(GTest REQUIRED)
    INCLUDE_DIRECTORIES(${GTEST_INCLUDE_DIRS})
    ADD_SUBDIRECTORY(test)
ENDIF()


