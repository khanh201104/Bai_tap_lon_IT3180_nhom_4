# Thành viên 4 — Bạn Giang (đã triển khai)

## URL giao diện

| Chức năng | URL |
|-----------|-----|
| Loại phí (CRUD) | `/fees` |
| Đóng phí | `/fee-payments` |
| Phí sinh hoạt (điện/nước) | `/living-fees` |
| Phương tiện | `/vehicles` |
| Tìm kiếm | `/search?q=` |
| Thống kê | `/reports` |

## File đã thêm (đúng cấu trúc README)

- `controller/`: `FeeTypeController`, `FeePaymentController`, `LivingFeeController`, `VehicleController`, `ReportController`, `SearchController`
- `entity/`: `FeeType`, `FeePayment`, `LivingFee`, `Vehicle`
- `repository/`, `service/`, `service/impl/`
- `dto/response/StatisticResponse.java`
- `templates/fee/`, `templates/vehicle/`, `templates/report/`, `templates/search/`

## Chạy thử

```bash
mvn spring-boot:run
```

Mở `http://localhost:8080/fees`
