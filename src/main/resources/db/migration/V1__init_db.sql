CREATE TABLE reservation
(
    id bigserial PRIMARY KEY,
    guest_id BIGINT NOT NULL,
    email varchar(36) NOT NULL,
    phone varchar(36) NOT NULL,
    hotel_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,
    room_type varchar(36) NOT NULL,
    status varchar(36) NOT NULL,
    created_date TIMESTAMP WITH TIME ZONE,
    start_date TIMESTAMP WITH TIME ZONE,
    end_date TIMESTAMP WITH TIME ZONE
)
