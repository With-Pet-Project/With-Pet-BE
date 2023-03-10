#!/bin/bash

REPOSITORY=/home/ubuntu/app/step1/With-Pet-BE
PROJECT_NAME=With-Pet-BE

echo ">>> Build 파일 복사"

cp $REPOSITORY/deploy/*.jar $REPOSITORY/

echo ">>> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(ps -ef | grep java | grep with-pet | awk '{print $2}')
echo "$CURRENT_PID"

 if [ -z $CURRENT_PID ]; then
echo ">현재 구동중인 어플리케이션이 없으므로 종료하지 않습니다."

else
echo "> kill -9 $CURRENT_PID"
kill -9 $CURRENT_PID
sleep 10
fi

echo ">>> 애플리케이션 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo ">>> JAR NAME : $JAR_NAME"

chmod +x $JAR_NAME

echo ">>> $JAR_NAME 실행"

nohup java -jar /home/ubuntu/app/step1/With-Pet-BE/with-pet-0.0.1-SNAPSHOT.jar &