#! /bin/bash

# 1. 启动应用
./gradlew bootRun > /dev/null 2>&1 &
gradle_pid="$!"

# 2. 等待应用启动完毕
echo "Await application startup"
sleep 30
echo "Starting functional test"

# 3. 启动Puppeteer进行测试
cd ./src/functionalTest
npm test

# 4. 关闭应用
kill -9 ${gradle_pid}
