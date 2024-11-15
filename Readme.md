# Architecture

## Version2

![ttokliparc4.png](https://github.com/ttoklip/BackEnd/blob/develop/introduce/ttokliparc4.png?raw=true)

## Version1 - 현재 미사용

![ttokliparc3.png](https://raw.githubusercontent.com/ttoklip/BackEnd/main/introduce/ttokliparc3.png)

## Project Folder Structure

```plaintext
📁 ttoklip
│
├── 📁nginx
├── 📁ttoklip-api
    ├── security
    ├── MDC Logging
├── 📁ttoklip-batch
    ├── scheduler
├── 📁ttoklip-common
     ├── aop anotation
     ├── jwt provider
     ├── exception
     ├── AsyncConfig
     ├── JasyptConfig
├── 📁ttoklip-domain
     ├── KafkaProducer
     ├── JPA
     ├── QueryDSL
     ├── Domain ETC..
├── 📁ttoklip-infrastructure
     ├── aws
     ├── redis
     ├── distrbution-lock
     ├── check-bad-word
│── 📁ttoklip-notification
    ├── FCMSerivce
    ├── KafkaConsumer
├── 📁ttoklip-monitoring
    ├── prometheus
    ├── promtail
    ├── 🐙docker-compose.monitoring.yml
├── 🐙docker-compose.dev.yml
├── 🐙docker-compose.prod.yml

```

## Model Diagram

![ttoklipdb.png](https://raw.githubusercontent.com/ttoklip/BackEnd/main/introduce/ttoklipdb.png)

[![android-download](https://github.com/user-attachments/assets/7d6d40d6-b785-4bd2-979e-410a2cfb02ed)](https://play.google.com/store/apps/details?id=com.umc.ttoklip)

![in1.png](https://raw.githubusercontent.com/ttoklip/BackEnd/main/introduce/in1.png)

![in2.png](https://raw.githubusercontent.com/ttoklip/BackEnd/main/introduce/in2.png)

![in3.png](https://raw.githubusercontent.com/ttoklip/BackEnd/main/introduce/in3.png)

![in4.png](https://raw.githubusercontent.com/ttoklip/BackEnd/main/introduce/in4.png)
