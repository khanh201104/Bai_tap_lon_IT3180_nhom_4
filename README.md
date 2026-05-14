# Bluemoon Apartment Management

Phần mềm web quản lý chung cư **Bluemoon**, xây dựng bằng **Java Spring Boot**.  
Project phục vụ bài tập lớn môn **IT3180**, hỗ trợ quản lý nhân khẩu, hộ khẩu, tạm trú/tạm vắng, khoản phí, phương tiện, diện tích căn hộ và thống kê.

---

## 1. Công nghệ sử dụng

- Java 17 / Java 21
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- Spring Security
- Thymeleaf
- MySQL
- Maven
- IntelliJ IDEA
- Bootstrap

---

## 2. Mục tiêu project

Hệ thống hỗ trợ ban quản lý chung cư Bluemoon quản lý các nghiệp vụ chính:

- Quản lý nhân khẩu
- Quản lý hộ khẩu
- Quản lý tạm trú, tạm vắng
- Quản lý khoản phí cố định
- Quản lý phí sinh hoạt hàng tháng
- Quản lý phương tiện
- Quản lý diện tích căn hộ
- Tìm kiếm dữ liệu
- Thống kê, báo cáo

---

## 3. Cấu trúc thư mục project

```txt
bluemoon-apartment-management/
│
├── README.md
├── .gitignore
├── pom.xml
├── mvnw
├── mvnw.cmd
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bluemoon/
│   │   │           └── apartment/
│   │   │               │
│   │   │               ├── ApartmentApplication.java
│   │   │               │
│   │   │               ├── config/
│   │   │               │   └── Cấu hình hệ thống, bảo mật, web config
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   └── Nhận request từ người dùng và trả về giao diện
│   │   │               │
│   │   │               ├── entity/
│   │   │               │   └── Các class ánh xạ với bảng trong database
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   └── Làm việc trực tiếp với database
│   │   │               │
│   │   │               ├── service/
│   │   │               │   └── Khai báo nghiệp vụ chính
│   │   │               │
│   │   │               ├── service/impl/
│   │   │               │   └── Code xử lý nghiệp vụ cụ thể
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   └── Object dùng để truyền dữ liệu giữa form và backend
│   │   │               │
│   │   │               ├── dto/request/
│   │   │               │   └── Dữ liệu nhận từ form/request
│   │   │               │
│   │   │               ├── dto/response/
│   │   │               │   └── Dữ liệu trả ra giao diện
│   │   │               │
│   │   │               ├── mapper/
│   │   │               │   └── Chuyển đổi giữa Entity và DTO
│   │   │               │
│   │   │               ├── exception/
│   │   │               │   └── Xử lý lỗi chung của hệ thống
│   │   │               │
│   │   │               ├── constant/
│   │   │               │   └── Các hằng số như role, trạng thái, loại phí
│   │   │               │
│   │   │               └── util/
│   │   │                   └── Các hàm tiện ích dùng chung
│   │   │
│   │   └── resources/
│   │       ├── application.properties
│   │       │
│   │       ├── templates/
│   │       │   └── Chứa các file giao diện HTML Thymeleaf
│   │       │
│   │       └── static/
│   │           └── Chứa CSS, JS, image
│   │
│   └── test/
│       └── Chứa code test
```

---

## 4. Ý nghĩa các package chính

### `config`

Chứa các class cấu hình hệ thống.

Ví dụ:

```txt
SecurityConfig.java
WebConfig.java
DataInitializer.java
```

Dùng để cấu hình:

- Login/logout
- Phân quyền
- Cho phép truy cập static file
- Khởi tạo dữ liệu mặc định

---

### `controller`

Chứa các controller nhận request từ trình duyệt.

Ví dụ:

```txt
DashboardController.java
AuthController.java
ResidentController.java
HouseholdController.java
FeePaymentController.java
```

Nhiệm vụ:

- Nhận request từ user
- Gọi service để xử lý
- Trả về trang HTML tương ứng
- Điều hướng giữa các màn hình

---

### `entity`

Chứa các class ánh xạ với bảng trong MySQL.

Ví dụ:

```txt
Resident.java
Household.java
FeeType.java
FeePayment.java
Vehicle.java
```

Mỗi entity thường tương ứng với một bảng database.

---

### `repository`

Chứa các interface làm việc với database thông qua Spring Data JPA.

Ví dụ:

```txt
ResidentRepository.java
HouseholdRepository.java
FeePaymentRepository.java
```

Nhiệm vụ:

- Thêm dữ liệu
- Sửa dữ liệu
- Xóa dữ liệu
- Tìm kiếm dữ liệu
- Truy vấn database

---

### `service`

Chứa interface khai báo nghiệp vụ.

Ví dụ:

```txt
ResidentService.java
HouseholdService.java
FeePaymentService.java
```

---

### `service/impl`

Chứa class implement service.

Ví dụ:

```txt
ResidentServiceImpl.java
HouseholdServiceImpl.java
FeePaymentServiceImpl.java
```

Đây là nơi xử lý logic chính của app.

---

### `dto`

Chứa object truyền dữ liệu giữa các tầng trong hệ thống.

Chia thành:

```txt
dto/request
dto/response
```

Trong đó:

- `dto/request`: nhận dữ liệu từ form hoặc request
- `dto/response`: trả dữ liệu ra giao diện

---

### `mapper`

Chứa các class chuyển đổi dữ liệu.

Ví dụ:

```txt
ResidentMapper.java
HouseholdMapper.java
FeeMapper.java
```

Dùng để chuyển đổi:

```txt
Entity <-> DTO
```

---

### `exception`

Chứa các class xử lý lỗi.

Ví dụ:

```txt
GlobalExceptionHandler.java
ResourceNotFoundException.java
BadRequestException.java
```

---

### `constant`

Chứa các hằng số dùng chung.

Ví dụ:

```txt
RoleName.java
ResidentStatus.java
PaymentStatus.java
FeeTypeName.java
```

---

### `util`

Chứa các hàm tiện ích dùng chung.

Ví dụ:

```txt
DateUtils.java
MoneyUtils.java
FileExportUtils.java
```

---

## 5. Các module chức năng chính

---

## Module 1: Dashboard

### Mục đích

Hiển thị trang tổng quan của hệ thống.

### File liên quan

```txt
controller/DashboardController.java
templates/dashboard/index.html
```

### Chức năng

- Hiển thị tổng số hộ khẩu
- Hiển thị tổng số nhân khẩu
- Hiển thị tổng khoản phí đã thu
- Hiển thị tổng khoản phí chưa thu
- Điều hướng nhanh đến các module khác

---

## Module 2: Quản lý nhân khẩu

### Mục đích

Quản lý thông tin cư dân trong chung cư.

### File liên quan

```txt
controller/ResidentController.java
entity/Resident.java
repository/ResidentRepository.java
service/ResidentService.java
service/impl/ResidentServiceImpl.java
dto/request/ResidentRequest.java
dto/response/ResidentResponse.java
mapper/ResidentMapper.java
templates/resident/
```

### Chức năng

- Thêm nhân khẩu mới
- Xem danh sách nhân khẩu
- Xem chi tiết nhân khẩu
- Cập nhật thông tin nhân khẩu
- Xóa nhân khẩu
- Tìm kiếm nhân khẩu theo tên, CCCD, số điện thoại
- Lọc nhân khẩu theo trạng thái

### Giao diện

```txt
templates/resident/list.html
templates/resident/create.html
templates/resident/update.html
templates/resident/detail.html
```

---

## Module 3: Quản lý hộ khẩu

### Mục đích

Quản lý thông tin các hộ trong chung cư.

### File liên quan

```txt
controller/HouseholdController.java
entity/Household.java
entity/HouseholdMember.java
repository/HouseholdRepository.java
repository/HouseholdMemberRepository.java
service/HouseholdService.java
service/impl/HouseholdServiceImpl.java
dto/request/HouseholdRequest.java
dto/response/HouseholdResponse.java
mapper/HouseholdMapper.java
templates/household/
```

### Chức năng

- Thêm hộ khẩu mới
- Xem danh sách hộ khẩu
- Xem chi tiết hộ khẩu
- Cập nhật thông tin hộ khẩu
- Xóa hộ khẩu
- Quản lý thành viên trong hộ
- Tìm kiếm hộ khẩu theo số phòng, tên chủ hộ

### Giao diện

```txt
templates/household/list.html
templates/household/create.html
templates/household/update.html
templates/household/detail.html
templates/household/members.html
```

---

## Module 4: Quản lý tạm trú, tạm vắng

### Mục đích

Quản lý cư dân tạm trú hoặc tạm vắng trong chung cư.

### File liên quan

```txt
controller/TemporaryResidenceController.java
entity/TemporaryResidence.java
repository/TemporaryResidenceRepository.java
service/TemporaryResidenceService.java
service/impl/TemporaryResidenceServiceImpl.java
dto/request/TemporaryResidenceRequest.java
templates/temporary/
```

### Chức năng

- Khai báo tạm trú
- Khai báo tạm vắng
- Xem danh sách tạm trú, tạm vắng
- Cập nhật thông tin tạm trú, tạm vắng
- Xóa bản ghi tạm trú, tạm vắng
- Tìm kiếm theo tên cư dân hoặc số căn hộ

### Giao diện

```txt
templates/temporary/list.html
templates/temporary/create.html
templates/temporary/update.html
```

---

## Module 5: Quản lý khoản phí cố định

### Mục đích

Quản lý các loại khoản phí cố định của chung cư.

### File liên quan

```txt
controller/FeeTypeController.java
entity/FeeType.java
repository/FeeTypeRepository.java
service/FeeTypeService.java
service/impl/FeeTypeServiceImpl.java
dto/request/FeeTypeRequest.java
templates/fee/
```

### Chức năng

- Thêm loại phí mới
- Xem danh sách loại phí
- Cập nhật loại phí
- Xóa loại phí
- Quản lý phí gửi xe
- Quản lý phí dịch vụ
- Quản lý phí chung cư

### Giao diện

```txt
templates/fee/fee-type-list.html
templates/fee/fee-type-create.html
templates/fee/fee-type-update.html
```

---

## Module 6: Quản lý đóng phí

### Mục đích

Theo dõi trạng thái đóng phí của từng hộ.

### File liên quan

```txt
controller/FeePaymentController.java
entity/FeePayment.java
repository/FeePaymentRepository.java
service/FeePaymentService.java
service/impl/FeePaymentServiceImpl.java
dto/request/FeePaymentRequest.java
dto/response/FeePaymentResponse.java
templates/fee/
```

### Chức năng

- Xem danh sách khoản phí theo từng hộ
- Cập nhật trạng thái đã nộp/chưa nộp
- Cập nhật ngày nộp tiền
- Tìm kiếm khoản phí theo hộ
- Thống kê hộ chưa đóng phí
- Thống kê tổng tiền đã thu

### Giao diện

```txt
templates/fee/payment-list.html
templates/fee/payment-update.html
```

---

## Module 7: Quản lý phí sinh hoạt hàng tháng

### Mục đích

Quản lý các khoản phí phát sinh hàng tháng của từng hộ.

### File liên quan

```txt
controller/LivingFeeController.java
entity/LivingFee.java
repository/LivingFeeRepository.java
service/LivingFeeService.java
service/impl/LivingFeeServiceImpl.java
dto/request/LivingFeeRequest.java
templates/fee/
```

### Chức năng

- Hiển thị phí sinh hoạt theo tháng
- Cập nhật phí sinh hoạt tháng hiện tại
- Quản lý tiền điện
- Quản lý tiền nước
- Quản lý phí dịch vụ phát sinh
- Tính tổng phí sinh hoạt của từng hộ

### Giao diện

```txt
templates/fee/living-fee-list.html
templates/fee/living-fee-update.html
```

---

## Module 8: Quản lý phương tiện

### Mục đích

Quản lý phương tiện của cư dân trong chung cư.

### File liên quan

```txt
controller/VehicleController.java
entity/Vehicle.java
repository/VehicleRepository.java
service/VehicleService.java
service/impl/VehicleServiceImpl.java
dto/request/VehicleRequest.java
templates/vehicle/
```

### Chức năng

- Thêm phương tiện
- Xem danh sách phương tiện
- Cập nhật phương tiện
- Xóa phương tiện
- Tìm kiếm theo biển số xe
- Tìm kiếm theo căn hộ
- Hỗ trợ tính phí gửi xe

### Giao diện

```txt
templates/vehicle/list.html
templates/vehicle/create.html
templates/vehicle/update.html
```

---

## Module 9: Quản lý diện tích căn hộ

### Mục đích

Quản lý diện tích từng căn hộ để phục vụ tính phí.

### File liên quan

```txt
controller/ApartmentAreaController.java
entity/ApartmentArea.java
repository/ApartmentAreaRepository.java
service/ApartmentAreaService.java
service/impl/ApartmentAreaServiceImpl.java
dto/request/ApartmentAreaRequest.java
templates/apartment-area/
```

### Chức năng

- Xem diện tích từng căn hộ
- Cập nhật diện tích căn hộ
- Tìm kiếm theo số căn hộ
- Hỗ trợ tính phí dịch vụ theo diện tích

### Giao diện

```txt
templates/apartment-area/list.html
templates/apartment-area/update.html
```

---

## Module 10: Tìm kiếm

### Mục đích

Tìm kiếm dữ liệu trong hệ thống.

### File liên quan

```txt
controller/SearchController.java
service/SearchService.java
service/impl/SearchServiceImpl.java
templates/search/result.html
```

### Chức năng

- Tìm kiếm nhân khẩu
- Tìm kiếm hộ khẩu
- Tìm kiếm khoản phí
- Tìm kiếm phương tiện
- Tìm kiếm thông tin tạm trú, tạm vắng

---

## Module 11: Báo cáo, thống kê

### Mục đích

Thống kê dữ liệu quản lý chung cư.

### File liên quan

```txt
controller/ReportController.java
service/ReportService.java
service/impl/ReportServiceImpl.java
dto/response/StatisticResponse.java
templates/report/
```

### Chức năng

- Thống kê tổng số hộ
- Thống kê tổng số nhân khẩu
- Thống kê số người tạm trú, tạm vắng
- Thống kê tổng tiền đã thu
- Thống kê tổng tiền chưa thu
- Thống kê danh sách hộ chưa đóng phí

### Giao diện

```txt
templates/report/resident-statistic.html
templates/report/household-statistic.html
templates/report/fee-statistic.html
```

---

## 6. Danh sách entity dự kiến

```txt
User
Role

Resident
Household
HouseholdMember
TemporaryResidence

FeeType
FeePayment
LivingFee

Vehicle
ApartmentArea

AuditLog
```

---

## 7. Danh sách bảng database dự kiến

```txt
users
roles
user_roles

residents
households
household_members
temporary_residences

fee_types
fee_payments
living_fees

vehicles
apartment_areas

audit_logs
```

---

## 8. Quy tắc đặt tên file

### Controller

```txt
TênModuleController.java
```

Ví dụ:

```txt
ResidentController.java
HouseholdController.java
```

---

### Entity

```txt
TênDanhTừ.java
```

Ví dụ:

```txt
Resident.java
Household.java
```

---

### Repository

```txt
TênEntityRepository.java
```

Ví dụ:

```txt
ResidentRepository.java
HouseholdRepository.java
```

---

### Service

```txt
TênModuleService.java
TênModuleServiceImpl.java
```

Ví dụ:

```txt
ResidentService.java
ResidentServiceImpl.java
```

---

### DTO

```txt
TênModuleRequest.java
TênModuleResponse.java
```

Ví dụ:

```txt
ResidentRequest.java
ResidentResponse.java
```

---

### HTML Thymeleaf

Tên file nên viết thường, cách nhau bằng dấu `-`.

Ví dụ:

```txt
list.html
create.html
update.html
detail.html
fee-type-list.html
payment-update.html
```

---

## 9. Luồng xử lý backend

Code backend đi theo luồng:

```txt
Controller -> Service -> Repository -> Database
```

Trong đó:

- `Controller`: nhận request, điều hướng trang
- `Service`: xử lý nghiệp vụ
- `Repository`: truy vấn database
- `Entity`: ánh xạ bảng database
- `DTO`: nhận/trả dữ liệu
- `Mapper`: chuyển đổi Entity và DTO

---

## 10. Quy trình làm việc Git

### Clone project

```bash
git clone https://github.com/khanh201104/Bai_tap_lon_IT3180_nhom_4.git
```

### Tạo branch mới

```bash
git checkout -b feature/ten-module
```

Ví dụ:

```bash
git checkout -b feature/resident-management
```

### Add code

```bash
git add .
```

### Commit code

```bash
git commit -m "Add resident management module"
```

### Push branch

```bash
git push origin feature/resident-management
```

Sau đó lên GitHub tạo Pull Request vào nhánh `main`.

---

## 11. Quy ước branch

```txt
main
develop
feature/auth
feature/dashboard
feature/resident-management
feature/household-management
feature/temporary-residence
feature/fee-management
feature/vehicle-management
feature/report-statistic
fix/login-error
fix/database-config
```

Không code trực tiếp lên `main`.

---



## 12. Phân chia công việc

## 13. Cách chạy project

### Bước 1: Tạo database

Mở MySQL Workbench hoặc terminal MySQL, chạy:

```sql
CREATE DATABASE bluemoon_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

---

### Bước 2: Cấu hình database

Mở file:

```txt
src/main/resources/application.properties
```

Cấu hình:

```properties
spring.application.name=bluemoon-apartment-management

server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/bluemoon_db?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.thymeleaf.cache=false
```

Lưu ý:

- `server.port=8080` là port của web app
- `3306` là port của MySQL
- Hai port này không cần giống nhau

---

### Bước 3: Chạy project

Chạy bằng terminal:

```bash
mvn spring-boot:run
```

Hoặc chạy trực tiếp file:

```txt
src/main/java/com/bluemoon/apartment/ApartmentApplication.java
```

trong IntelliJ IDEA.

---

### Bước 4: Truy cập web

Mở trình duyệt:

```txt
http://localhost:8080
```

---

## 14. Lưu ý khi code

- Không push folder `.idea`
- Không push folder `target`
- Không push file `.iml`
- Không push mật khẩu database thật
- Mỗi thành viên làm trên branch riêng
- Không code trực tiếp lên `main`
- Trước khi push nên chạy thử project
- Tên class, package, file HTML phải đặt thống nhất
- Code backend theo đúng luồng:

```txt
Controller -> Service -> Repository -> Database
```

- Giao diện để trong:

```txt
src/main/resources/templates
```

- CSS, JS, ảnh để trong:

```txt
src/main/resources/static
```

---

## 15. File `.gitignore` đề xuất

```gitignore
HELP.md
target/
.mvn/wrapper/maven-wrapper.jar
!**/src/main/**/target/
!**/src/test/**/target/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache

### IntelliJ IDEA ###
.idea/
*.iws
*.iml
*.ipr
out/

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/
build/
!**/src/main/**/build/
!**/src/test/**/build/

### VS Code ###
.vscode/

### OS ###
.DS_Store
Thumbs.db

### Logs ###
logs/
*.log

### Environment / Local config ###
.env
application-local.properties

### Upload files ###
uploads/*
!uploads/.gitkeep
```

---

## 16. Quy trình push code

### Lần đầu push project lên GitHub

```bash
git init
git add .
git commit -m "Initial Bluemoon apartment management project"
git branch -M main
git remote add origin https://github.com/khanh201104/Bai_tap_lon_IT3180_nhom_4.git
git pull origin main --allow-unrelated-histories
git push -u origin main
```

Nếu Git mở màn hình Vim khi pull, thoát bằng:

```txt
Ctrl + [
:wq
Enter
```

Hoặc:

```txt
Shift + Z
Shift + Z
```

---

### Các lần push sau

```bash
git add .
git commit -m "Message mô tả nội dung đã làm"
git push
```

---

## 17. Ghi chú cho team

Trước khi bắt đầu code:

1. Pull code mới nhất từ GitHub.
2. Tạo branch riêng.
3. Code đúng module được phân công.
4. Chạy thử project trước khi commit.
5. Commit message phải rõ ràng.
6. Push branch lên GitHub.
7. Tạo Pull Request để merge vào `main`.

Không tự ý sửa module của người khác nếu chưa thống nhất với team.
