# SocialRobotKM
KM revised

## Environment

- JRE 8 이상
- ROS kinetic
- Ubuntu 14.04 이상



## 실행 방법

- 터미널에서 roscore 실행

  ```shell
  :~$ roscore
  ```

- roscore 실행한 터미널에서 ROS_MASTER_URI=http://########/11311/ 확인

- 해당 URI 를 복사하여 /configuration/RosConfiguration.xml 수정

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <RosConfiguration>
  	<MasterUri>http://########:11311/</MasterUri>
  </RosConfiguration>
  ```

- km.jar 실행

  ```shell
  :~/SocialRobotKM$ java -jar km.jar
  ```



## 로스 메시지 스펙

- rostopic

  - /taskExecution

    ```json
    {
        "header" : {
            "source" : "planning",
            "target" : ["knowledge"],
            "content" : ["social_context"],
            "timestamp" : "1591935547.972"
        },
        "social_context" : {
            "id" : 123,
            "name" : "조건희",
            "isro_social" : [
                "gender" : "",
                "age" : "",
                "appellation" : "",
                "action_sentiment" : "",
                "visit_History" : "",
                "medical_record" : ""
            ],
            "information" : {}
        }
    }
    ```

    

  - /taskCompletion

    ```json
    {
        "header" : {
            "source" : "knowledge",
            "target" : ["planning", "dialog"],
            "content" : ["social_context"],
            "timestamp" : "1591935589.122"
        },
        "social_context" : {
            "id" : 123,
            "name" : "조건희",
            "isro_social" : [
                "gender" : "남성",
                "age" : "성인",
                "appellation" : "연구원",
                "action_sentiment" : "Neutral",
                "visit_History" : "?",
                "medical_record" : "?"
            ],
            "information" : {},
            "result" : "completion"
        }
    }
    ```

    

