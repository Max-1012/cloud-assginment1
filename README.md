# CH 4 클라우드_아키텍쳐 설계 & 배포

## LV 0 - 요금 폭탄 방지 AWS Budget 설정
<img src="image/lv0_budget(1).png">
<img src ="image/lv0_budget(2).png">

## Lv 1 - 네트워크 구축 및 핵심 기능 배포
EC2 Public IP : 43.202.50.2
### 실행 사진
<img src="image/lv1_execute_on_EC2.png">

```GET http://43.202.50.2:8080/actuator/health```
<img src="image/lv1_Actuator_health.png">

## Lv 2 - DB 분리 및 보안 연결하기

### RDS 구축 및 보안 그룹 체이닝
<img src="image/lv2_RDS_sg.png">

### EC2 보안 그룹
<img src="image/lv2_EC2_sg.png">

### 파라미터 스토어 설정
<img src="image/lv2_parameterStore.png">

### Actuator Info 확장
<img src="image/lv2_Actuator_info.png">

URL : http://43.202.50.2:8080/actuator/info

## Lv 3 - 프로필 사진 기능 추가와 권한 관리
### s3 접근 권한을 가진 IAM Role
IAM ROLE : EC2ParameterStoreAccess
<img src="image/lv3_IAM_ROLE.png">
역할에 부여된 Policy
- AmazonSSMReadOnlyAccess
- Cloud_Assignment_S3_Upload_Get_Policy
<img src="image/lv3_S3_Access_Policy.png">

### EC2에 IAM Role 연결 확인
<img src="image/EC2_IAM_ROLE_Assginment_Check.png">

### API 요구사항 확인
```POST /api/members/{id}/profile-image```
<img src="image/lv3_Post_profile_image.png">

```GET /api/members/{id}/profile-image```

<img src="image/lv3_GET_Profile_iimage.png">

Presigned URL 클릭 시 요쳥 결과

<img src="image/lv3_Presigned_URL_Image.png">

