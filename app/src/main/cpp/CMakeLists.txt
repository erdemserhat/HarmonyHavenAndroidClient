# Set the minimum CMake version required for this project.
cmake_minimum_required(VERSION 3.22.1)

# Define the project name
project("harmonyhaven")

# Create and name the library (shared), providing the relative paths to its source code.
add_library(harmonyhaven SHARED
        # List the C++ source file
        harmonyhaven.cpp)

# Link Android system libraries and log library to your native library
target_link_libraries(harmonyhaven
        android
        log)
