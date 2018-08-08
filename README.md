# meetingApp
会议室预约APP服务端

## 数据库设计

- user

Field | Type | Null | Default | Primary Key | extra
--- | --- | ---| ---| ---| ---
**id** | bigint(20) | no |  | yes | auto_increment start from 1
**full_name** | varchar(255) | yes | null | no |
**role** | varchar(10) | no | user| no | admin or user
**email** | varchar(255) | no | | no | 
**password** | varchar(255) | no | | no | 
**telephone** | varchar(11) |  yes |null  | no |
**register_ip** | varchar(45) | yes | null |no |
**last_login_ip**| varchar(45) | yes | null | no | 

- room

Field | Type | Null | Default | Primary Key | extra
--- | --- | ---| ---| ---| ---
**id** | bigint(20) | no | |yes | auto_increment start from 1
**address** | varchar(255) | no | | no | 
**contain**| int(5) | yes | null | no | hold people number

- meeting 

Field | Type | Null | Default | Primary Key | extra
--- | --- | ---| ---| ---| ---
**id** | bigint(20) | no | | yes | auto_increment start from 1
**start_time** | date | no | | no |
**end_time** | date | no | | no |
**member_num**| int(5) | no | | no |
**room_id** | long | no | | no |
**theme** | varchar(255) | yes | null | no |
**description** | varchar(255) | yes | null | no | 
**member** | varchar(255) |  yes | null | no |
**status** | int(4) | no | | no |
**cancel_reason** | varchar(255) | yes | null | no |
**created_by** | bigint(20) | no | | no |
**updated_by** | bigint(20) | no | | no |

